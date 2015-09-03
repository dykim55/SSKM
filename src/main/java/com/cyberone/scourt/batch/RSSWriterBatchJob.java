package com.cyberone.scourt.batch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cyberone.scourt.model.RssFeed;
import com.cyberone.scourt.model.RssNews;
import com.cyberone.scourt.rss.service.RssService;
import com.cyberone.scourt.utils.StringUtil;

@Service
public class RSSWriterBatchJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SqlSession sqlSession;

    @Autowired
    private RssService rssService;

	private long lLastTime;
	private long millis;

	/*
	 * 1. RSS 주소 DB 조회
	 * 2. RSS 파싱
	 * 3. RSS 주소 DB 등록
	 * 4. RSS 주소 DB guidLast 업데이트 등록
	 */
    @Transactional
    public void run() {

    	try {
    		logger.debug("RSSWriterBatchJob Start...");
    		lLastTime = System.currentTimeMillis();
    		
    		/* RSS 주소 DB 조회 */
			RssFeed rssFeed = new RssFeed();
			List<HashMap<String, Object>> rssFeedList = sqlSession.selectList("Rss.selectRssFeed", rssFeed);
			
			/* RSS 파싱 */
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			URL u = null;
			Document doc = null;
			NodeList nodes = null;
			Element element = null;
			RssNews rss = null;

			int feedSeq = 0; // rss 츨처
			String rssSrc = ""; // rss 츨처
			String feed = ""; // rss xml 링크 주소
			String guidParam = ""; // guid값 파라메터명
			long guidLast = 0; // guid 마지막수집 값
			int nodesLen = 0; // nodes 갯수
			int nodesLen2 = 0; // nodes 갯수
			String link = ""; // rss link
			String link2 = ""; // rss link
			String title = ""; // rss title
			String description = ""; // rss description
			String guid = ""; // rss guid

	    	String paramAllArr[];
	    	int paramAllArrLen;
	    	String paramArr[];
	    	String name="";
	    	String value="";
	    	long intGuid = 0; // guid 
	    	long guidMax = 0; // guid 가장 큰값
	    	boolean dbProcessBool = false;
	    	
			for (HashMap<String, Object> result : rssFeedList) {
				
		    	try {
		    		feedSeq = (Integer)result.get("feedSeq");
					rssSrc = StringUtil.convertString(result.get("rssSrc"));
					feed = StringUtil.convertString(result.get("rssFeed"));
					guidParam = StringUtil.convertString(result.get("guidParam"));
					guidLast = Long.valueOf(StringUtil.convertString(result.get("guidLast")));
	
					u = new URL(feed);
	
					doc = builder.parse(u.openStream());
			    	
					nodes = doc.getElementsByTagName("item");
					
					nodesLen = nodes.getLength();
					nodesLen2 = nodesLen-1;
							
					for (int i = 0; i < nodesLen; i++) {
						element = (Element) nodes.item(i);
						link = getElementValue(element, "link");
						title = getElementValue(element, "title");
						description = getElementValue(element, "description");
						
						if("etnews".equals(rssSrc)){
							value = StringUtil.nullToStr(getElementValue(element, "guid"), "0");
							intGuid = Long.parseLong(value);
						} else if("zdnet".equals(rssSrc)){
							// <guid>http://zdnetkorea.feedsportal.com/c/34249/f/622755/s/434e9350/l/0L0Szdnet0Bco0Bkr0Cnews0Cnews0Iview0Basp0Dartice0Iid0F20A150A212140A239/story01.htm</guid>
							guid = getElementValue(element, "guid");
							String guid1Arr [] = guid.split("/s/");
							String guid2Arr [] = guid1Arr[1].split("/l/");
							intGuid = Long.parseLong(StringUtil.nullToStr(guid2Arr[0], "0"), 16);
						} else {
							/* link 에서 guidParam 값을 찾는다 */
							link2 = link.substring(link.indexOf("?")+1, link.length());
							paramAllArr = link2.split("&");
							paramAllArrLen = paramAllArr.length;
							
							for(int j=0; j<paramAllArrLen; j++){
	
								paramArr = paramAllArr[j].split("=");
	
								if(paramArr.length > 1){
					    	    	name=paramArr[0];
					    	    	value=paramArr[1];
								} else {
					    	    	name=paramArr[0];
					    	    	value="0";
								}
	
								/* link 에서 guidParam 값을 찾음 */
					    		if(name.equals(guidParam)){
					    			value = value.trim();
					    			break;
						    	} 
							}
							intGuid = Long.parseLong(StringUtil.nullToStr(value.trim(), "0"));
						}
						
						/* guidLast 값보다 큰 guid인 rss만 등록한다. */
			    		if(guidLast >= intGuid){
			    			dbProcessBool = false;
		    			} else {
		    				if(guidMax < intGuid){
			    				guidMax = intGuid;
		    				}
		    				dbProcessBool = true;
		    			}
						
			    		if(dbProcessBool){
			    			description = description.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
			    			description = description.length() > 150 ? description.substring(0, 150) : description;
							
			    	    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
			    	    	paramMap.put("rssTit", title);
			    	    	paramMap.put("rssCont", description);
			    	    	paramMap.put("rssSrc", rssSrc);
			    	    	paramMap.put("rssLink", link);
			    	    	paramMap.put("rssShow", "n");
			    	    	paramMap.put("regr", "system");
			    	    	paramMap.put("regDtime", new Date());
		
							/* RSS 주소 DB 등록 */
							//rssService.insertRssNews(paramMap);
							sqlSession.insert("Rss.insertRssNews", paramMap);
			    		}
			    		
						/* RSS 주소 DB guidLast 업데이트 등록 */
						if(i == nodesLen2 && guidLast < guidMax){
							HashMap<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("feedSeq", feedSeq);
							paramMap.put("guidLast", guidMax);

							//rssService.updateRssFeed(paramMap);
							sqlSession.update("Rss.updateRssFeed", paramMap);
							
							guidMax = 0;
						}
					}// for
		    	} catch (SAXException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}// for
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		millis = System.currentTimeMillis() - lLastTime;
    		logger.debug("RSSWriterBatchJob End... [소요시간: " + millis + "]");
    	}
    
    }

    private String getElementValue(Element parent, String label) {
		return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
	}

	private String getCharacterDataFromElement(Element e) {
		try {
			Node child = e.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
		} catch (Exception ex) {

		}
		return "";
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
    
}
