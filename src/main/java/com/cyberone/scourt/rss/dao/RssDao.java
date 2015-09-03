package com.cyberone.scourt.rss.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberone.scourt.model.RssFeed;
import com.cyberone.scourt.model.RssNews;

@Repository
public class RssDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<HashMap<String, Object>> selectRssNewsList(RssNews dto) throws Exception {
		dto.searchRowCount(sqlSession, "Rss.selectRssNewsList-Count");
		return sqlSession.selectList("Rss.selectRssNewsList", dto);
	}
	
	public void insertRssNews(HashMap<String, Object> paramMap) {
		sqlSession.insert("Rss.insertRssNews", paramMap);
	}

	public HashMap<String, Object> selectRssFeed(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Rss.selectRssFeed", paramMap);
	}
	
	public List<HashMap<String, Object>> selectRssFeedList(RssFeed dto) throws Exception {
		dto.searchRowCount(sqlSession, "Rss.selectRssFeedList-Count");
		return sqlSession.selectList("Rss.selectRssFeedList", dto);
	}

	
	public void insertRssFeed(HashMap<String, Object> paramMap) {
		sqlSession.insert("Rss.insertRssFeed", paramMap);
	}

	public void updateRssFeed(HashMap<String, Object> paramMap) {
		sqlSession.update("Rss.updateRssFeed", paramMap);
	}

	public void deleteRssFeed(HashMap<String, Object> paramMap) {
		sqlSession.delete("Rss.deleteRssFeed", paramMap);
	}
	
}
