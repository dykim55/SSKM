package com.cyberone.scourt.model;

import java.util.Date;

public class Files extends Paging {

	private int fileId;
	private int pId;
	private String fileSct;
	private String fileLoc;
	private int fileSize;
	private String delYn;
	private String fileOrgNm;
	private String fileSavNm;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;

	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getFileSct() {
		return fileSct;
	}
	public void setFileSct(String fileSct) {
		this.fileSct = fileSct;
	}
	public String getFileLoc() {
		return fileLoc;
	}
	public void setFileLoc(String fileLoc) {
		this.fileLoc = fileLoc;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getFileOrgNm() {
		return fileOrgNm;
	}
	public void setFileOrgNm(String fileOrgNm) {
		this.fileOrgNm = fileOrgNm;
	}
	public String getFileSavNm() {
		return fileSavNm;
	}
	public void setFileSavNm(String fileSavNm) {
		this.fileSavNm = fileSavNm;
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
