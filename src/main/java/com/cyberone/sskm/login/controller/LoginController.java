package com.cyberone.sskm.login.controller;

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

import com.cyberone.sskm.Common;
import com.cyberone.sskm.Constants;
import com.cyberone.sskm.account.service.AccountService;
import com.cyberone.sskm.exception.BizException;
import com.cyberone.sskm.interceptor.SessionMonitorListener;
import com.cyberone.sskm.login.service.LoginService;
import com.cyberone.sskm.model.Acct;
import com.cyberone.sskm.model.AcctGrp;
import com.cyberone.sskm.model.UserInfo;
import com.cyberone.sskm.utils.Encryption;
import com.cyberone.sskm.utils.StringUtil;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountService accountService;

    /**
     * 로그인 페이지 요청
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	return "/login/auth";
    }

    /**
     * 로그인 인증
     */
    @RequestMapping(value="/verify", method=RequestMethod.POST)
    public String verifyAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

    	String sAcctId = request.getParameter("loginId");
    	String sAcctPw = request.getParameter("loginPw");
    	
    	if (StringUtil.isEmpty(sAcctId) || StringUtil.isEmpty(sAcctPw)) {
    		throw new BizException("9999", "아이디 또는 패스워드를 입력하세요.");
    	}
    	sAcctPw = Encryption.encrypt(sAcctPw);
    	
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("acctId", sAcctId);
		paramMap.put("acctPw", sAcctPw);
		paramMap.put("acctSt", 1);
		
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
    	
    	logger.info("{}({})님이 접속하셨습니다.", userInfo.getAcct().getAcctId(), userInfo.getAcct().getAcctNm());
    	
    	String prmsMenus = userInfo.getAcctGrp().getAcctPrmsMenus();
    	String[] arrayMenus = prmsMenus.split(",");
    	
       	String sViewName = "";
       	if ("1000".equals(arrayMenus[1])) {
       		sViewName = "/files/security_control";
       	} else if ("2000".equals(arrayMenus[1])) {
       		sViewName = "/files/security_diagnosis";
       	} else if ("3000".equals(arrayMenus[1])) {
       		sViewName = "/files/info_protection_manage";
       	} else if ("4000".equals(arrayMenus[1])) {
       		sViewName = "/files/info_protection_policy";
       	} else if ("5000".equals(arrayMenus[1])) {
       		sViewName = "/files/info_protection_trend";
       	} else if ("6000".equals(arrayMenus[1])) {
       		sViewName = "/files/security_news";
       	} else if ("A000".equals(arrayMenus[1])) {
       		sViewName = "/article/notice";
       	} else if ("B000".equals(arrayMenus[1])) {
       		sViewName = "/article/transfer";
       	} else if ("C000".equals(arrayMenus[1])) {
       		sViewName = "/schedule";
       	} else if ("Z000".equals(arrayMenus[1])) {
       		sViewName = "/account";
       	}
    	
		return "redirect:" + sViewName;
    }

    /**
     * 로그인 아웃
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        
        try { request.getSession(true).invalidate(); } catch(Exception e) {}

        return "redirect:/";
    }
    
}
