package com.cyberone.scourt.files.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.Files;
import com.cyberone.scourt.model.Product;

@Repository
public class FilesDao {

	@Autowired
	private SqlSession sqlSession;

	public HashMap<String, Object> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Files.selectCategory", paramMap);
	}

	public List<HashMap<String, Object>> selectCategoryList(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Files.selectCategoryList", paramMap);
	}
	
	public List<HashMap<String, Object>> selectCategoryTree(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Files.selectCategoryTree", paramMap);
	}

	public List<HashMap<String, Object>> selectProductList(Product dto) throws Exception {
		dto.searchRowCount(sqlSession, "Files.selectProductList-Count");
		return sqlSession.selectList("Files.selectProductList", dto);
	}

	public HashMap<String, Object> selectProduct(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Files.selectProduct", paramMap);
	}
	
	public int deleteProduct(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.delete("Files.deleteProduct", paramMap);
	}
	
	public void insertCategory(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Files.insertCategory", paramMap);
	}

	public void updateCategory(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Files.updateCategory", paramMap);
	}	

	public void deleteCategory(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.delete("Files.deleteCategory", paramMap);
	}
	
	public int insertFiles(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.insert("Files.insertFiles", paramMap);
	}

	public int deleteFilesById(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.delete("Files.deleteFilesById", paramMap);
	}

	public int deleteFilesByRef(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.delete("Files.deleteFilesByRef", paramMap);
	}

	public List<Files> selectFiles(Files files) throws Exception {
		return sqlSession.selectList("Files.selectFiles", files);
	}
	
	public void insertProduct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Files.insertProduct", paramMap);
	}
	
	public void updateProduct(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Files.updateProduct", paramMap);
	}	

	public List<HashMap<String, Object>> selectMenu(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Files.selectMenu", paramMap);
	}
	
}
