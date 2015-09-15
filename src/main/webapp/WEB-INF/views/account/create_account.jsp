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

        	if (t) {
	        	_tt_id = t[0].getAttribute('data-tt-id');
	        	_tt_parent_id = t[0].getAttribute('data-tt-parent-id');
	        	$("#acct_auth_grp").val(_tt_parent_id);
        	} else {
        		$("#acct_auth_grp").val(<%=acctInfo.getAuthGrp() %>);
        		$("#acct_grp").val(<%=acctInfo.getAcctGrpCd() %>);
        	}
        	
        	_Dlg.find("#acct_auth_grp").change(function() {
            	var slctAuth = _Dlg.find("#acct_auth_grp option:selected").val();
            	_Dlg.find("#acct_grp").load('/account/selectView?name=acct_grp&grpSct='+slctAuth, function() {});
            });
        	
        	_Dlg.find("#path_name").html($("#account-tree").treetable('pathName', parent));
        	
        	_Dlg.find("#g_parent").val(parent);
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: "계정 생성",
                buttons: {
                    "생성" : function() {
                    	
                        $.ajax({
                            url: "/account/register_account",
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
								_Dlg.dialog("close");
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

<div class="contents">
    <div class="dialogContent">
        <div class="contents">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="160"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>계정 ID</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_id" id="acct_id" style="width:200px;" value="<%=acctInfo.getAcctId() %>">
                        </td>
                    </tr>
                    <tr>
                        <th>성명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_name" id="acct_name" style="width:200px;" value="<%=acctInfo.getAcctNm() %>">
                        </td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_pw" id="acct_pw" style="width:200px;">
                        </td>
                    </tr>
                    <tr>
                        <th>비밀번호 확인</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_pw_cf" id="acct_pw_cf" style="width:200px;">
                        </td>
                    </tr>
                    <tr>
                        <th>접근권한 그룹</th>
                        <td class="left">
                        	<%=Common.acctGrpSelect("acct_auth_grp", "선택하세요.", Integer.valueOf(sAuthGrp), 1) %>
                        </td>
                    </tr>
                    <tr>
                        <th>계정 그룹</th>
                        <td class="left">
                        	<%=Common.acctGrpSelect("acct_grp", "선택하세요.", Integer.valueOf(sAcctGrp), Integer.valueOf(sAuthGrp)) %>
                        </td>
                    </tr>
                    <tr>
                        <th>부서</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_dept" id="acct_dept" style="width:200px;">
                        </td>
                    </tr>
                    <tr>
                        <th>직급</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_oflv" id="acct_oflv" style="width:200px;">
                        </td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_email" id="acct_email" style="width:200px;">
                        </td>
                    </tr>
                    <tr>
                        <th>모바일</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="acct_mobile" id="acct_mobile" style="width:200px;">
                        </td>
                    </tr>
                    
                </tbody>
            </table>
        </div>
    </div>
</div>

