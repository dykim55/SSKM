package com.cyberone.scourt.document.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.Product;

@Repository
public class DocumentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, Object>> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Document.selectCategory", paramMap);
	}

	public List<HashMap<String, Object>> selectProductList(Product dto) throws Exception {
		dto.searchRowCount(sqlSession, "Document.selectProductList-Count");
		return sqlSession.selectList("Document.selectProductList", dto);
	}
	
	public void insertCategory(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Document.insertCategory", paramMap);
	}

	public void updateCategory(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Document.updateCategory", paramMap);
	}	

	public void insertProduct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Document.insertProduct", paramMap);
	}
	
	public void updateProduct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Document.updateProduct", paramMap);
	}	
	
	public int insertDcmtFile(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.insert("Document.insertDcmtFile", paramMap);
	}
	
}
