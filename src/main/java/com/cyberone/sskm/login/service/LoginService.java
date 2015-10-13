package com.cyberone.sskm.login.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.sskm.login.dao.LoginDao;
import com.cyberone.sskm.model.Acct;
import com.cyberone.sskm.model.AcctGrp;
import com.cyberone.sskm.model.MenuAuth;

@Service
public class LoginService {
	
	@Autowired
	private LoginDao loginDao;
	
	public Acct verifyAccount(HashMap<String, Object> paramMap) throws Exception {
		return loginDao.verifyAccount(paramMap);
	}

	public Acct selectAcct(HashMap<String, Object> paramMap) throws Exception {
		return loginDao.selectAcct(paramMap);
	}

	public AcctGrp selectAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		return loginDao.selectAcctGrp(paramMap);
	}

	public MenuAuth selectMenuAuth(HashMap<String, Object> paramMap) throws Exception {
		return loginDao.selectMenuAuth(paramMap);
	}
	
}
