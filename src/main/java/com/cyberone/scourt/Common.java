package com.cyberone.scourt;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.cyberone.scourt.model.AcctGrp;

public class Common {
	
	private static SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		Common.sqlSession = sqlSession;
	}
	
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
	
}
