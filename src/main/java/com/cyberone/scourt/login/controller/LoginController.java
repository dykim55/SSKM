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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.login.service.LoginService;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.MenuAuth;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.Encryption;
import com.cyberone.scourt.utils.StringUtil;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private LoginService loginService;
    
	private HashMap<String, HttpSession> userMap = new HashMap<String, HttpSession>();
	
    @RequestMapping("/loginForm")
    public String loginForm(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        return "/login/auth";
    }
	
    @RequestMapping(value="loginForm", method=RequestMethod.POST)
    public String verifyAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	String sAcctId = request.getParameter("loginId");
    	String sAcctPw = Encryption.encrypt(request.getParameter("loginPw"));
    	
    	if (StringUtil.isEmpty(sAcctId)) {
    		throw new BizException("9999", "아이디 또는 패스워드를 입력하세요.");
    	}
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("acctId", sAcctId);
		paramMap.put("acctPw", sAcctPw);
		
    	Acct acct = loginService.selectAcct(paramMap);
    	
		if (acct == null) {
			throw new BizException("9999", "로그인아이디 또는 비밀번호가 잘못됐습니다.");
		}

		MenuAuth menuAuth = loginService.selectMenuAuth(paramMap);
		
		paramMap.clear();
		paramMap.put("acctGrpCd", acct.getAcctGrpCd());
		AcctGrp acctGrp = loginService.selectAcctGrp(paramMap);
		
		
		UserInfo userInfo  = new UserInfo();
		userInfo.setAcct(acct);
		userInfo.setAcctGrp(acctGrp);
		userInfo.setMenuAuth(menuAuth);
		
    	HttpSession session = request.getSession();
    	session.setAttribute("userInfo", userInfo);
    	
    	logger.info("접속 아이디: " + userInfo.getAcct().getAcctId() + "(" + userInfo.getAcct().getAcctNm() + ")");
    	logger.info("Login Session ID %s ( timeout: %s )", session.getId(), session.getMaxInactiveInterval());
    	
    	userMap.put(acct.getAcctId(), session);
    	
		return "redirect:/main";
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
        
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");

        userMap.remove(userInfo.getAcct().getAcctId());
        
        try { request.getSession(true).invalidate(); } catch(Exception e) {}

        return "redirect:/";
    }
    
}
