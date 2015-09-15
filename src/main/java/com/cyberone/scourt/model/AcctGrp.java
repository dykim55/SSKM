package com.cyberone.scourt.model;

import java.util.Date;

public class AcctGrp extends Paging {

	private int acctGrpCd;
	private String acctGrpNm;
	private String acctPrmsMenus;
	private String acctGrpDesc;
	private int acctPrntCd;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;
	
	public int getAcctGrpCd() {
		return acctGrpCd;
	}
	public void setAcctGrpCd(int acctGrpCd) {
		this.acctGrpCd = acctGrpCd;
	}
	public String getAcctGrpNm() {
		return acctGrpNm;
	}
	public void setAcctGrpNm(String acctGrpNm) {
		this.acctGrpNm = acctGrpNm;
	}
	public String getAcctPrmsMenus() {
		return acctPrmsMenus;
	}
	public void setAcctPrmsMenus(String acctPrmsMenus) {
		this.acctPrmsMenus = acctPrmsMenus;
	}
	public String getAcctGrpDesc() {
		return acctGrpDesc;
	}
	public void setAcctGrpDesc(String acctGrpDesc) {
		this.acctGrpDesc = acctGrpDesc;
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
	public int getAcctPrntCd() {
		return acctPrntCd;
	}
	public void setAcctPrntCd(int acctPrntCd) {
		this.acctPrntCd = acctPrntCd;
	} 

	
}
