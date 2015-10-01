package com.cyberone.scourt.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberone.scourt.Common;
import com.cyberone.scourt.Constants;
import com.cyberone.scourt.model.UserInfo;

public class SessionMonitorListener implements HttpSessionListener {

	private static int totalActiveSessions;
	
	private static HashMap<String, UserInfo> userMap = new HashMap<String, UserInfo>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static int getTotalActiveSession(){
		return totalActiveSessions;
	}
	
	public static void addUserInfo(UserInfo userInfo, HttpSession session) {
		userMap.put(session.getId(), userInfo);
	}
	
	public static boolean isLogined(String id) {
		for (Map.Entry<String, UserInfo> e : userMap.entrySet()) {
			UserInfo userInfo = e.getValue();
			if (id.equals(userInfo.getAcct().getAcctId())) {
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
		try {
			UserInfo userInfo = (UserInfo)userMap.remove(arg0.getSession().getId());
			Common.insertAuditHist(Constants.AUDIT_LOGIN, userInfo.getAcct().getAcctNm() + "님이 로그아웃하셨습니다.", "S", "", userInfo.getAcct().getAcctId());
		} catch (Exception e) {}
		
		logger.debug("sessionDestroyed {} {}", arg0.getSession().getId(), (new Date()).toLocaleString());
		logger.debug(userMap.toString());
		totalActiveSessions--;
	}	
}