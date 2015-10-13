package com.cyberone.sskm.model;

import java.util.Date;

public class RssFeed extends Paging {

	private int feedSeq;
	private String rssSrc;
	private String rssFeed;
	private String guidParam;
	private long guidLast;
	private String regr;
	private Date regDtime;
	private String modr;
	private Date modDtime;
	
	public int getFeedSeq() {
		return feedSeq;
	}
	public void setFeed(int feedSeq) {
		this.feedSeq = feedSeq;
	}
	public String getRssSrc() {
		return rssSrc;
	}
	public void setRssSrc(String rssSrc) {
		this.rssSrc = rssSrc;
	}
	public String getRssFeed() {
		return rssFeed;
	}
	public void setRssFeed(String rssFeed) {
		this.rssFeed = rssFeed;
	}
	public String getGuidParam() {
		return guidParam;
	}
	public void setGuidParam(String guidParam) {
		this.guidParam = guidParam;
	}
	public long getGuidLast() {
		return guidLast;
	}
	public void setGuidLast(long guidLast) {
		this.guidLast = guidLast;
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
