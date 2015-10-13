package com.cyberone.sskm.model;

import java.util.Date;

public class Board extends Paging {

	private int bbsId;
	private String bbsTit;
	private String bbsCont;
	private int qryCnt;
	private String bbsSct;
	private String fileYn;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;
	
	private String startDtime;
	private String endDtime;

	public int getBbsId() {
		return bbsId;
	}
	public void setBbsId(int bbsId) {
		this.bbsId = bbsId;
	}
	public String getBbsTit() {
		return bbsTit;
	}
	public void setBbsTit(String bbsTit) {
		this.bbsTit = bbsTit;
	}
	public String getBbsCont() {
		return bbsCont;
	}
	public void setBbsCont(String bbsCont) {
		this.bbsCont = bbsCont;
	}
	public int getQryCnt() {
		return qryCnt;
	}
	public void setQryCnt(int qryCnt) {
		this.qryCnt = qryCnt;
	}
	public String getBbsSct() {
		return bbsSct;
	}
	public void setBbsSct(String bbsSct) {
		this.bbsSct = bbsSct;
	}
	public String getFileYn() {
		return fileYn;
	}
	public void setFileYn(String fileYn) {
		this.fileYn = fileYn;
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
