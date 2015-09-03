package com.cyberone.scourt.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.scourt.exception.BizException;
import com.cyberone.scourt.model.UserInfo;
import com.cyberone.scourt.schedule.service.ScheduleService;
import com.cyberone.scourt.utils.StringUtil;

@Controller
public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("schedule")
    public ModelAndView schedule(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	ModelAndView modelAndView = new ModelAndView("/schedule/schedule");
    	return modelAndView.addObject("status", "success");
    }

    @RequestMapping("scheduleWrite")
    public ModelAndView regSchedule(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	String schdSeq = request.getParameter("schd_seq");
    	String schdSct = request.getParameter("schd_sct");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("schdSeq", schdSeq);
    	paramMap.put("schdSct", schdSct);
    	
    	HashMap<String, Object> schedule = scheduleService.selectSchedule(paramMap);
    	
    	ModelAndView modelAndView = new ModelAndView("/schedule/scheduleWrite");
    	modelAndView.addObject("schedule", schedule);
    	
    	return modelAndView.addObject("status", "success");
    }
    
    @RequestMapping("scheduleList")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	String sSearchMonth = StringUtil.convertString(request.getParameter("searchMonth"));
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("searchMonth", sSearchMonth);
    	
    	List<HashMap<String, Object>> scheduleList = scheduleService.selectScheduleList(paramMap);
    	
    	modelAndView.addObject("list", scheduleList);
    	
    	return modelAndView.addObject("status", "success");
    }

    @RequestMapping("scheduleReg")
    public ModelAndView scheduleReg(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());
    	
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	String schdSeq = request.getParameter("schd_seq");
    	String schdSct = request.getParameter("schd_sct");
    	
    	String sSDate = request.getParameter("schd_sdate");
    	String sEDate = request.getParameter("schd_edate");
    	String sSTime = request.getParameter("schd_stime");
    	String sETime = request.getParameter("schd_etime");
    	String sCont = request.getParameter("schd_cont");

    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    	Calendar pCal = new GregorianCalendar( TimeZone.getTimeZone( "GMT+09:00"), Locale.KOREA );

		paramMap.put("schdSct", StringUtil.nullToStr(schdSct, "1"));
		
		Date pDate = sdf.parse(StringUtil.convertString(sSDate));
		pCal.setTime(pDate);
		paramMap.put("strtDate", pCal.getTime());
		
		pDate = sdf.parse(StringUtil.convertString(sEDate));
		pCal.setTime(pDate);
		paramMap.put("endDate", pCal.getTime());
		
		pDate = sdf2.parse(StringUtil.convertString(sSTime));
		pCal.setTime(pDate);
		paramMap.put("strtTime", pCal.getTime());
		
		pDate = sdf2.parse(StringUtil.convertString(sETime));
		pCal.setTime(pDate);
		paramMap.put("endTime", pCal.getTime());

		paramMap.put("schdCont", StringUtil.replaceHtml(sCont));
		paramMap.put("modr", userInfo.getAcct().getAcctId());
		paramMap.put("modDtime", new Date());
    	
    	if (StringUtil.isEmpty(schdSeq)) {
    		paramMap.put("regr", userInfo.getAcct().getAcctId());
    		paramMap.put("regDtime", new Date());
    		scheduleService.insertSchedule(paramMap);
    	} else {
        	paramMap.put("schdSeq", schdSeq);
        	scheduleService.updateSchedule(paramMap);
    	}
    	
    	return modelAndView.addObject("status", "success");
    }

    @RequestMapping("scheduleDel")
    public ModelAndView scheduleDel(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	logger.debug(request.getServletPath());

    	String schdSeq = request.getParameter("schd_seq");
    	String schdSct = request.getParameter("schd_sct");

    	if (StringUtil.isEmpty(schdSeq)) {
    		throw new BizException("잘못된 요청입니다. 잠시 후 다시 시도해 보세요.");	
    	}
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("schdSeq", schdSeq);
    	paramMap.put("schdSct", StringUtil.nullToStr(schdSct, "1"));
    	
    	scheduleService.deleteSchedule(paramMap);
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	return modelAndView.addObject("status", "success");
    }
    
}
