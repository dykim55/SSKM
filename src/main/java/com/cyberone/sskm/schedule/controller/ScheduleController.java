package com.cyberone.sskm.schedule.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cyberone.sskm.exception.BizException;
import com.cyberone.sskm.model.UserInfo;
import com.cyberone.sskm.schedule.service.ScheduleService;
import com.cyberone.sskm.utils.StringUtil;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 일정관리 페이지 요청
     */
    @RequestMapping("schedule")
    public ModelAndView schedule(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("/schedule/schedule");
    	return modelAndView.addObject("status", "success");
    }

    /**
     * 일정등록 페이지 요청
     */
    @RequestMapping("/schedule/schedule_write")
    public ModelAndView scheduleWrite(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	String schdSeq = request.getParameter("schd_seq");
    	String schdSct = request.getParameter("schd_sct");
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("schdSeq", schdSeq);
    	paramMap.put("schdSct", schdSct);
    	
    	HashMap<String, Object> schedule = scheduleService.selectSchedule(paramMap);
    	
    	ModelAndView modelAndView = new ModelAndView("/schedule/schedule_write");
    	modelAndView.addObject("schedule", schedule);
    	
    	return modelAndView.addObject("status", "success");
    }
    
    /**
     * 일정 목록 조회
     */
    @RequestMapping("/schedule/schedule_list")
    public ModelAndView scheduleList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	ModelAndView modelAndView = new ModelAndView("jsonView");
    	
    	String sSearchMonth = StringUtil.convertString(request.getParameter("searchMonth"));
    	
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("searchMonth", sSearchMonth);
    	
    	List<HashMap<String, Object>> scheduleList = scheduleService.selectScheduleList(paramMap);
    	
    	modelAndView.addObject("list", scheduleList);
    	
    	return modelAndView.addObject("status", "success");
    }

    /**
     * 일정 등록/수정
     */
    @RequestMapping("/schedule/schedule_register")
    public ModelAndView scheduleRegister(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
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
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	
		String sDate = sSDate + " " + sSTime;
		String eDate = sEDate + " " + sETime;

		Date startDate = sdf.parse(sDate);
		Date endDate = sdf.parse(eDate);
		
		if (endDate.compareTo(startDate) < 0) {
			throw new BizException("시작일이 종료일 보다 큽니다.");
		}
    	
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    	Calendar pCal = new GregorianCalendar( TimeZone.getTimeZone( "GMT+09:00"), Locale.KOREA );

		paramMap.put("schdSct", StringUtil.nullToStr(schdSct, "1"));
		
		Date pDate = sdf1.parse(StringUtil.convertString(sSDate));
		pCal.setTime(pDate);
		paramMap.put("strtDate", pCal.getTime());
		
		pDate = sdf1.parse(StringUtil.convertString(sEDate));
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

    /**
     * 일정 삭제
     */
    @RequestMapping("/schedule/schedule_delete")
    public ModelAndView scheduleDelete(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

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
