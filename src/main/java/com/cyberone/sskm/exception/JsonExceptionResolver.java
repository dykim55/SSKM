package com.cyberone.sskm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.cyberone.sskm.utils.StringUtil;

public class JsonExceptionResolver extends AbstractHandlerExceptionResolver  {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected ModelAndView doResolveException(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			Exception ex) {

		ex.printStackTrace();
		
		if (ex instanceof BizException) {
			if ((request.getHeader("X-Requested-With") != null && 
				request.getHeader("X-Requested-With").toLowerCase().equals("xmlhttprequest"))) {
				return _resolveException("jsonView", ex);	
			} else {
				return _resolveException("error/error", ex);
			}
		} else {
			if ((request.getHeader("X-Requested-With") != null && 
				 request.getHeader("X-Requested-With").toLowerCase().equals("xmlhttprequest"))) {
				return _resolveException("jsonView", null);	
			} else {
				return _resolveException("error/error1", null);
			}
		}
	}

	private ModelAndView _resolveException(String viewName, Exception ex) {
		
		String sCode = "";
		String sMessage = "";
		
		if (ex != null) {
			if (ex instanceof BizException) {
				sCode = ((BizException)ex).getErrorCode();
				sMessage = ((BizException)ex).getErrorMessage();
			} else {
				sMessage = "요청처리 중 예외가 발생했습니다. 관리자에게 문의하세요.";
			}
		} else {
			sMessage = "요청처리 중 예외가 발생했습니다. 관리자에게 문의하세요.";
		}
		
		if (StringUtil.isEmpty(sMessage)) {
			sMessage = "오류가 발생했습니다. 관리자에게 문의하세요.";
		}
		
		logger.debug("ERROR CODE: " + sCode);
		logger.debug("ERROR MSG: " + sMessage);
		
		if (sCode.equals("9999")) {
			viewName = "error/error2";
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		
		modelAndView.addObject("code", sCode);
		modelAndView.addObject("message", sMessage);
		modelAndView.addObject("status", "fail");
		
		return modelAndView;
	}
	
}
