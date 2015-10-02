package com.cyberone.scourt.article.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.AppxFile;
import com.cyberone.scourt.model.Board;

@Repository
public class ArticleDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, Object>> selectBoardList(Board dto) throws Exception {
		dto.searchRowCount(sqlSession, "Board.selectBoardList-Count");
		return sqlSession.selectList("Board.selectBoardList", dto);
	}
	
	public HashMap<String, Object> selectBoard(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Board.selectBoard", paramMap);
	}
	
	public List<AppxFile> selectAppxFiles(AppxFile appxFile) throws Exception {
		return sqlSession.selectList("Board.selectAppxFile", appxFile);
	}
	
	public void insertBoard(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Board.insertBoard", paramMap);
	}
	
	public void updateBoard(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Board.updateBoard", paramMap);
	}	
	
	public int writeAppxFile(AppxFile appxFile) throws Exception {
		return sqlSession.insert("Board.insertAppxFile", appxFile);
	}

	public int deleteAppxById(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.delete("Board.deleteAppxById", paramMap);
	}

	public int deleteAppxByRef(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.delete("Board.deleteAppxByRef", paramMap);
	}
	
}
