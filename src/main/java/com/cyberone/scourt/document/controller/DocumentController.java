package com.cyberone.scourt.document.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.Constants;
import com.cyberone.scourt.document.service.DocumentService;
import com.cyberone.scourt.model.Board;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;


@Controller
public class DocumentController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DocumentService docService;

    @RequestMapping("product")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	String sBbsSct = Constants.getBbsSct(request.getServletPath());
       	
       	Board dto = (Board) DataUtil.dtoBuilder(request, Board.class);
       	dto.setBbsSct(sBbsSct);
       	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
       	paramMap.put("ctgSct", 2001);
    	List<HashMap<String, Object>> ctgList = docService.selectCategory(paramMap);
       	
        model.addAttribute("ctgList", ctgList);
        
        return "/document/" + request.getServletPath();
    }

    @RequestMapping("product/create_folder")
    public String createFolder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	return "/document/create_folder";
    }

    @RequestMapping("product/folder_register")
    public ModelAndView folderRegister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	logger.debug(request.getParameter("parent"));
    	logger.debug(request.getParameter("name"));
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	return modelAndView.addObject("status", "success");
    }
    
}