package com.cyberone.scourt.rss.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.scourt.model.RssFeed;
import com.cyberone.scourt.model.RssNews;
import com.cyberone.scourt.rss.dao.RssDao;

@Service
public class RssService {
	
	@Autowired
	private RssDao rssDao;
	
	public List<HashMap<String, Object>> selectRssNewsList(RssNews dto) throws Exception {
		return rssDao.selectRssNewsList(dto);
	}
	
	public void insertRssNews(HashMap<String, Object> paramMap) throws Exception {
		rssDao.insertRssNews(paramMap);
	}
	
	public void updateRssFeed(HashMap<String, Object> paramMap) {
		rssDao.updateRssFeed(paramMap);
	}

	public void insertRssFeed(HashMap<String, Object> paramMap) {
		rssDao.insertRssFeed(paramMap);
	}

	public void deleteRssFeed(HashMap<String, Object> paramMap) {
		rssDao.deleteRssFeed(paramMap);
	}

	public List<HashMap<String, Object>> selectRssFeedList(RssFeed dto) throws Exception {
		return rssDao.selectRssFeedList(dto);
	}
	
	public HashMap<String, Object> selectRssFeed(HashMap<String, Object> paramMap) throws Exception {
		return rssDao.selectRssFeed(paramMap);
	}
}
