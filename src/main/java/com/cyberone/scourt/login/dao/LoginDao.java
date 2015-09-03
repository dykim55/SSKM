package com.cyberone.scourt.login.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;
import com.cyberone.scourt.model.MenuAuth;

@Repository
public class LoginDao {

	@Autowired
	private SqlSession sqlSession;

	public Acct verifyAccount(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Login.verifyAccount", paramMap);
	}

	public Acct selectAcct(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Login.selectAcct", paramMap);
	}

	public AcctGrp selectAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Login.selectAcctGrp", paramMap);
	}

	public MenuAuth selectMenuAuth(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Login.selectMenuAuth", paramMap);
	}
	
}
