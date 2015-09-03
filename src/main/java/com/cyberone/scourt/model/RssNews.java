package com.cyberone.scourt.model;

import java.util.Date;

public class RssNews extends Paging {

	private int rssSeq;
	private String rssSrc;
	private String rssLink;
	private String rssShow;
	private String rssTit;
	private String rssCont;
	private String regr;
	private Date regDtime;
	
	public int getRssSeq() {
		return rssSeq;
	}
	public void setRssSeq(int rssSeq) {
		this.rssSeq = rssSeq;
	}
	public String getRssSrc() {
		return rssSrc;
	}
	public void setRssSrc(String rssSrc) {
		this.rssSrc = rssSrc;
	}
	public String getRssLink() {
		return rssLink;
	}
	public void setRssLink(String rssLink) {
		this.rssLink = rssLink;
	}
	public String getRssShow() {
		return rssShow;
	}
	public void setRssShow(String rssShow) {
		this.rssShow = rssShow;
	}
	public String getRssTit() {
		return rssTit;
	}
	public void setRssTit(String rssTit) {
		this.rssTit = rssTit;
	}
	public String getRssCont() {
		return rssCont;
	}
	public void setRssCont(String rssCont) {
		this.rssCont = rssCont;
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
	
}
