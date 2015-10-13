package com.cyberone.sskm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.cyberone.sskm.model.AcctGrp;
import com.cyberone.sskm.model.AuditHist;

public class Common {
	
	private static SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		Common.sqlSession = sqlSession;
	}
	
    /**
     * selectbox 
     */
	public static String acctGrpSelect(String name, String first, int prnt, int cd) throws Exception {
		AcctGrp dto = new AcctGrp();
		dto.setAcctPrntCd(cd);
		List<AcctGrp> list = sqlSession.selectList("Account.selectAcctGrpList", dto);
		StringBuffer buf = new StringBuffer(100);
		buf.append("<select class=\"normal focus_s\" name=\"").append(name).append("\" id=\"").append(name).append("\" >");
		if (first != null && !first.isEmpty()) {
			buf.append("	<option value=\""+"0"+"\">").append(first).append("</option>");		
		}
		for (AcctGrp result : list) {
			buf.append("	<option value=\"").append(result.getAcctGrpCd()).append("\" ").append(result.getAcctGrpCd() == prnt ? "selected=\"selected\"" : "").append('>').append(result.getAcctGrpNm()).append("</option>");
		}
		buf.append("</select>");
		return buf.toString();
	}
	
    /**
     * 감사로그 등록
     */
	public static void insertAuditHist(String workSct, String workMsg, String workResult, String workMemo, String regr) throws Exception {
    	AuditHist auditHist = new AuditHist();
    	auditHist.setWorkSct(workSct);
    	auditHist.setWorkMsg(workMsg);
    	auditHist.setWorkResult(workResult);
    	auditHist.setWorkMemo(workMemo);
    	auditHist.setRegr(regr);
    	auditHist.setRegDtime(new Date());
    	sqlSession.insert("Audit.insertAuditHist", auditHist);
	}
	
	public static List<HashMap<String, Object>> selectMenu() throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		return sqlSession.selectList("Files.selectMenu", paramMap);
	}

}
