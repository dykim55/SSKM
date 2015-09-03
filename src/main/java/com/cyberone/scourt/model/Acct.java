package com.cyberone.scourt.model;

import java.util.Date;

public class Acct extends Paging {

	private String acctId;
	private String acctNm;
	private String bizSct;
	private int acctGrpCd;
	private String acctSt;
	private String deptCd;
	private String oflvCd;
	private String acctPw;
	private String mobile;
	private String telephone;
	private String email;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;

	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public String getAcctNm() {
		return acctNm;
	}
	public void setAcctNm(String acctNm) {
		this.acctNm = acctNm;
	}
	public String getBizSct() {
		return bizSct;
	}
	public void setBizSct(String bizSct) {
		this.bizSct = bizSct;
	}
	public int getAcctGrpCd() {
		return acctGrpCd;
	}
	public void setAcctGrpCd(int acctGrpCd) {
		this.acctGrpCd = acctGrpCd;
	}
	public String getAcctSt() {
		return acctSt;
	}
	public void setAcctSt(String acctSt) {
		this.acctSt = acctSt;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getOflvCd() {
		return oflvCd;
	}
	public void setOflvCd(String oflvCd) {
		this.oflvCd = oflvCd;
	}
	public String getAcctPw() {
		return acctPw;
	}
	public void setAcctPw(String acctPw) {
		this.acctPw = acctPw;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
}