<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.scourt.Common
        , com.cyberone.scourt.model.Acct
        , com.cyberone.scourt.utils.StringUtil"
%>

<%
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
	var _Dlg, _tt_id, _tt_parent_id;
	var bProcessing = false;
	
    return {
        init: function(Dlg, t) {
        	_Dlg = Dlg;

        	stop();
        	if (t) {
	        	_tt_id = t.attr('data-tt-id');
	        	_tt_parent_id = t.attr('data-tt-parent-id');
	        	$("#acct_auth_grp").val(_tt_parent_id);
        	} else {
        		$("#acct_auth_grp").val(<%=acctInfo.getAuthGrp() %>);
        		$("#acct_grp").val(<%=acctInfo.getAcctGrpCd() %>);
        	}
        	
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
                        $.ajax({
                       <% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %>                        	
                            url: "/account/register_account",
                       <% } else { %>
                       		url: "/account/modify_account",
                       <% } %>
                            dataType: 'json',
                            data : { 
                            	id : $("#acct_id").val(),
                            	name : $("#acct_name").val(),
                            	pw: $("#acct_pw").val(),
                            	pw_cf: $("#acct_pw_cf").val(),
                            	auth_grp: $("#acct_auth_grp").val(),
                            	acct_grp: $("#acct_grp").val(),
                            	dept: $("#acct_dept").val(),
                            	oflv: $("#acct_oflv").val(),
                            	email: $("#acct_email").val(),
                            	mobile: $("#acct_mobile").val()
                            },
                            success: function(data, text, request) {
                            	if (data.status == "success") {
                            		ACCT_PG.reload(false, false, function() { _Dlg.dialog("close"); });
                            	} else {
                            		alert(data.message);
                            	}
                            }
                        });
                    	
                    },
                    "취소": function() {
                        $(this).dialog("close");
                    }
                },
                close: function( event, ui ) {
                	bProcessing = false;
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
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>계정 ID</th>
				<td>
			<% if (StringUtil.isEmpty(acctInfo.getAcctId())) { %>
					<input type="text" name="acct_id" id="acct_id" value="<%=StringUtil.convertString(acctInfo.getAcctId()) %>">
			<% } else { %>
					<%=StringUtil.convertString(acctInfo.getAcctId()) %><input type="hidden" name="acct_id" id="acct_id" value="<%=StringUtil.convertString(acctInfo.getAcctId()) %>">
			<% }  %>
				</td>
				<th>성명</th>
				<td>
					<input type="text" name="acct_name" id="acct_name" value="<%=StringUtil.convertString(acctInfo.getAcctNm()) %>">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="acct_pw" id="acct_pw"></td>
				<th>비밀번호 확인</th>
				<td><input type="password" name="acct_pw_cf" id="acct_pw_cf"></td>
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
					<input type="text" name="acct_dept" id="acct_dept" value="<%=StringUtil.convertString(acctInfo.getDeptNm()) %>">
				</td>
				<th>직급</th>
				<td>
					<input type="text" name="acct_oflv" id="acct_oflv" value="<%=StringUtil.convertString(acctInfo.getOflvNm()) %>">
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<input type="text" name="acct_email" id="acct_email" value="<%=StringUtil.convertString(acctInfo.getEmail()) %>"> 
				</td>
				<th>모바일 번호</th>
				<td>
					<input type="text" name="acct_mobile" id="acct_mobile" value="<%=StringUtil.convertString(acctInfo.getMobile()) %>">
				</td>
			</tr>
		</table>
  	</div>
</div>
