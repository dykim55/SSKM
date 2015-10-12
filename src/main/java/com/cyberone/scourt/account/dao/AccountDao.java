package com.cyberone.scourt.account.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.Acct;
import com.cyberone.scourt.model.AcctGrp;

@Repository
public class AccountDao {

	@Autowired
	private SqlSession sqlSession;

	public Acct selectAcct(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.selectAcct", paramMap);
	}

	public void insertAcct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Account.insertAcct", paramMap);
	}

	public void updateAcct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Account.updateAcct", paramMap);
	}

	public AcctGrp selectAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Account.selectAcctGrp", paramMap);
	}
	
	public List<HashMap<String, Object>> selectAcctGrpTree(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Account.selectAcctGrpTree", paramMap);
	}
	
	public void insertAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Account.insertAcctGrp", paramMap);
	}

	public void updateAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Account.updateAcctGrp", paramMap);
	}

	public void deleteAcctGrp(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.delete("Account.deleteAcctGrp", paramMap);
	}

	public List<HashMap<String, Object>> selectAccountList(Acct dto) throws Exception {
		dto.searchRowCount(sqlSession, "Account.selectAccountList-Count");
		return sqlSession.selectList("Account.selectAccountList", dto);
	}

	public List<HashMap<String, Object>> selectMenu(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Files.selectMenu", paramMap);
	}
	
}
