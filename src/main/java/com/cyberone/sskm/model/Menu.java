package com.cyberone.sskm.model;

import java.util.Date;

public class Menu {

	private int menuCd;
	private String menuNm;
	private int prtsCd;
	private String execUrl;
	private String useYn;
	private String desc;
	private Date regDtime;
	private String regr;
	
	public int getMenuCd() {
		return menuCd;
	}
	public void setMenuCd(int menuCd) {
		this.menuCd = menuCd;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public int getPrtsCd() {
		return prtsCd;
	}
	public void setPrtsCd(int prtsCd) {
		this.prtsCd = prtsCd;
	}
	public String getExecUrl() {
		return execUrl;
	}
	public void setExecUrl(String execUrl) {
		this.execUrl = execUrl;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getRegDtime() {
		return regDtime;
	}
	public void setRegDtime(Date regDtime) {
		this.regDtime = regDtime;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	
}




