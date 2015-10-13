package com.cyberone.sskm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cyberone.sskm.exception.BizException;
import com.cyberone.sskm.model.UserInfo;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	private static int contextNameLength	= -1;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
    		HttpServletResponse response, Object handler) throws Exception {

		if (contextNameLength == -1) {
			contextNameLength = request.getContextPath().length();
		}
		String requestedUrl = request.getRequestURI().substring(contextNameLength);

    	if (requestedUrl.indexOf("verify") > -1 || requestedUrl.indexOf("login") > -1) {
    		return true;
    	}

    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
		if (userInfo == null) {
			throw new BizException("9999", "로그인정보가 없습니다. 다시 로그인 하십시요.");
		}
		
        request.setAttribute("userInfo", userInfo);
		
        return true;
    }


}
