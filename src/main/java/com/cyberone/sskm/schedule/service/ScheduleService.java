package com.cyberone.sskm.schedule.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberone.sskm.schedule.dao.ScheduleDao;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	public List<HashMap<String, Object>> selectScheduleList(HashMap<String, Object> paramMap) throws Exception {
		return scheduleDao.selectScheduleList(paramMap);
	}
	
	public HashMap<String, Object> selectSchedule(HashMap<String, Object> paramMap) throws Exception {
		return scheduleDao.selectSchedule(paramMap);
	}
	
	public void updateSchedule(HashMap<String, Object> paramMap) throws Exception {
		scheduleDao.updateSchedule(paramMap);
	}
	
	public void insertSchedule(HashMap<String, Object> paramMap) throws Exception {
		scheduleDao.insertSchedule(paramMap);
	}

	public void deleteSchedule(HashMap<String, Object> paramMap) throws Exception {
		scheduleDao.deleteSchedule(paramMap);
	}
	
}
