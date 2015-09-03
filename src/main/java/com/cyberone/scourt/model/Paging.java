package com.cyberone.scourt.model;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.ModelMap;

public class Paging {

	private int rows = 10;		//페이지당 Row 수
	private int records;		//전체 레코드 수
	private int total;			//전체 페이지 수
	private int page = 1;		//현재 페이지
	private int limit;			//시작순번
	private int group;			//페이지그룹위치
	private int groupPage = 10;	//그룹당페이지수
	private int groupStart; 	//그룹시작위치
	private int groupEnd;   	//그룹마지막위치
	private int prev;			//이전페이지
	private int next;			//다음페이지
	private String sidx;
	private String sord;
	private String searchSel="";
	private String searchWord="";
	/**
	 *  모델 맵 리턴
	 * @param list
	 * @return
	 */
	public ModelMap createModelMap(List<?> list){
		return createModelMap(list,null);
	}
	/**
	 * 모델 맵 리턴
	 * @param list
	 * @param map
	 * @return
	 */
	public ModelMap createModelMap(List<?> list,ModelMap map){
		if(map == null)map = new ModelMap();
		
		map.addAttribute("list", list);
		map.addAttribute("page", page);
		map.addAttribute("rows", rows);
		map.addAttribute("record", records);
		map.addAttribute("total", total);
		map.addAttribute("group", group);
		map.addAttribute("groupPage", groupPage);
		map.addAttribute("groupStart", groupStart);
		map.addAttribute("groupEnd", groupEnd);
		map.addAttribute("prev", prev);
		map.addAttribute("next", next);
		
		return map;
	}
	
	/**
	 * 페이지 카운터 조회
	 * @param connection
	 * @param alias
	 * @throws SQLException
	 */
	public void searchRowCount(SqlSession sqlSession, String alias) throws SQLException {
		Paging paging = sqlSession.selectOne(alias, this);
		records = paging.records;
		if(records > 0 && rows > 0){
			total += (records/rows) + ((records%rows > 0) ? 1 : 0);
		}
		limit = rows * (page - 1); 
		
        if (page > total) {
        	page = total > 0 ? total : 1;
        }

        group = (page / groupPage) + (page % groupPage > 0 ? 1 : 0);
        groupEnd = group * groupPage;
        groupStart = groupEnd - (groupPage - 1) ;
		
        if (groupEnd > total) {
        	groupEnd = total;
        }

        prev = (groupStart/groupPage)*groupPage;
        next = groupStart + groupPage;

        if (prev < 1) {
        	prev = 1;
        }

        if (next > total) {
        	next = total;
        }
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getGroupPage() {
		return groupPage;
	}
	public void setGroupPage(int groupPage) {
		this.groupPage = groupPage;
	}
	public int getGroupStart() {
		return groupStart;
	}
	public void setGroupStart(int groupStart) {
		this.groupStart = groupStart;
	}
	public int getGroupEnd() {
		return groupEnd;
	}
	public void setGroupEnd(int groupEnd) {
		this.groupEnd = groupEnd;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSearchSel() {
		return searchSel;
	}
	public void setSearchSel(String searchSel) {
		this.searchSel = searchSel;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}
