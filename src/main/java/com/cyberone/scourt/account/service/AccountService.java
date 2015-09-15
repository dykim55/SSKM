package com.cyberone.scourt.account.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.scourt.account.dao.AccountDao;
import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;

@Service
public class AccountService {
	
	@Autowired
	private AccountDao accountDao;

	public Acct selectAcct(HashMap<String, Object> paramMap) throws Exception {
		return accountDao.selectAcct(paramMap);
	}

	public void insertAcct(HashMap<String, Object> paramMap) throws Exception {
		accountDao.insertAcct(paramMap);
	}

	public void updateAcct(HashMap<String, Object> paramMap) throws Exception {
		accountDao.updateAcct(paramMap);
	}

	public AcctGrp selectAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		return accountDao.selectAcctGrp(paramMap);
	}
	
	public List<HashMap<String, Object>> selectAcctGrpTree(HashMap<String, Object> paramMap) throws Exception {
		return accountDao.selectAcctGrpTree(paramMap);
	}

	public void insertAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		accountDao.insertAcctGrp(paramMap);
	}

	public void updateAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		accountDao.updateAcctGrp(paramMap);
	}
	
	public List<HashMap<String, Object>> selectAccountList(Acct dto) throws Exception {
		return accountDao.selectAccountList(dto);
	}
	
	public List<HashMap<String, Object>> selectMenu(HashMap<String, Object> paramMap) throws Exception {
		return accountDao.selectMenu(paramMap);
	}
}
