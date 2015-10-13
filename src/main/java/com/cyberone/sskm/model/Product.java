package com.cyberone.sskm.model;

import java.util.Date;

public class Product extends Paging {

	private int pId;
	private String title;
	private String content;
	private int query;
	private int parent;
	private String fileYn;
	private String dleYn;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;
	
	private String startDtime;
	private String endDtime;

	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getQuery() {
		return query;
	}
	public void setQuery(int query) {
		this.query = query;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public String getFileYn() {
		return fileYn;
	}
	public void setFileYn(String fileYn) {
		this.fileYn = fileYn;
	}
	public String getDleYn() {
		return dleYn;
	}
	public void setDleYn(String dleYn) {
		this.dleYn = dleYn;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public Date getRegDtime() {
		return regDtime;
	}
	public void setRegDtime(Date regDtime) {
		this.regDtime = regDtime;
	}
	public String getModr() {
		return modr;
	}
	public void setModr(String modr) {
		this.modr = modr;
	}
	public Date getModDtime() {
		return modDtime;
	}
	public void setModDtime(Date modDtime) {
		this.modDtime = modDtime;
	}
	public String getStartDtime() {
		return startDtime;
	}
	public void setStartDtime(String startDtime) {
		this.startDtime = startDtime;
	}
	public String getEndDtime() {
		return endDtime;
	}
	public void setEndDtime(String endDtime) {
		this.endDtime = endDtime;
	}

	
}
