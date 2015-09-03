package com.cyberone.scourt.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.scourt.main.dao.MainDao;

@Service
public class MainService {
	
	@Autowired
	private MainDao centermainDao;
	
}
