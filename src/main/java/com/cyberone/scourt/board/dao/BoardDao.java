package com.cyberone.scourt.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.AppxFile;
import com.cyberone.scourt.model.Board;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, Object>> selectBoardList(Board dto) throws Exception {
		dto.searchRowCount(sqlSession, "Board.selectBoardList-Count");
		return sqlSession.selectList("Board.selectBoardList", dto);
	}
	
	public HashMap<String, Object> selectBoard(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Board.selectBoard", paramMap);
	}

	public void insertBoard(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Board.insertBoard", paramMap);
	}

	public void updateBoard(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Board.updateBoard", paramMap);
	}	

	public void updateAppxFile(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Board.updateAppxFile", paramMap);
	}	
	
	public int writeAppxFile(AppxFile appxFile) throws Exception {
		return sqlSession.insert("Board.insertAppxFile", appxFile);
	}
	
	public List<AppxFile> selectAppxFiles(AppxFile appxFile) throws Exception {
		return sqlSession.selectList("Board.selectAppxFile", appxFile);
	}
	
	public void insertAttachments(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.insert("Board.insertAttachments", paramMap);
	}

	public void updateAttachments(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Board.updateAttachments", paramMap);
	}
	
	public List<HashMap<String, Object>> selectAttachments(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Board.selectAttachments", paramMap);
	}

	public void deleteAttachments(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.delete("Board.deleteAttachments", paramMap);
	}	

	public void updateQryCnt(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Board.updateQryCnt", paramMap);
	}
	
}
