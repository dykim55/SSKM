<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , java.util.Date
        , com.cyberone.sskm.Common
        , com.cyberone.sskm.model.UserInfo
        , com.cyberone.sskm.model.Acct
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
	UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

	String sAuthGrp = request.getParameter("auth_grp");
	String sAcctGrp = request.getParameter("acct_grp");
	
	Acct acctInfo = (Acct)request.getAttribute("acctInfo");
	if (StringUtil.isEmpty(acctInfo)) {
		acctInfo = new Acct();
	} else {
		sAuthGrp = StringUtil.convertString(acctInfo.getAuthGrp());
		sAcctGrp = StringUtil.convertString(acctInfo.getAcctGrpCd());
	}
%>

<script type="text/javascript">

CREATE_ACCOUNT = (function() {
	var _Dlg;
	var _bProcessing = false;
	
	$("#acct_mobile").toPhone();
	
	$("#acct_frm").ajaxForm({
        beforeSubmit: function(data, form, option) {
            return true;
        },
        success: function(data, status) {
        	if (data.status == "success") {
        		ACCT_PG.reload(false, false, function() { _Dlg.dialog("close"); });
        	} else {
        		_bProcessing = false;
        		_alert(data.message);
        	}
        }
    });
	
    $("#acct_del_frm").ajaxForm({
        success: function(data, status) {
        	if (data.status == "success") {
        		ACCT_PG.reload(false, false, function() { _Dlg.dialog("close"); });
        	} else {
        		_bProcessing = false;
        		_alert(data.message);
        	}
        }
    });
	
    return {
        init: function(Dlg) {
        	_bProcessing = false;
        	_Dlg = Dlg;

        	<% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %>
	    		$("#acct_auth_grp").val(<%=sAuthGrp %>);
	    		$("#acct_grp").val(<%=sAcctGrp %>);
        	<% } else { %>
	    		$("#acct_auth_grp").val(<%=acctInfo.getAuthGrp() %>);
	    		$("#acct_grp").val(<%=acctInfo.getAcctGrpCd() %>);
        	<% } %>
        	
        	_Dlg.find("#acct_auth_grp").change(function() {
            	var slctAuth = _Dlg.find("#acct_auth_grp option:selected").val();
            	_Dlg.find("#acct_grp").load('/account/selectView?name=acct_grp&grpSct='+slctAuth, function() {});
            });
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: <% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %> "계정 등록" <% } else { %> "계정 수정" <% } %>,
                buttons: {
<% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %> "등록" <% } else { %> "수정" <% } %> : function() {
	
					  	var flag=false;
						_Dlg.find(".important:visible").each(function() {
					        if ($(this).val().length == 0) {
					            $(this).select();
					            flag = true;
					            _alert($(this).attr("alt")+" 필수 입력값입니다.");
					            return false;
					        }
					    });
					    if (flag) return false;
					  
						if (!_bProcessing) {
							_bProcessing = true;
							$("#acct_frm").submit();
						}
                    }
                <% if (!StringUtil.isEmpty(acctInfo.getAcctId())) { %>                    
                    ,"삭제": function() {
                    	_confirm("삭제 하시겠습니까?", function() {
    						if (!_bProcessing) {
    							_bProcessing = true;
                    			$("#acct_del_frm").submit();
    						}
                    	});
                    }
				<% } %>                    
                    ,"취소": function() {
                        $(this).dialog("close");
                    }
                },
                close: function( event, ui ) {
                    $(this).children().remove();
                },
                open: function( event, ui ) {
                    var t = $(this).parent(), w = window;
                    t.offset({top: (w.innerHeight / 2) - (t.height() / 2),left: (w.innerWidth / 2) - (t.width() / 2)});
                    $(this).attr('tabindex',-1).css('outline',0).focus();
                }            
            });
            _Dlg.dialog("open");    
            _Dlg.dialog('option', 'position', 'center');
        }
    };
    
})();

</script>

<div id="account">
	<div class="dia-insert">
<% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %>	
	<form name="acct_frm" id="acct_frm" action="/account/register_account" method="POST">
<% } else { %>	
	<form name="acct_frm" id="acct_frm" action="/account/modify_account" method="POST">
<% } %>
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>계정 ID</th>
				<td>
			<% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %>
					<input type="text" class="important" name="acct_id" id="acct_id" value="<%=StringUtil.convertString(acctInfo.getAcctId()) %>" alt="계정아이디는">
			<% } else { %>
					<%=StringUtil.convertString(acctInfo.getAcctId()) %><input type="hidden" name="acct_id" id="acct_id" value="<%=StringUtil.convertString(acctInfo.getAcctId()) %>">
			<% }  %>
				</td>
				<th>성명</th>
				<td>
					<input type="text" class="important" name="acct_name" id="acct_name" value="<%=StringUtil.convertString(acctInfo.getAcctNm()) %>" alt="계정명은">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" <% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %> class="important" <% } %> name="acct_pw" id="acct_pw" alt="비밀번호는">
				</td>
				<th>비밀번호 확인</th>
				<td>
					<input type="password" <% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %> class="important" <% } %> name="acct_pw_cf" id="acct_pw_cf" alt="비밀번호확인은">
				</td>
			</tr>
			<tr>
				<th>접근권한그룹</th>
				<td>
					<%=Common.acctGrpSelect("acct_auth_grp", "선택하세요.", Integer.valueOf(sAuthGrp), 1) %>
				</td>
				<th>계정그룹</th>
				<td>
					<%=Common.acctGrpSelect("acct_grp", "선택하세요.", Integer.valueOf(sAcctGrp), Integer.valueOf(sAuthGrp)) %>
				</td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input type="text" class="important" name="acct_dept" id="acct_dept" value="<%=StringUtil.convertString(acctInfo.getDeptNm()) %>" alt="부서는">
				</td>
				<th>직급</th>
				<td>
					<input type="text" class="important" name="acct_oflv" id="acct_oflv" value="<%=StringUtil.convertString(acctInfo.getOflvNm()) %>" alt="직급은">
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<input type="text" name="acct_email" id="acct_email" value="<%=StringUtil.convertString(acctInfo.getEmail()) %>"> 
				</td>
				<th>휴대폰</th>
				<td>
					<input type="text" name="acct_mobile" id="acct_mobile" placeholder="000-0000-0000" value="<%=StringUtil.convertString(acctInfo.getMobile()) %>">
				</td>
			</tr>
			<tr>
				<th>등록자</th>
				<td>
					<%=StringUtil.isEmpty(acctInfo.getAcctId()) ? userInfo.getAcct().getAcctNm() : StringUtil.convertString(acctInfo.getRegrNm()) %> 
				</td>
				<th>등록 일자</th>
				<td>
					<%=StringUtil.isEmpty(acctInfo.getAcctId()) ? StringUtil.convertDate(new Date(),"yyyy-MM-dd HH:mm:ss") : StringUtil.convertDate(acctInfo.getRegDtime(),"yyyy-MM-dd HH:mm:ss") %>
				</td>
			</tr>
		</table>
	</form>
  	</div>
</div>

<form name="acct_del_frm" id="acct_del_frm" action="/account/delete_account" method="POST">
	<input type="hidden" name="acct_id" id="acct_id" value="<%=StringUtil.convertString(acctInfo.getAcctId()) %>">
</form>