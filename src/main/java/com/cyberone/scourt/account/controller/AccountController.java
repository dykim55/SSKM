package com.cyberone.scourt.account.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.cyberone.scourt.Constants;
import com.cyberone.scourt.account.service.AccountService;
import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.Product;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.utils.DataUtil;
import com.cyberone.scourt.utils.Encryption;
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

    @RequestMapping(value = {"/account/create_auth", "/account/modify_auth"})
    public String createAuthGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sAuthGrp = request.getParameter("grp");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpCd", sAuthGrp);
    	AcctGrp authInfo = accountService.selectAcctGrp(paramMap);
    	
    	model.addAttribute("authInfo", authInfo);
    	
    	return "/account/create_auth";
    }
    
    @RequestMapping("/account/delete_auth")
    public ModelAndView deleteAuthGrp(HttpServletRequest request) throws Exception {
    	logger.debug(request.getServletPath());

    	String sAuthGrp = request.getParameter("grp");

    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpCd", sAuthGrp);
    	AcctGrp authInfo = accountService.selectAcctGrp(paramMap);

    	if (StringUtil.isEmpty(authInfo)) {
    		throw new BizException("삭제할 접근권한그룹을 선택하세요.");
    	}
    	
    	Acct dto = new Acct();
   		dto.setAcctGrpCd(0);
		dto.setAuthGrp(Integer.valueOf(sAuthGrp));
       	
    	List<HashMap<String, Object>> acctList = accountService.selectAccountList(dto);
    	if (acctList.size() > 0) {
    		throw new BizException("계정이 존재하는 접근권한그룹은 삭제할 수 없습니다.");
    	}
    	
    	paramMap.clear();
    	paramMap.put("acctGrpCd", sAuthGrp);
    	accountService.deleteAcctGrp(paramMap);
    	
    	paramMap.clear();
    	paramMap.put("acctPrntCd", sAuthGrp);
    	accountService.deleteAcctGrp(paramMap);
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "접근권한그룹 '" + authInfo.getAcctGrpNm() + "'이 삭제되었습니다.", "S", "", userInfo.getAcct().getAcctId());
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping(value = {"/account/create_group", "/account/modify_group"})
    public String createAcctGrp(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	List<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	
    	String sAcctGrp = request.getParameter("acct_grp");
    	
    	AcctGrp acctGrpInfo = null;
    	if (StringUtil.isEmpty(sAcctGrp)) {
    		acctGrpInfo = new AcctGrp();
    	} else {
	    	paramMap.put("acctGrpCd", sAcctGrp);
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
    
    @RequestMapping("/account/delete_group")
    public ModelAndView deleteAcctGrp(HttpServletRequest request) throws Exception {
    	logger.debug(request.getServletPath());

    	String sAcctGrp = request.getParameter("grp");
    	String sPrtsCd= StringUtil.nullToStr(request.getParameter("prts"), "0");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctGrpCd", sAcctGrp);
    	AcctGrp acctInfo = accountService.selectAcctGrp(paramMap);

    	if (StringUtil.isEmpty(acctInfo)) {
    		throw new BizException("삭제할 계정그룹을 선택하세요.");
    	}
    	
    	Acct dto = new Acct();
   		dto.setAcctGrpCd(Integer.valueOf(sAcctGrp));
       	
    	List<HashMap<String, Object>> acctList = accountService.selectAccountList(dto);
    	if (acctList.size() > 0) {
    		throw new BizException("계정이 존재하는 계정그룹은 삭제할 수 없습니다.");
    	}
    	
    	accountService.deleteAcctGrp(paramMap);
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
        Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "계정그룹 '" + acctInfo.getAcctGrpNm() + "'이 삭제되었습니다.", "S", "", userInfo.getAcct().getAcctId());
        
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("parent", sPrtsCd);
        return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping("/account/create_account")
    public String createAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sAcctId = request.getParameter("id");

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
    	String sAuthGrpName = StringUtil.replaceHtml(request.getParameter("name"));
    	String sAuthGrpDesc = StringUtil.replaceHtml(request.getParameter("desc"));
    	
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
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "접근권한 그룹을 생성하였습니다.", "S", sAuthGrpName, userInfo.getAcct().getAcctId());
    	} else {
    		paramMap.put("acctGrpCd", Integer.valueOf(sAuthGrpId));
    		AcctGrp authInfo = accountService.selectAcctGrp(paramMap);
    		accountService.updateAcctGrp(paramMap);
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "접근권한 그룹을 수정하였습니다.", "S", "이전: " + authInfo.getAcctGrpNm() + "\n이후: " + sAuthGrpName, userInfo.getAcct().getAcctId());
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
    	String sAuthGrpName = StringUtil.replaceHtml(request.getParameter("name"));
    	String sAuthGrpDesc = StringUtil.replaceHtml(request.getParameter("desc"));
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
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "계정그룹을 생성하였습니다.", "S", sAuthGrpName, userInfo.getAcct().getAcctId());
    	} else {
    		paramMap.put("acctGrpCd", Integer.valueOf(sAuthGrpId));

    		String sBefore = "이전\n";
    		String sAfter = "이후\n";
    		AcctGrp authInfo = accountService.selectAcctGrp(paramMap);
    		
    		if (!sAuthGrpName.equals(authInfo.getAcctGrpNm())) {
				sBefore += "계정그룹명: " + authInfo.getAcctGrpNm() + "\n";
				sAfter += "계정그룹명: " + sAuthGrpName + "\n";
    		}
    		if (!sPrmsMenus.equals(authInfo.getAcctPrmsMenus())) {
				sBefore += "메뉴권한: " + authInfo.getAcctPrmsMenus() + "\n";
				sAfter += "메뉴권한: " + sPrmsMenus + "\n";
    		}
    		accountService.updateAcctGrp(paramMap);
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "계정그룹을 수정하였습니다.", "S", sBefore + "\n\n" +sAfter, userInfo.getAcct().getAcctId());
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("id", paramMap.get("acctGrpCd"));
    	return modelAndView.addObject("status", "success"); 
    }
    
    @RequestMapping(value = {"/account/register_account", "/account/modify_account"})
    public ModelAndView registerAccount(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	String sAcctId = request.getParameter("id");
    	String sAcctNm = StringUtil.replaceHtml(request.getParameter("name"));
    	String sPw = request.getParameter("pw");
    	String sPwCf = request.getParameter("pw_cf");
    	String sAuthGrp = request.getParameter("auth_grp");
    	String sAcctGrp = request.getParameter("acct_grp");
    	String sDept = StringUtil.replaceHtml(request.getParameter("dept"));
    	String sOflv = StringUtil.replaceHtml(request.getParameter("oflv"));
    	String sEmail = StringUtil.replaceHtml(request.getParameter("email"));
    	String sMobile = StringUtil.replaceHtml(request.getParameter("mobile"));
    	
		if (StringUtil.isEmpty(sAcctId)) {
			throw new BizException("아이디를 입력하세요.");
		}
    	
		Pattern pattern = Pattern.compile(Constants.ACCTID_PATTERN);
        Matcher matcher = pattern.matcher(sAcctId);
        if (!matcher.matches()) {
        	throw new BizException("아이디는 영문자 또는 숫자조합으로 5자부터 16자까지 가능합니다.");
        }
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("acctId", sAcctId);

    	Acct acct = accountService.selectAcct(paramMap);
    	
    	if (request.getServletPath().equals("/account/register_account") && acct != null) {
    		throw new BizException("사용중인 아이디입니다.");
    	}
    	
    	if (!sPw.isEmpty() && !sPwCf.isEmpty()) {
			if (!sPw.equals(sPwCf)) {
				throw new BizException("비밀번호확인이 틀림니다.");
			}
			pattern = Pattern.compile(Constants.PASSWORD_PATTERN);
			matcher = pattern.matcher(sPw);
	        if (!matcher.matches()) {
	        	throw new BizException("비밀번호는 8~16자리의 영문 대/소문자,숫자,특수문자를 조합하여 사용해야 합니다.");
	        }
	        paramMap.put("acctPw", Encryption.encrypt(sPw));
    	}
    	
    	paramMap.put("acctNm", sAcctNm);
    	paramMap.put("acctGrpCd", Integer.valueOf(sAcctGrp));
    	paramMap.put("deptNm", sDept);
    	paramMap.put("oflvNm", sOflv);
    	paramMap.put("mobile", sMobile);
    	paramMap.put("email", sEmail);
    	paramMap.put("modr", userInfo.getAcct().getAcctId());
    	paramMap.put("modDtime", new Date());

    	if (StringUtil.isEmpty(acct)) {
        	paramMap.put("regr", userInfo.getAcct().getAcctId());
        	paramMap.put("regDtime", new Date());
    		accountService.insertAcct(paramMap);
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "계정을 생성하였습니다.", "S", sAcctId + "(" + sAcctNm + ")", userInfo.getAcct().getAcctId());
    	} else {
    		String sBefore = "이전\n";
    		String sAfter = "이후\n";

    		if (Integer.valueOf(sAcctGrp) != acct.getAcctGrpCd()) {
				sBefore += "계정그룹: " + acct.getAcctGrpCd() + "\n";
				sAfter += "계정그룹: " + sAcctGrp + "\n";
    		}

    		if (!Encryption.encrypt(sPw).equals(acct.getAcctPw())) {
				sBefore += "비밀번호:\n";
				sAfter += "비밀번호: 변경되었습니다.\n";
    		}

    		accountService.updateAcct(paramMap);
    		Common.insertAuditHist(Constants.AUDIT_ACCOUNT, "계정을 수정하였습니다.", "S", sBefore + "\n\n" +sAfter, userInfo.getAcct().getAcctId());
    	}
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	modelAndView.addObject("id", paramMap.get("acctGrpCd"));
    	return modelAndView.addObject("status", "success"); 
    }

    
    @RequestMapping("/account/account_ajax")
    public String accountAjax(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String sGrpCd = request.getParameter("grp");
    	String sPrtsCd= StringUtil.nullToStr(request.getParameter("prts"), "0");
    	
    	Acct dto = (Acct)DataUtil.dtoBuilder(request, Acct.class);
    	
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