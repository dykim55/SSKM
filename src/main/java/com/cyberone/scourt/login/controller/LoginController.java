package com.cyberone.scourt.login.controller;

import java.util.Date;
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

import com.cyberone.scourt.Common;
import com.cyberone.scourt.Constants;
import com.cyberone.scourt.account.service.AccountService;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.interceptor.SessionMonitorListener;
import com.cyberone.scourt.login.service.LoginService;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.Encryption;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        return "/login/auth";
    }
	
    @RequestMapping(value="/verify", method=RequestMethod.POST)
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
			Common.insertAuditHist(Constants.AUDIT_LOGIN, "로그인 아이디 또는 비밀번호가 잘못됐습니다.", "F", "", sAcctId);
			throw new BizException("9999", "로그인 아이디 또는 비밀번호가 잘못됐습니다.");
		}

		
		paramMap.clear();
		paramMap.put("acctId", sAcctId);
		paramMap.put("latestDtime", new Date());
		accountService.updateAcct(paramMap);
		
		UserInfo userInfo  = new UserInfo();
		userInfo.setAcct(acct);
		
		paramMap.put("acctGrpCd", acct.getAcctGrpCd());
		AcctGrp acctGrp = loginService.selectAcctGrp(paramMap);
		userInfo.setAcctGrp(acctGrp);
		
    	HttpSession session = request.getSession();
    	session.setAttribute("userInfo", userInfo);
    	
    	SessionMonitorListener.addUserInfo(userInfo, session);
    	
    	Common.insertAuditHist(Constants.AUDIT_LOGIN, userInfo.getAcct().getAcctNm() + "님이 로그인하셨습니다.", "S", "", userInfo.getAcct().getAcctId());
    	
    	logger.info("접속 아이디: " + userInfo.getAcct().getAcctId() + "(" + userInfo.getAcct().getAcctNm() + ")");
    	logger.info("Login Session ID {} ( timeout: {} )", session.getId(), session.getMaxInactiveInterval());
    	
		return "redirect:/files/security_control";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        
        try { request.getSession(true).invalidate(); } catch(Exception e) {}

        return "redirect:/";
    }
    
}
