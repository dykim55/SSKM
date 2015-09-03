package com.cyberone.scourt.login.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.scourt.login.dao.LoginDao;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.MenuAuth;

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
