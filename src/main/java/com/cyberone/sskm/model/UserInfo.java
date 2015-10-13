package com.cyberone.sskm.model;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;



/**
 * 계정정보
 *
 */
public class UserInfo {

	private AcctGrp acctGrp;
	private Acct acct;
	private MenuAuth menuAuth;
	
	public Acct getAcct() {
		return acct;
	}
	public void setAcct(Acct acct) {
		this.acct = acct;
	}
	public AcctGrp getAcctGrp() {
		return acctGrp;
	}
	public void setAcctGrp(AcctGrp acctGrp) {
		this.acctGrp = acctGrp;
	}
	public boolean isMenuAuth(String s) {
		s = "," + s + ",";
		return acctGrp.getAcctPrmsMenus().indexOf(s) >= 0 ? true : false;
	}
	public MenuAuth getMenuAuth() {
		return menuAuth;
	}
	public void setMenuAuth(MenuAuth menuAuth) {
		this.menuAuth = menuAuth;
	}
	public boolean isAuth(String menu, String auth) {
		
		try {
	    	if (menuAuth != null) {
	        	ObjectMapper mapper = new ObjectMapper();
	        	List<HashMap<String,Object>> acctMenuAuth = mapper.readValue(menuAuth.getMenuRtInfo(),
	        			TypeFactory.defaultInstance().constructCollectionType(List.class,  
	        			   HashMap.class));
	    	
	        	for (HashMap<String, Object> ma : acctMenuAuth) {
	        		if (((String)ma.get("code")).equals(menu)) {
	        			if (auth.equals("W")) {
	        				return (Boolean)ma.get("write") ? true : false;
	        			} else if (auth.equals("R")) {
	        				return (Boolean)ma.get("read") ? true : false;
	        			}
	        		}
	        	}
	    	}
		} catch (Exception e) {}
		
		return false;
	}
}
