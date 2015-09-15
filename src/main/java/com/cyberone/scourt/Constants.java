package com.cyberone.scourt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Constants {

	public static final String ACCTID_PATTERN = 
            "((?=.*[a-zA-Z0-9]).{6,16})";
	
	public static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()]).{8,16})";

	public static final String MOBILE_PATTERN = 
    		"^01(?:0|[1|6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    
	public static final String PHONE_PATTERN = 
    		"^(02|0[3-9]{1}[0-9]{1})-(?:\\d{3}|\\d{4})-\\d{4}$";
    		
	public static final String EMAIL_PATTERN = 
    		"^([a-zA-Z0-9._-]+)@([a-zA-Z0-9_-]+)(\\.[a-zA-Z0-9]+){1,2}$";
	

	/** 게시판타입 */
	public static final String	BBS_NOTICE = "1";	//공지사항
	public static final String	BBS_RAWS = "2";		//법제도
	public static final String	BBS_TREND = "3";	//보안동향
	public static final String	BBS_PRIVACY = "4";	//개인정보
	
	public static String getBbsSct(String sUrl) {
		if (sUrl.indexOf("/notice") >= 0) {
			return BBS_NOTICE;
		} else if (sUrl.indexOf("/raws") >= 0) {
			return BBS_RAWS;
		} else if (sUrl.indexOf("/securityTrend") >= 0) {
			return BBS_TREND;
		} else if (sUrl.indexOf("/privacy") >= 0) {
			return BBS_PRIVACY;	
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 계정상태 */
	public static final String	ACST_WAIT = "0";
	public static final String	ACST_NORMAL = "1";
	public static final String	ACST_CLOSE = "2";
	public static final String	ACST_REST = "3";
	public static final String	ACST_DELETE = "9";
	
	/** 계정타입 */
	public static final String	ACTP_ADMIN = "A0";		//관리자
	public static final String	ACTP_NRLCST = "A1";		//일반고객
	public static final String	ACTP_IDCMNG = "A2";		//IDC담당
	public static final String	ACTP_IDCCST = "A3"; 	//IDC고객
	public static final String	ACTP_CENTER = "A4";		//관제센터
	public static final String	ACTP_CYBERONE = "A5";	//내부직원
	
	/** 직급 */
	public static final String OFLV_CNT_GRPMNG =  "O1";  	//그룹장
	public static final String OFLV_CNT_TEAMMNG = "O2"; 	//팀장
	public static final String OFLV_CNT_TEAM =    "O3"; 	//팀원
	public static final String OFLV_CNT_CPCG =    "O4";		//팀원(전담)
	public static final String OFLV_CNT_HEAD =    "O5";		//팀원(조장)
	public static final String OFLV_CNT_MBR =     "O6";		//팀원(조원)
	public static final String OFLV_HQ_BOSS =     "O11";	//임원
	public static final String OFLV_HQ_MNG =      "O12";    //부장
	public static final String OFLV_HQ_TEAMMNG =  "O13";	//팀장
	public static final String OFLV_HQ_TEAM =     "O14";	//팀원

	/** 파일타입 */
	public static final String	FLTP_REQUEST = "FT01";		//고객요청
	public static final String	FLTP_REQUEST_PROC = "FT02";	//고객요청처리
	public static final String	FLTP_REQUEST_CERT = "FT03";	//Cert추가내용 첨부파일
	public static final String	FLTP_FIREWALL = "FT04";		//방화벽요청
	public static final String	FLTP_FIREWALL_PROC = "FT05";//방화벽요청처리
	public static final String	FLTP_CSCOEQPM = "FT06";	//고객장비 첨부파일
	public static final String	FLTP_SECURITYTRND = "FT07";	//주간보안동향
	public static final String	FLTP_PROPAGATION = "FT08";	//상황전파
	public static final String	FLTP_REPORT = "FT09";	//보고서
	public static final String	FLTP_WKDRY = "FT10";	//근무일지
	public static final String	FLTP_WKDRYPROC = "FT11";	//근무일지 처리내역
	public static final String	FLTP_SECURITY_MNG = "FT12";	//보안관제 보고서
	public static final String	FLTP_VIOL_ALRT = "FT13";	//침해위협통보
	public static final String	FLTP_WK_FNSH = "FT14";		//작업완료보고서 구성도
	public static final String	FLTP_ONDY_CLIPPING= "FT15";		//일일뉴스클리핑
	public static final String	FLTP_NOTICE= "FT16";		//홈페이지공지사항
	public static final String	FLTP_BADCODETHREATRPT= "FT17";		//악성코드위협보고서
	public static final String	FLTP_C1BOARD= "FT18";		//C1게시판
	public static final String	FLTP_C1BOARDPROC = "FT19";	//C1게시판 처리내역
	
	/** 티켓처리상태 */
	public static final String	PRST_NO_PROC = "1";		//미처리
	public static final String	PRST_ACPT_PROC = "2";	//접수중
	public static final String	PRST_ING_PROC = "3";	//처리중
	public static final String	PRST_WAIT_APPR = "4";	//승인대기
	public static final String	PRST_FIN_PROC = "5";	//처리완료
	
	/** 검색구분 */
	public static final String	SRCH_SCT_TITLE = "1";	//제목
	public static final String	SRCH_SCT_CONTENT = "2";	//내용
	public static final String	SRCH_SCT_WRITER = "3";	//작성자
	public static final String	SRCH_SCT_EVENT = "4";	//이벤트명
	public static final String	SRCH_SCT_SRCIP = "5";	//출발지
	public static final String	SRCH_SCT_DESTIP = "6";	//목적지
	public static final String	SRCH_SCT_ACCTID = "7";	//계정ID
	public static final String	SRCH_SCT_SERIAL = "8";	//식별번호
	public static final String	SRCH_SCT_DESTPORT = "9";	//목적지포트
	public static final String	SRCH_SCT_DTCTEQPM = "10";	//탐지장비
	public static final String	SRCH_SCT_SRCGEO = "11";		//출발지국가
	public static final String	SRCH_SCT_DESTGEO = "12";	//목적지국가
	
	/** 요청구분 */
	public static final String	RQST_FIREWALL = "1";	//방화벽요청
	public static final String	RQST_NRML = "2";		//일반요청
	public static final String	RQST_ANAL = "3";		//분석요청
	public static final String	RQST_SPRT = "4";		//기술지원
	
	/** 티켓타입 */
	public static final String	TCKT_TYPE_REQUEST = "1";	//일반요청
	public static final String	TCKT_TYPE_FIREWALL = "2";	//방화벽정책설정요청
	public static final String	TCKT_TYPE_SCRTCTRL = "3";	//보안관제 보고서
	public static final String	TCKT_TYPE_VIOLALRT = "4";	//침해위협탐지
	
	/** 보고서종류 */
	public static final String	RPTP_MONTH = "1";		//월간보고서
	public static final String	RPTP_WEEK = "2";		//주간보고서
	public static final String	RPTP_DAY = "3";			//일간보고서
	
	public static final String	RPTP_DETECTION = "4";	//탐지보고서
	public static final String	RPTP_SITUATION = "5";   //상황보고서
	public static final String	RPTP_HLT = "6";			//장애보고서
	public static final String	RPTP_WORK_PERF = "7";	//작업수행계획서
	public static final String	RPTP_WORK_FNSH = "8";	//작업완료보고서
	
	/** 부서코드 */
	public static final String	DEPT_CERT = "D1";		//CERT
	public static final String	DEPT_TECH = "D2";		//TECH
	public static final String	DEPT_ANALY = "D3";		//ANALY
	public static final String	DEPT_DEVL = "D4";		//DEVL
	
	/** 일지유형 */
	public static final String	DRY_TP_NRML = "1";	//일반
	public static final String	DRY_TP_SITN = "2";	//상황
	public static final String	DRY_TP_HLT = "3";	//장애
	public static final String	DRY_TP_WK = "4";	//작업수행
	public static final String	DRY_TP_WK_FNSH = "5";	//작업완료
	public static final String	DRY_TP_RULE = "6";		//룰설정
	public static final String	DRY_TP_PATTERN = "7";	//패턴업데이트
	public static final String	DRY_TP_PATCH = "8";		//패치작업
	
	/** 일정유형 */
	public static final String	SCHD_SCT_1 = "1";	//휴가
	public static final String	SCHD_SCT_2 = "2";	//작업
	public static final String	SCHD_SCT_3 = "3";	//외근
	public static final String	SCHD_SCT_4 = "4";	//공지(내부)
	public static final String	SCHD_SCT_5 = "5";	//근무일지
	public static final String	SCHD_SCT_6 = "6";	//근무조
	
	/** 고객유형 */
	public static final String	CSCO_TP_CONT = "1";	//관제
	public static final String	CSCO_TP_NCONT = "2";//비관제
	
	/** 고객상태 */
	public static final String	CSCO_ST_NORM = "1";	//정상
	public static final String	CSCO_ST_CNCL = "2";	//해제

	/** 임대구분 */
	public static final String	RNTL_C1 = "0";		//C1
	public static final String	RNTL_ANCO = "1";	//타사

	/** 근무일지처리상태 */
	public static final String	WKDRY_NO_PROC = "1";	//미처리
	public static final String	WKDRY_FIN_PROC = "2";	//처리완료
	
    private static final String[] countryCode = { "--", "AP", "EU", "AD", "AE",
        "AF", "AG", "AI", "AL", "AM", "CW", "AO", "AQ", "AR", "AS", "AT",
        "AU", "AW", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI",
        "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BV", "BW", "BY", "BZ",
        "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN",
        "CO", "CR", "CU", "CV", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM",
        "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ",
        "FK", "FM", "FO", "FR", "SX", "GA", "GB", "GD", "GE", "GF", "GH",
        "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW",
        "GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IN",
        "IO", "IQ", "IR", "IS", "IT", "JM", "JO", "JP", "KE", "KG", "KH",
        "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC",
        "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD",
        "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS",
        "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF",
        "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE",
        "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW",
        "PY", "QA", "RE", "RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE",
        "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "ST",
        "SV", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TM",
        "TN", "TO", "TL", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "UM",
        "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF",
        "WS", "YE", "YT", "RS", "ZA", "ZM", "ME", "ZW", "A1", "A2", "O1",
        "AX", "GG", "IM", "JE", "BL", "MF", "BQ", "SS", "O1" };

    private static final String[] countryName = { "N/A", "Asia/Pacific Region",
        "Europe", "Andorra", "United Arab Emirates", "Afghanistan",
        "Antigua and Barbuda", "Anguilla", "Albania", "Armenia", "Curacao",
        "Angola", "Antarctica", "Argentina", "American Samoa", "Austria",
        "Australia", "Aruba", "Azerbaijan", "Bosnia and Herzegovina",
        "Barbados", "Bangladesh", "Belgium", "Burkina Faso", "Bulgaria",
        "Bahrain", "Burundi", "Benin", "Bermuda", "Brunei Darussalam",
        "Bolivia", "Brazil", "Bahamas", "Bhutan", "Bouvet Island",
        "Botswana", "Belarus", "Belize", "Canada",
        "Cocos (Keeling) Islands", "Congo, The Democratic Republic of the",
        "Central African Republic", "Congo", "Switzerland",
        "Cote D'Ivoire", "Cook Islands", "Chile", "Cameroon", "China",
        "Colombia", "Costa Rica", "Cuba", "Cape Verde", "Christmas Island",
        "Cyprus", "Czech Republic", "Germany", "Djibouti", "Denmark",
        "Dominica", "Dominican Republic", "Algeria", "Ecuador", "Estonia",
        "Egypt", "Western Sahara", "Eritrea", "Spain", "Ethiopia",
        "Finland", "Fiji", "Falkland Islands (Malvinas)",
        "Micronesia, Federated States of", "Faroe Islands", "France",
        "Sint Maarten (Dutch part)", "Gabon", "United Kingdom", "Grenada",
        "Georgia", "French Guiana", "Ghana", "Gibraltar", "Greenland",
        "Gambia", "Guinea", "Guadeloupe", "Equatorial Guinea", "Greece",
        "South Georgia and the South Sandwich Islands", "Guatemala",
        "Guam", "Guinea-Bissau", "Guyana", "Hong Kong",
        "Heard Island and McDonald Islands", "Honduras", "Croatia",
        "Haiti", "Hungary", "Indonesia", "Ireland", "Israel", "India",
        "British Indian Ocean Territory", "Iraq",
        "Iran, Islamic Republic of", "Iceland", "Italy", "Jamaica",
        "Jordan", "Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati",
        "Comoros", "Saint Kitts and Nevis",
        "Korea, Democratic People's Republic of", "Korea, Republic of",
        "Kuwait", "Cayman Islands", "Kazakhstan",
        "Lao People's Democratic Republic", "Lebanon", "Saint Lucia",
        "Liechtenstein", "Sri Lanka", "Liberia", "Lesotho", "Lithuania",
        "Luxembourg", "Latvia", "Libya", "Morocco", "Monaco",
        "Moldova, Republic of", "Madagascar", "Marshall Islands",
        "Macedonia", "Mali", "Myanmar", "Mongolia", "Macau",
        "Northern Mariana Islands", "Martinique", "Mauritania",
        "Montserrat", "Malta", "Mauritius", "Maldives", "Malawi", "Mexico",
        "Malaysia", "Mozambique", "Namibia", "New Caledonia", "Niger",
        "Norfolk Island", "Nigeria", "Nicaragua", "Netherlands", "Norway",
        "Nepal", "Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru",
        "French Polynesia", "Papua New Guinea", "Philippines", "Pakistan",
        "Poland", "Saint Pierre and Miquelon", "Pitcairn Islands",
        "Puerto Rico", "Palestinian Territory", "Portugal", "Palau",
        "Paraguay", "Qatar", "Reunion", "Romania", "Russian Federation",
        "Rwanda", "Saudi Arabia", "Solomon Islands", "Seychelles", "Sudan",
        "Sweden", "Singapore", "Saint Helena", "Slovenia",
        "Svalbard and Jan Mayen", "Slovakia", "Sierra Leone", "San Marino",
        "Senegal", "Somalia", "Suriname", "Sao Tome and Principe",
        "El Salvador", "Syrian Arab Republic", "Swaziland",
        "Turks and Caicos Islands", "Chad", "French Southern Territories",
        "Togo", "Thailand", "Tajikistan", "Tokelau", "Turkmenistan",
        "Tunisia", "Tonga", "Timor-Leste", "Turkey", "Trinidad and Tobago",
        "Tuvalu", "Taiwan", "Tanzania, United Republic of", "Ukraine",
        "Uganda", "United States Minor Outlying Islands", "United States",
        "Uruguay", "Uzbekistan", "Holy See (Vatican City State)",
        "Saint Vincent and the Grenadines", "Venezuela",
        "Virgin Islands, British", "Virgin Islands, U.S.", "Vietnam",
        "Vanuatu", "Wallis and Futuna", "Samoa", "Yemen", "Mayotte",
        "Serbia", "South Africa", "Zambia", "Montenegro", "Zimbabwe",
        "Anonymous Proxy", "Satellite Provider", "Other", "Aland Islands",
        "Guernsey", "Isle of Man", "Jersey", "Saint Barthelemy",
        "Saint Martin", "Bonaire, Saint Eustatius and Saba", "South Sudan",
        "Other" };

	/** 게시글유형 */
	public static final String	ART_TP_NOT = "1";	//공지
	public static final String	ART_TP_INFO = "2";	//정보공유
	public static final String	ART_TP_REQ = "3";	//업무요청
	public static final String	ART_TP_GUD = "4";	//업무가이드
	public static final String	ART_TP_ETC = "5";	//기타
	
	public static String getArtTpName(String type) {
		if (type.equals("1")) {
			return "공지";
		} else if (type.equals("2")) {
			return "정보공유";
		} else if (type.equals("3")) {
			return "업무요청";
		} else if (type.equals("4")) {
			return "업무가이드";
		} else if (type.equals("5")) {
			return "기타";
		}
		return "";
	}
	
	public static String getAcctTpName(String acctTp) {
		if (acctTp.equals("A0")) {
			return "관리자계정";
		} else if (acctTp.equals("A1")) {
			return "고객계정";
		} else if (acctTp.equals("A2")) {
			return "IDC담당계정";
		} else if (acctTp.equals("A3")) {
			return "IDC고객계정";
		} else if (acctTp.equals("A4")) {
			return "관제센터계정";
		} else if (acctTp.equals("A5")) {
			return "내부직원계정";
		}
		return "";
	}
	
	public static String getDeptName(String deptCd) {
		if (deptCd.equals("D1")) {
			return "CERT팀";
		} else if (deptCd.equals("D2")) {
			return "기술팀";
		} else if (deptCd.equals("D3")) {
			return "분석팀";
		} else if (deptCd.equals("D4")) {
			return "관제개발팀";
		} else if (deptCd.equals("D5")) {
			return "관제지원팀";
		} else if (deptCd.equals("D6")) {
			return "서비스사업부";
		} else if (deptCd.equals("D7")) {
			return "전략사업부";
		} else if (deptCd.equals("D8")) {
			return "솔루션사업부";
		} else if (deptCd.equals("D9")) {
			return "정보보안사업팀";
		} else if (deptCd.equals("D10")) {
			return "SNS사업팀";
		} else if (deptCd.equals("D11")) {
			return "경영기획본부";
		} else if (deptCd.equals("D12")) {
			return "파견팀";
		} else if (deptCd.equals("D13")) {
			return "임원";
		} else if (deptCd.equals("D14")) {
			return "관리그룹";
		} else if (deptCd.equals("D15")) {
			return "컨설팅본부";
		}
		return "";
	}

	public static String getOflvName(String oflvCd) {
		if (oflvCd.equals("O1")) {
			return "그룹장";
		} else if (oflvCd.equals("O2")) {
			return "팀장";
		} else if (oflvCd.equals("O3")) {
			return "팀원";
		} else if (oflvCd.equals("O4")) {
			return "팀원(전담)";
		} else if (oflvCd.equals("O11")) {
			return "임원";
		} else if (oflvCd.equals("O12")) {
			return "부장";
		} else if (oflvCd.equals("O13")) {
			return "팀장";
		} else if (oflvCd.equals("O14")) {
			return "팀원";
		}
		return "";
	}

	public static String getRqstType(String rqst) {
		if (rqst.equals(RQST_FIREWALL)) {
			return "방화벽정책요청";	
		} else if (rqst.equals(RQST_NRML)) {
			return "일반요청";
		} else if (rqst.equals(RQST_ANAL)) {
			return "침해분석요청";
		} else if (rqst.equals(RQST_SPRT)) {
			return "기술지원요청";
		}
		return "기타요청";
	}

	public static String getRptType(String rpt) {
		if (rpt.equals(RPTP_MONTH)) {
			return "월간보고서";
		} else if (rpt.equals(RPTP_WEEK)) {
			return "주간보고서";
		} else if (rpt.equals(RPTP_DAY)) {
			return "일일보고서";
		} else if (rpt.equals(RPTP_DETECTION)) {
			return "침해위협통보";	
		} else if (rpt.equals(RPTP_SITUATION)) {
			return "상황보고서";
		} else if (rpt.equals(RPTP_HLT)) {
			return "장애보고서";
		} else if (rpt.equals(RPTP_WORK_PERF)) {
			return "작업수행계획서";
		} else if (rpt.equals(RPTP_WORK_FNSH)) {
			return "작업완료보고서";
		}
		return "기타보고서";
	}
	
	public static String getEsmAlert(String s) {
		if (s.equals("1")) {
			return "상";
		} else if (s.equals("2")) {
			return "중";
		} else if (s.equals("3")) {
			return "하";
		}
		return "";
	}
	
	public static String getEsmSvrt(String s) {
		if (s.equals("1")) {
			return "highest";
		} else if (s.equals("2")) {
			return "high";
		} else if (s.equals("3")) {
			return "medium";
		} else if (s.equals("4")) {
			return "low";
		} else if (s.equals("5")) {
			return "lowest";
		}
		return "";
	}

	public static String getSb01(String sb) {
		if (sb.equals("10")) {
			return "악성코드 URL 삽입 및 소스코드 변조";	
		} else if (sb.equals("11")) {
			return "악성코드 확인 - 웹쉘";
		} else if (sb.equals("12")) {
			return "악성코드 확인 - 의심스러운 프로세스 또는 파일생성";
		} else if (sb.equals("13")) {
			return "SQL Injection";
		} else if (sb.equals("14")) {
			return "의심스러운 외부 네트워크 연결";
		} else if (sb.equals("15")) {
			return "ARP Spoofing";
		} else if (sb.equals("16")) {
			return "과도한 네트워크 트래픽 유발(DDoS)";
		} else if (sb.equals("17")) {
			return "불필요한 원격접속";
		} else if (sb.equals("18")) {
			return "정보유출 의심";
		} else if (sb.equals("99")) {
			return "기타";
		}
		return "기타";
	}
	
	public static String getSb02(String sb) {
		if (sb.equals("10")) {
			return "Windows Server 2012 x86";	
		} else if (sb.equals("11")) {
			return "Windows Server 2012 x64";
		} else if (sb.equals("12")) {
			return "Windows Server 2008 x86";
		} else if (sb.equals("13")) {
			return "Windows Server 2008 x64";
		} else if (sb.equals("14")) {
			return "Windows Server 2003 x86";
		} else if (sb.equals("15")) {
			return "Windows Server 2003 x64";
		} else if (sb.equals("16")) {
			return "Windows Server 2000";
		} else if (sb.equals("17")) {
			return "Windows NT Server";
		} else if (sb.equals("18")) {
			return "Windows 10 x86";
		} else if (sb.equals("19")) {
			return "Windows 10 x64";
		} else if (sb.equals("20")) {
			return "Windows 8.x x86";
		} else if (sb.equals("21")) {
			return "Windows 8.x x64";
		} else if (sb.equals("22")) {
			return "Windows 7 x86";
		} else if (sb.equals("23")) {
			return "Windows 7 x64";
		} else if (sb.equals("24")) {
			return "Windows Vista x86";
		} else if (sb.equals("25")) {
			return "Windows Vista x64";
		} else if (sb.equals("26")) {
			return "Windows XP x86";
		} else if (sb.equals("27")) {
			return "Windows XP x64";
		} else if (sb.equals("28")) {
			return "Unix 계열";
		} else if (sb.equals("29")) {
			return "Solaris";
		} else if (sb.equals("30")) {
			return "Red Hat x86";
		} else if (sb.equals("31")) {
			return "Red Hat x64";
		} else if (sb.equals("32")) {
			return "CentOS x86";
		} else if (sb.equals("33")) {
			return "CentOS x64";
		} else if (sb.equals("99")) {
			return "기타 OS";
		}
		return "기타 OS";
	}
	
	public static String getSb03(String sb) {
		if (sb.equals("10")) {
			return "Web Application Server(Apache & PHP)";	
		} else if (sb.equals("11")) {
			return "Web Application Server(Apache & Tomcat)";
		} else if (sb.equals("12")) {
			return "Web Application Server(IIS & ASP)";
		} else if (sb.equals("13")) {
			return "Web Application Server(기타 WAS)";
		} else if (sb.equals("20")) {
			return "Database Server(MS SQL)";
		} else if (sb.equals("21")) {
			return "Database Server(Oracle)";
		} else if (sb.equals("22")) {
			return "Database Server(MySQL)";
		} else if (sb.equals("23")) {
			return "Database Server(Sybase)";
		} else if (sb.equals("24")) {
			return "Database Server(DB2)";
		} else if (sb.equals("30")) {
			return "Active Directory Server";
		} else if (sb.equals("40")) {
			return "DNS Server";
		} else if (sb.equals("50")) {
			return "Exchange & SMTP Server";
		} else if (sb.equals("60")) {
			return "File Server";
		} else if (sb.equals("70")) {
			return "FTP Server";
		} else if (sb.equals("80")) {
			return "Client PC";
		} else if (sb.equals("99")) {
			return "기타 시스템";
		}
		return "기타 시스템";
	}
	
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
	
    public static String searchAlarmGrade() {
    	String sAlertGrade = "0";
    	URL ocu = null;
    	try {
    		ocu = new URL("http://www.krcert.or.kr/kor/main/main.jsp");  //URL 생성
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		}

    	URLConnection con = null;
		BufferedReader in = null;
		String inputLine = new String();
		try {
			con = ocu.openConnection();                    //URL연결
			in = new BufferedReader(new InputStreamReader(con.getInputStream())); //URLConnection에서 읽어오기
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.indexOf("/images/main/Alarm_")>0) {
					break;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			sAlertGrade = System.getProperty("alertGrade");
		}    	
		
		if (inputLine.indexOf("images/main/Alarm_01.gif") > 0) {
			sAlertGrade = "1";
		} else if (inputLine.indexOf("images/main/Alarm_02.gif") > 0) {
			sAlertGrade = "2";
		} else if (inputLine.indexOf("images/main/Alarm_03.gif") > 0) {
			sAlertGrade = "3";
		} else if (inputLine.indexOf("images/main/Alarm_04.gif") > 0) {
			sAlertGrade = "4";
		} else if (inputLine.indexOf("images/main/Alarm_05.gif") > 0) {
			sAlertGrade = "5";
		}
		
		return sAlertGrade;
    }
    
}


