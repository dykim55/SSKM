package com.cyberone.sskm.article.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.sskm.Common;
import com.cyberone.sskm.Constants;
import com.cyberone.sskm.article.service.ArticleService;
import com.cyberone.sskm.exception.BizException;
import com.cyberone.sskm.model.AppxFile;
import com.cyberone.sskm.model.Board;
import com.cyberone.sskm.model.UserInfo;
import com.cyberone.sskm.utils.DataUtil;
import com.cyberone.sskm.utils.StringUtil;


@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 공지사항, 인수인계 페이지 요청
     */
    @RequestMapping(value = {"/article/notice", "/article/transfer"})
    public String article(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
       	String sBbsSct = "";
       	if (request.getServletPath().equals("/article/notice")) {
       		sBbsSct = Constants.BBS_NOTICE;
       	} else if (request.getServletPath().equals("/article/transfer")) {
       		sBbsSct = Constants.BBS_TRANSFER;
       	} 
       	
       	Board dto = (Board) DataUtil.dtoBuilder(request, Board.class);
       	dto.setBbsSct(sBbsSct);
       	
    	List<HashMap<String, Object>> articleList = articleService.selectBoardList(dto);
       	
    	model.addAttribute("bbsSct", sBbsSct);
        model.addAllAttributes(dto.createModelMap(articleList));
        
        return request.getServletPath();
    }

    /**
     * 게시글 목록 조회
     */
    @RequestMapping("/article/article_list")
    public String articleList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	
        List<HashMap<String, Object>> articleList = null;
        Board board = (Board) DataUtil.dtoBuilder(request, Board.class);
        
        articleList = articleService.selectBoardList(board);
        
    	model.addAllAttributes(board.createModelMap(articleList));
    	
    	return request.getServletPath(); 
    }

    /**
     * 게시글 등록/수정 페이지 요청
     */
    @RequestMapping("/article/article_write")
    public String articleWrite(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	
    	if (!StringUtil.isEmpty(sId)) {
    		HashMap<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("bbsId", Integer.valueOf(sId));
    		HashMap<String, Object> bbsMap =  articleService.selectBoard(paramMap);
    		model.addAttribute("bbsMap", bbsMap);
    		
        	AppxFile appxFile = new AppxFile();
        	appxFile.setRefCd(Integer.valueOf(sId));
        	List<AppxFile> appxFiles = articleService.selectAppxFiles(appxFile);
        	model.addAttribute("appxFiles", appxFiles);
    		
        	if (!StringUtil.convertString(bbsMap.get("regr")).equals(userInfo.getAcct().getAcctId())) {
	        	paramMap.put("qryCnt", 1);
	        	articleService.updateBoard(paramMap);
        	}
    	}
    	
    	return request.getServletPath(); 
    }
    
    /**
     * 게시글 등록/수정
     */
    @RequestMapping("/article/article_register")
    public ModelAndView articleRegister(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
        List<MultipartFile> mpfList = request.getFiles("file-data");
    	
        String sId = request.getParameter("uf_id");
        String sBbsSct = request.getParameter("uf_sct");
        String sTitle = StringUtil.replaceHtml(request.getParameter("uf_title"));
        String sContent = StringUtil.replaceHtml(request.getParameter("uf_content"));
        String sDel = request.getParameter("uf_del");
        
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("bbsTit", sTitle);
    	paramMap.put("bbsCont", sContent);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (sId != null) {
    		paramMap.put("bbsId", Integer.valueOf(sId));
    		articleService.updateBoard(request, paramMap, sBbsSct, mpfList);

    		//삭제요청 첨부파일 리스트
    		if (!StringUtil.isEmpty(sDel)) {
    			String[] saDel = sDel.split(",");
    			for (String f : saDel) {
    				if (StringUtil.isEmpty(f)) {
    					continue;
    				}
    				removeFile(f, sId, userInfo);
    			}
    		}
    		
    	} else {
    		paramMap.put("bbsSct", sBbsSct);
        	paramMap.put("fileYn", "n");
        	paramMap.put("delYn", "n");
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		
        	articleService.insertBoard(request, paramMap, sBbsSct, mpfList);

    		sId = StringUtil.convertString(paramMap.get("bbsId"));
    	}
    	
        ModelAndView modelAndView = new ModelAndView("jsonView");
        return modelAndView.addObject("status", "success");
    }    
 
    /**
     * 게시글 삭제
     */
    @RequestMapping("/article/article_delete")
    public ModelAndView fileDelete(HttpServletRequest request) throws Exception {

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sId = request.getParameter("id");
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bbsId", Integer.valueOf(sId));
		HashMap<String, Object> articleMap =  articleService.selectBoard(paramMap);

		if (articleMap == null) {
			throw new BizException("이미 삭제되었습니다.");
		}
		
		articleService.deleteBoard(paramMap);
		
		articleService.deleteAppxByRef(paramMap);
		
		Common.insertAuditHist(Constants.AUDIT_ARTICLE, "공지사항이 삭제되었습니다.", "S", StringUtil.convertString(articleMap.get("title")), userInfo.getAcct().getAcctId());
		
        ModelAndView modelAndView = new ModelAndView("jsonView");
        return modelAndView.addObject("status", "success"); 
    }
    
    /**
     * 게시글 첨부파일 리스트 조회
     */
    @RequestMapping("/article/appx_list")
    public String appxList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
 
    	String sId = request.getParameter("id");
    	
    	if (!StringUtil.isEmpty(sId)) {
    		
        	AppxFile appxFile = new AppxFile();
        	appxFile.setRefCd(Integer.valueOf(sId));
    		List<AppxFile> appxFiles = articleService.selectAppxFiles(appxFile);
        	model.addAttribute("appxList", appxFiles);
    	}

    	return "/article/file_box";
    }
    
    /**
     * 게시글 첨부파일 다운로드
     */
    @RequestMapping("/article/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	String sFileId = request.getParameter("id");
    	
    	AppxFile appxFile = new AppxFile();
    	appxFile.setFileId(Integer.valueOf(sFileId));
    	List<AppxFile> appxFiles = articleService.selectAppxFiles(appxFile);
    	
    	if (appxFiles.size() == 0) {
    		throw new Exception();
    	}
    	
		String sOrgFileName = appxFiles.get(0).getFileOrgNm();
		
		File uFile = new File(appxFiles.get(0).getFileLoc());
		int fSize = (int) uFile.length();
 
		if (fSize > 0) {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));

			String mimetype = "text/html";
 
			response.setBufferSize(fSize);
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(sOrgFileName, "UTF-8") + "\"");
			response.setContentLength(fSize);
 
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} else {
			throw new Exception();
		}
	}
    
    /**
     * 게시글 첨부파일 삭제
     */
	public void removeFile(String sFileSeq, String sRef, UserInfo userInfo) throws Exception {

    	HashMap<String, Object> hRefMap = null;
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("bbsId", Integer.parseInt(sRef));
		hRefMap = articleService.selectBoard(paramMap);
		
    	AppxFile appxFile = new AppxFile();
    	appxFile.setFileId(Integer.valueOf(sFileSeq));
    	List<AppxFile> appxFiles = articleService.selectAppxFiles(appxFile);
    	
		if (appxFiles.size() == 0) {
			throw new BizException("파일을 찾을 수 없습니다.");
		}
		
		paramMap.clear();
		paramMap.put("fileId", Integer.parseInt(sFileSeq));
		articleService.deleteAppxById(paramMap);

		File uFile = new File(appxFiles.get(0).getFileLoc());
		if (!uFile.delete()) { }
		
		StringBuffer sMemo = new StringBuffer();
		sMemo = sMemo.append("제목:").append(StringUtil.convertString(hRefMap.get("title"))).append("\n");
		sMemo = sMemo.append("파일:").append(StringUtil.convertString(appxFiles.get(0).getFileOrgNm())).append("\n");
		
		Common.insertAuditHist(Constants.AUDIT_FILES, "첨부파일이 삭제되었습니다.", "S", sMemo.toString(), userInfo.getAcct().getAcctId());
		
		appxFile = new AppxFile();
		appxFile.setRefCd(Integer.parseInt(sRef));
		appxFiles = articleService.selectAppxFiles(appxFile);
		if (appxFiles.size() == 0) {
			paramMap.clear();
			paramMap.put("bbsId", Integer.parseInt(sRef));
			paramMap.put("fileYn", "n");
			articleService.updateBoard(paramMap);
		}
	}
	
}