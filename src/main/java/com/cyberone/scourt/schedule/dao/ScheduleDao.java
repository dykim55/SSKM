package com.cyberone.scourt.schedule.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao {

	@Autowired
	private SqlSession sqlSession;

	public List<HashMap<String, Object>> selectScheduleList(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectList("Schedule.selectScheduleList", paramMap);
	}

	public HashMap<String, Object> selectSchedule(HashMap<String, Object> paramMap) throws Exception {
		return sqlSession.selectOne("Schedule.selectSchedule", paramMap);
	}
	
	public void updateSchedule(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Schedule.updateSchedule", paramMap);
	}	

	public void insertSchedule(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.update("Schedule.insertSchedule", paramMap);
	}	

	public void deleteSchedule(HashMap<String, Object> paramMap) throws Exception {
		sqlSession.delete("Schedule.deleteSchedule", paramMap);
	}	
	
}
