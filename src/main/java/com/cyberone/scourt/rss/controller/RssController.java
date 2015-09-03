package com.cyberone.scourt.rss.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyberone.scourt.model.RssFeed;
import com.cyberone.scourt.model.RssNews;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.rss.service.RssService;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.StringUtil;

@Controller
public class RssController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RssService rssService;

    /*
     * 보안뉴스
    */
    @RequestMapping("securityNews")
    public String rssManage(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	/*
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        if (!userInfo.isAuth("2005", "R")) {
        	throw new BizException("권한이 없습니다.");
        }
        */
    	
    	RssNews dto = (RssNews) DataUtil.dtoBuilder(request, RssNews.class);
       	
    	List<HashMap<String, Object>> newsList = rssService.selectRssNewsList(dto);
       	
        model.addAllAttributes(dto.createModelMap(newsList));
        
        return "/securityNews/news";
    }

    /*
     * RSS 피드 목록
    */
    @RequestMapping("rssFeedMng")
    public String rssFeedMng(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
		
        RssFeed dto = (RssFeed) DataUtil.dtoBuilder(request, RssFeed.class);
		
        List<HashMap<String, Object>> feedList = rssService.selectRssFeedList(dto);
       	
        model.addAllAttributes(dto.createModelMap(feedList));        
        
        return "/securityNews/rssFeedMng"; 
    }

    @RequestMapping("rssFeedWrite")
    public String rssFeedWrite(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        return "/securityNews/rssFeedWrite"; 
    }

    @RequestMapping("rssFeedDtl")
    public String rssFeedDtl(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
		
    	int nFeedSeq = Integer.valueOf(request.getParameter("seq"));
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("feedSeq", nFeedSeq);
    	
        HashMap<String, Object> feedMap = rssService.selectRssFeed(paramMap);
       	
        model.addAttribute("rssFeed", feedMap);        
        
        return "/securityNews/rssFeedDtl"; 
    }
    
    @RequestMapping("rssFeedReg")
    public String rssFeedReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	int nFeedSeq = Integer.valueOf(StringUtil.nullToStr(request.getParameter("seq"), "0"));
    	String sRssSrc = request.getParameter("rssSrc");
    	String sRssFeed = request.getParameter("rssFeed");
    	String sGuidParam = request.getParameter("guidParam");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("rssSrc", sRssSrc);
    	paramMap.put("rssFeed", sRssFeed);
    	paramMap.put("guidParam", sGuidParam);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());
    	
    	if (nFeedSeq > 0) {
    		paramMap.put("feedSeq", nFeedSeq);
    		rssService.updateRssFeed(paramMap);
    	} else {
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		rssService.insertRssFeed(paramMap);
    	}
    	
    	return "redirect:/rssFeedMng";
    }

    @RequestMapping("rssFeedDel")
    public String rssFeedDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	int nFeedSeq = Integer.valueOf(StringUtil.nullToStr(request.getParameter("seq"), "0"));
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("feedSeq", nFeedSeq);
    	
    	rssService.deleteRssFeed(paramMap);
    	
    	return "redirect:/rssFeedMng";
    }
    
}