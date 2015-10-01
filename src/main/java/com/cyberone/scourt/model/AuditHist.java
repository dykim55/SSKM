package com.cyberone.scourt.model;

import java.util.Date;

public class AuditHist extends Paging {

	private int auditCd;
	private String regr;
	private Date regDtime;
	private String workMsg;
	private String workResult;
	private String workMemo;
	private String workSct;
	
	private String startDtime;
	private String endDtime;
	
	public int getAuditCd() {
		return auditCd;
	}
	public void setAuditCd(int auditCd) {
		this.auditCd = auditCd;
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
	public String getWorkMsg() {
		return workMsg;
	}
	public void setWorkMsg(String workMsg) {
		this.workMsg = workMsg;
	}
	public String getWorkResult() {
		return workResult;
	}
	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}
	public String getWorkMemo() {
		return workMemo;
	}
	public void setWorkMemo(String workMemo) {
		this.workMemo = workMemo;
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
	public String getWorkSct() {
		return workSct;
	}
	public void setWorkSct(String workSct) {
		this.workSct = workSct;
	}


}
