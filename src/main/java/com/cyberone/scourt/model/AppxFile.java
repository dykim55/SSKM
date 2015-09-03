package com.cyberone.scourt.model;

import java.util.Date;

public class AppxFile extends Paging {

	private int fileId;
	private int refCd;
	private String fileTp;
	private String fileLoc;
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
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public int getRefCd() {
		return refCd;
	}
	public void setRefCd(int refCd) {
		this.refCd = refCd;
	}
	public String getFileTp() {
		return fileTp;
	}
	public void setFileTp(String fileTp) {
		this.fileTp = fileTp;
	}
	public String getFileLoc() {
		return fileLoc;
	}
	public void setFileLoc(String fileLoc) {
		this.fileLoc = fileLoc;
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
	public Date getRegDtime() {
		return regDtime;
	}
	public void setRegDtime(Date regDtime) {
		this.regDtime = regDtime;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
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
