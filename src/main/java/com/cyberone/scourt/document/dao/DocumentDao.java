package com.cyberone.scourt.document.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, Object>> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Document.selectCategory", paramMap);
	}
	
}
