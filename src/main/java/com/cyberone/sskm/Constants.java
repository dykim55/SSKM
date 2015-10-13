package com.cyberone.sskm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Constants {

	public static final String ACCTID_PATTERN = 
            "((?=.*[a-zA-Z0-9]).{5,16})";
	
	public static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()]).{8,16})";

	public static final String MOBILE_PATTERN = 
    		"^01(?:0|[1|6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    
	public static final String PHONE_PATTERN = 
    		"^(02|0[3-9]{1}[0-9]{1})-(?:\\d{3}|\\d{4})-\\d{4}$";
    		
	public static final String EMAIL_PATTERN = 
    		"^([a-zA-Z0-9._-]+)@([a-zA-Z0-9_-]+)(\\.[a-zA-Z0-9]+){1,2}$";
	
	/** 로그타입 */
	public static final String	AUDIT_LOGIN = "1";		//로그인
	public static final String	AUDIT_ACCOUNT = "2";	//계정관리
	public static final String	AUDIT_FILES = "3";		//산출물관리
	public static final String	AUDIT_ARTICLE = "4";	//게시판
	
	/** 게시판타입 */
	public static final String	BBS_NOTICE = "1";	//공지사항
	public static final String	BBS_TRANSFER = "2";	//인수인계
	
	public static String getFileExtension(String sFileName) {
		
		if (sFileName.lastIndexOf(".") < 0) {
			return "none";
		}
		
		String sExt = sFileName.substring(sFileName.lastIndexOf(".")+1, sFileName.length()).toLowerCase();
		if (sExt.equals("doc") || sExt.equals("docx")) {
			return "doc";
		} else if (sExt.equals("xls") || sExt.equals("xlsx")) {
			return "xls";
		} else if (sExt.equals("ppt") || sExt.equals("pptx")) {
			return "ppt";
		} else if (sExt.equals("zip") || sExt.equals("jar") || sExt.equals("cab") || sExt.equals("rar")) {
			return "zip";
		} else if (sExt.equals("hwp")) {
			return "hwp";
		} else if (sExt.equals("exe")) {
			return "exe";
		} else if (sExt.equals("gif") || sExt.equals("jpg") || sExt.equals("png") || sExt.equals("bmp") || sExt.equals("tif")) {
			return "gif";
		} else if (sExt.equals("txt")) {
			return "txt";
		} else if (sExt.equals("pdf")) {
			return "pdf";
		}
		return "none";
	}
    
}


