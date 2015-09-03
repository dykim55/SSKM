package com.cyberone.scourt.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.main.service.MainService;
import com.cyberone.scourt.model.UserInfo;

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MainService mainService;

    @RequestMapping("/main")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	model.addAttribute("userInfo", userInfo);
    	
    	ModelAndView modelAndView = new ModelAndView("/main/view");
    	return modelAndView.addObject("status", "success");
    }
}
