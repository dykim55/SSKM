package com.cyberone.scourt.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberone.scourt.model.UserInfo;

public class SessionMonitorListener implements HttpSessionListener {

	private static int totalActiveSessions;
	
	private static HashMap<String, String> userMap = new HashMap<String, String>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static int getTotalActiveSession(){
		return totalActiveSessions;
	}
	
	public static void addUserInfo(UserInfo userInfo, HttpSession session) {
		userMap.put(session.getId(), userInfo.getAcct().getAcctId());
	}
	
	public static boolean isLogined(String id) {
		for (Map.Entry<String, String> e : userMap.entrySet()) {
			if (id.equals(e.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		logger.debug("sessionCreated {} {}", arg0.getSession().getId(), (new Date()).toLocaleString());
		logger.debug(userMap.toString());
		totalActiveSessions++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		userMap.remove(arg0.getSession().getId());
		logger.debug("sessionDestroyed {} {}", arg0.getSession().getId(), (new Date()).toLocaleString());
		logger.debug(userMap.toString());
		totalActiveSessions--;
	}	
}