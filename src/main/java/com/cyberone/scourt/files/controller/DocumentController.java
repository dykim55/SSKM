package com.cyberone.scourt.files.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.files.service.DocumentService;
import com.cyberone.scourt.model.Product;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.StringUtil;


@Controller
public class DocumentController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DocumentService docService;

    @RequestMapping(value = {"/files/security_control", "/files/security_diagnosis", "/files/info_protection_manage", "/files/info_protection_policy", "/files/info_protection_trend", "/files/security_news"})
    public String files(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	String sPrtsCd = "";
       	if (request.getServletPath().equals("/files/security_control")) {
       		sPrtsCd = "1000";
       	} else if (request.getServletPath().equals("/files/security_diagnosis")) {
       		sPrtsCd = "2000";
       	} else if (request.getServletPath().equals("/files/info_protection_manage")) {
       		sPrtsCd = "3000";
       	} else if (request.getServletPath().equals("/files/info_protection_policy")) {
       		sPrtsCd = "4000";
       	} else if (request.getServletPath().equals("/files/info_protection_trend")) {
       		sPrtsCd = "5000";
       	} else if (request.getServletPath().equals("/files/security_news")) {
       		sPrtsCd = "6000";
       	}
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("prtsCd", Integer.valueOf(sPrtsCd));
       	List<HashMap<String, Object>> subMenuList = docService.selectMenu(paramMap);
       	
       	HashMap<String, Object> subMenuMap = subMenuList.get(0);
       	
       	HashMap<String, Object> sideMap = new HashMap<String, Object>();
       	
       	for (HashMap<String, Object> hMap : subMenuList) {
           	paramMap.put("ctgSct", hMap.get("menuCd"));
        	List<HashMap<String, Object>> ctgList = docService.selectCategory(paramMap);
        	sideMap.put(StringUtil.convertString(hMap.get("menuCd")), ctgList);
       	}
       	
    	model.addAttribute("gubun", subMenuMap.get("menuCd"));
    	model.addAttribute("subMenuList", subMenuList);
    	model.addAttribute("sideMap", sideMap);
        
        return "/files/contents";
    }

    @RequestMapping("/files/create_folder")
    public String createFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	return "/files/create_folder";
    }

    @RequestMapping("/files/folder_register")
    public ModelAndView folderRegister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	logger.debug(request.getParameter("gubun"));
    	logger.debug(request.getParameter("parent"));
    	logger.debug(request.getParameter("name"));
    	
    	String sSct = request.getParameter("gubun");
    	String sParent = request.getParameter("parent");
    	String sName = request.getParameter("name");
    	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgNm", sName);
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
       	paramMap.put("ctgParent", Integer.valueOf(sParent));
       	paramMap.put("ctgDesc", "");
       	paramMap.put("regr", userInfo.getAcct().getAcctId());
       	paramMap.put("regDtime", new Date());
       	paramMap.put("modr", userInfo.getAcct().getAcctId());
       	paramMap.put("modDtime", new Date());

       	docService.insertCategory(paramMap);
    	
       	logger.debug(StringUtil.convertString(paramMap.get("ctgId")));
       	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("gubun", Integer.valueOf(sSct));
    	modelAndView.addObject("id", paramMap.get("ctgId"));
    	modelAndView.addObject("name", sName);
    	modelAndView.addObject("parent", Integer.valueOf(sParent));

    	return modelAndView.addObject("status", "success");
    }
    
    @RequestMapping("/files/tree_ajax")
    public String treePane(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sSct = request.getParameter("gubun");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
    	List<HashMap<String, Object>> ctgList = docService.selectCategory(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/files/tree_ajax";
    }
    
    @RequestMapping("/files/file_upload")
    public String fileUpload(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	return "/files/file_upload";
    }

    @RequestMapping("/files/file_register")
    public ModelAndView fileRegister(MultipartHttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
        List<MultipartFile> mpfList = request.getFiles("uf_file");
    	
        String sId = request.getParameter("id");
        
        String sParent = request.getParameter("uf_parent");
        String sTitle = request.getParameter("uf_title");
        String sContent = request.getParameter("uf_content");

    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("title", sTitle);
    	paramMap.put("content", sContent);
    	paramMap.put("parent", Integer.valueOf(sParent));
    	paramMap.put("fileYn", "n");
    	paramMap.put("delYn", "n");
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (sId != null) {
    		paramMap.put("pId", Integer.valueOf(sId));
    		docService.updateProduct(request, paramMap, mpfList);
    	} else {
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		
    		docService.insertProduct(request, paramMap, mpfList);
    		sId = StringUtil.convertString(paramMap.get("pId"));
    	}
    	
    	/* Daum Editor
    	//삭제된 본문 이미지 삭제
    	if (sRemoveImages != null) {
    		boardService.removeEditorImage(request, paramMap, sRemoveImages);
    	}

        //본문첨부 이미지정보 저장
    	if (sAttachImages != null) {
    		boardService.insertAttachments(request, paramMap, sAttachImages);
    	}
    	*/
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", paramMap.get("parent"));
        return modelAndView.addObject("status", "success");
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping("product")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	String sSct = request.getParameter("gubun");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", Integer.valueOf(sSct));
    	List<HashMap<String, Object>> ctgList = docService.selectCategory(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/document/" + request.getServletPath();
    }


    @RequestMapping("file_pane")
    public String filePane(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sParent = request.getParameter("parent");
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("parent", Integer.valueOf(sParent));
       	
       	Product product = (Product)DataUtil.dtoBuilder(request, Product.class);
       	
    	List<HashMap<String, Object>> productList = docService.selectProductList(product);
       	
    	model.addAllAttributes(product.createModelMap(productList));
    	 
    	return "/document/file_pane";
    }
    
    @RequestMapping("product/upload_file")
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	return "/document/upload_file";
    }

    
     
}