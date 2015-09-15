package com.cyberone.scourt.account.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.Common;
import com.cyberone.scourt.account.service.AccountService;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.StringUtil;


@Controller
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AccountService accountService;

    @RequestMapping("/account")
    public String account(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	List<HashMap<String, Object>> acctList = accountService.selectAcctGrpTree(paramMap);

    	model.addAttribute("acctList", acctList);
        
        return "/account/account";
    }

    @RequestMapping("/account/create_auth")
    public String createAuthGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sAuthGrp = request.getParameter("auth_grp");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpCd", sAuthGrp);
    	AcctGrp authInfo = accountService.selectAcctGrp(paramMap);
    	
    	model.addAttribute("authInfo", authInfo);
    	
    	return "/account/create_auth";
    }
    
    @RequestMapping("/account/create_group")
    public String createAcctGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	List<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	
    	String sAcctGrpCd = request.getParameter("acct_grp");
    	
    	AcctGrp acctGrpInfo = null;
    	if (StringUtil.isEmpty(sAcctGrpCd)) {
    		acctGrpInfo = new AcctGrp();
    	} else {
	    	paramMap.put("acctGrpCd", sAcctGrpCd);
	    	acctGrpInfo = accountService.selectAcctGrp(paramMap);
    	}
    	
    	paramMap.put("prtsCd", "0");
    	List<HashMap<String, Object>> menuList_1st = accountService.selectMenu(paramMap);
    	for (HashMap<String, Object> hMap : menuList_1st) {
    		menuList.add(hMap);
    		paramMap.put("prtsCd", hMap.get("menuCd"));
    		List<HashMap<String, Object>> menuList_2nd = accountService.selectMenu(paramMap);
    		for (HashMap<String, Object> pMap : menuList_2nd) {
    			menuList.add(pMap);
    		}
    	}
    	
    	model.addAttribute("acctGrpInfo", acctGrpInfo);
    	model.addAttribute("menuList", menuList);
    	
    	return "/account/create_group";
    }
    
    @RequestMapping("/account/create_account")
    public String createAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sAcctId = request.getParameter("id");
    	String sAuthGrp = request.getParameter("auth_grp");
    	String sAcctGrp = request.getParameter("acct_grp");
    	
    	if (StringUtil.isEmpty(sAcctId)) {
    		return "/account/create_account";
    	}
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctId", sAcctId);
    	Acct acctInfo = accountService.selectAcct(paramMap);
    	
    	model.addAttribute("acctInfo", acctInfo);
    	
    	return "/account/create_account";
    }

    @RequestMapping("/account/register_auth_group")
    public ModelAndView registerAuthGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sAuthGrpId = request.getParameter("id");
    	String sAuthGrpName = request.getParameter("name");
    	String sAuthGrpDesc = request.getParameter("desc");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpNm", sAuthGrpName);
    	paramMap.put("acctGrpDesc", sAuthGrpDesc);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());
    	
    	if (StringUtil.isEmpty(sAuthGrpId) || Integer.valueOf(sAuthGrpId) == 0) {
    		paramMap.put("acctPrntCd", 1);
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());

    		accountService.insertAcctGrp(paramMap);
    	} else {
    		paramMap.put("acctGrpCd", Integer.valueOf(sAuthGrpId));
    		
    		accountService.updateAcctGrp(paramMap);
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("id", paramMap.get("acctGrpCd"));
    	return modelAndView.addObject("status", "success"); 
    }

    @RequestMapping("/account/register_acct_group")
    public ModelAndView registerAcctGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sAuthGrpId = request.getParameter("id");
    	String sParent = request.getParameter("prts");
    	String sAuthGrpName = request.getParameter("name");
    	String sAuthGrpDesc = request.getParameter("desc");
    	String sPrmsMenus = request.getParameter("auth");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpNm", sAuthGrpName);
    	paramMap.put("acctGrpDesc", sAuthGrpDesc);
    	paramMap.put("acctPrmsMenus", sPrmsMenus);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());
    	
    	if (StringUtil.isEmpty(sAuthGrpId) || Integer.valueOf(sAuthGrpId) == 0) {
    		paramMap.put("acctPrntCd", Integer.valueOf(sParent));
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());

    		accountService.insertAcctGrp(paramMap);
    	} else {
    		paramMap.put("acctGrpCd", Integer.valueOf(sAuthGrpId));
    		
    		accountService.updateAcctGrp(paramMap);
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("id", paramMap.get("acctGrpCd"));
    	return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping("/account/register_account")
    public ModelAndView registerAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sAcctId = request.getParameter("id");
    	String sAcctNm = request.getParameter("name");
    	String sPw = request.getParameter("pw");
    	String sPwCf = request.getParameter("pw_cf");
    	String sAuthGrp = request.getParameter("auth_grp");
    	String sAcctGrp = request.getParameter("acct_grp");
    	String sDept = request.getParameter("dept");
    	String sOflv = request.getParameter("oflv");
    	String sEmail = request.getParameter("email");
    	String sMobile = request.getParameter("mobile");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctId", sAcctId);
    	Acct acct = accountService.selectAcct(paramMap);

    	paramMap.put("acctNm", sAcctNm);
    	paramMap.put("acctGrpCd", Integer.valueOf(sAcctGrp));
    	paramMap.put("deptCd", sDept);
    	paramMap.put("oflvCd", sOflv);
    	paramMap.put("acctPw", sPw);
    	paramMap.put("mobile", sMobile);
    	paramMap.put("email", sEmail);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (StringUtil.isEmpty(acct)) {
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		accountService.insertAcct(paramMap);
    	} else {
    		accountService.updateAcct(paramMap);
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("id", paramMap.get("acctGrpCd"));
    	return modelAndView.addObject("status", "success"); 
    }

    
    @RequestMapping("/account_ajax")
    public String accountAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sGrpCd = request.getParameter("grpCd");
    	String sPrtsCd= StringUtil.nullToStr(request.getParameter("prtsCd"), "0");
    	
    	Acct dto = new Acct();
    	if (sPrtsCd.equals("0")) {
    		dto.setAcctGrpCd(-1);
    	} else if (sPrtsCd.equals("1")) {
    		dto.setAcctGrpCd(0);
    		dto.setAuthGrp(Integer.valueOf(sGrpCd));
    	} else {
    		dto.setAcctGrpCd(Integer.valueOf(sGrpCd));
    	}
       	
    	List<HashMap<String, Object>> acctList = accountService.selectAccountList(dto);
       	
    	model.addAllAttributes(dto.createModelMap(acctList));
    	 
    	return "/account/account_ajax";
    }
 
    @RequestMapping("/account/tree_ajax")
    public String treeAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
       	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	List<HashMap<String, Object>> acctList = accountService.selectAcctGrpTree(paramMap);
       	
        model.addAttribute("acctList", acctList);
        
        return "/account/tree_ajax";
    }
    
    @RequestMapping("/account/selectView")
    public void selectDeptView(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	String name = request.getParameter("name");
    	String grpSct = request.getParameter("grpSct");
    	String first = "선택하세요";
    	
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println(Common.acctGrpSelect(name, first, 0, Integer.valueOf(grpSct)));
    	out.close();
    }
    
    
}