package com.cyberone.sskm.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.cyberone.sskm.Common;
import com.cyberone.sskm.Constants;
import com.cyberone.sskm.model.UserInfo;

public class SessionMonitorListener implements HttpSessionListener {

	private static int totalActiveSessions;
	
	private static HashMap<String, UserInfo> userMap = new HashMap<String, UserInfo>();
	
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
		totalActiveSessions++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		try {
			UserInfo userInfo = (UserInfo)userMap.remove(arg0.getSession().getId());
			Common.insertAuditHist(Constants.AUDIT_LOGIN, userInfo.getAcct().getAcctNm() + "님이 로그아웃하셨습니다.", "S", "", userInfo.getAcct().getAcctId());
		} catch (Exception e) {}
		totalActiveSessions--;
	}	
}