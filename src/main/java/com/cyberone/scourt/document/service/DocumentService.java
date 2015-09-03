package com.cyberone.scourt.document.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.scourt.document.dao.DocumentDao;

@Service
public class DocumentService {
	
	@Autowired
	private DocumentDao docDao;

	public List<HashMap<String, Object>> selectCategory(HashMap<String, Object> paramMap) throws Exception {
		return docDao.selectCategory(paramMap);
	}
	
}
