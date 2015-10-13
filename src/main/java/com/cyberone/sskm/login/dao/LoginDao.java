package com.cyberone.sskm.login.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.sskm.model.Acct;
import com.cyberone.sskm.model.AcctGrp;
import com.cyberone.sskm.model.MenuAuth;

@Repository
public class LoginDao {

	@Autowired
	private SqlSession sqlSession;

	public Acct verifyAccount(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.verifyAccount", paramMap);
	}

	public Acct selectAcct(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.selectAcct", paramMap);
	}

	public AcctGrp selectAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.selectAcctGrp", paramMap);
	}

	public MenuAuth selectMenuAuth(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.selectMenuAuth", paramMap);
	}
	
}
