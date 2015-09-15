package com.cyberone.scourt.login.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.interceptor.SessionMonitorListener;
import com.cyberone.scourt.login.service.LoginService;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.Encryption;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private LoginService loginService;
    
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        return "/login/auth";
    }
	
    @RequestMapping(value="/verify_account", method=RequestMethod.POST)
    public String verifyAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	String sAcctId = request.getParameter("loginId");
    	String sAcctPw = request.getParameter("loginPw");
    	
    	/*
    	if (StringUtil.isEmpty(sAcctId) || StringUtil.isEmpty(sAcctPw)) {
    		throw new BizException("9999", "아이디 또는 패스워드를 입력하세요.");
    	}*/
    	sAcctPw = Encryption.encrypt(request.getParameter("loginPw"));
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("acctId", sAcctId);
		paramMap.put("acctPw", sAcctPw);
		
    	Acct acct = loginService.selectAcct(paramMap);
    	
		if (acct == null) {
			throw new BizException("9999", "로그인아이디 또는 비밀번호가 잘못됐습니다.");
		}

		UserInfo userInfo  = new UserInfo();
		userInfo.setAcct(acct);
		
    	HttpSession session = request.getSession();
    	session.setAttribute("userInfo", userInfo);
    	
    	SessionMonitorListener.addUserInfo(userInfo, session);
    	
    	logger.info("접속 아이디: " + userInfo.getAcct().getAcctId() + "(" + userInfo.getAcct().getAcctNm() + ")");
    	logger.info("Login Session ID {} ( timeout: {} )", session.getId(), session.getMaxInactiveInterval());
    	
		return "redirect:/main";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        
        try { request.getSession(true).invalidate(); } catch(Exception e) {}

        return "redirect:/";
    }
    
}
