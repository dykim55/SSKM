<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.scourt.model.AcctGrp
        , com.cyberone.scourt.utils.StringUtil"
%>

<%
@SuppressWarnings("unchecked")
AcctGrp authInfo = (AcctGrp)request.getAttribute("authInfo");
authInfo = StringUtil.isEmpty(authInfo) || authInfo.getAcctPrntCd() == 0 ? new AcctGrp() : authInfo; 
%>

<script type="text/javascript">

CREATE_AUTH_GROUP = (function() {
	var _Dlg;
	var bProcessing = false;
	
    return {
        init: function(Dlg, t) {
        	_Dlg = Dlg;

        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: <% if (authInfo.getAcctPrntCd() == 0) { %> "접근권한 그룹 등록" <% } else { %> "접근권한 그룹 수정" <% } %>,
                buttons: {
<% if (authInfo.getAcctPrntCd() == 0) { %> "등록" <% } else { %> "수정" <% } %> : function() {
                    	
                        $.ajax({
                            url: "/account/register_auth_group",
                            dataType: 'json',
                            data : { 
                            	id : <%=authInfo.getAcctGrpCd()%>, 
                            	name : $("#auth_name").val(), 
                            	desc : $("#auth_desc").val() 
                            },
                            success: function(data, text, request) {
								$("#account-tree-div").load("/account/tree_ajax", {}, function() {
								    $("#account-tree tr").each(function() {
								    	stop();
								    	console.log($(this)[0].getAttribute('data-tt-id'));
								    	if ($(this)[0].getAttribute('data-tt-id')==data.id) {
								    	    $(".selected").not(this).removeClass("selected");
								    	    $(this).addClass("selected");
								    		return false;
								    	}
									});
								});
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
                        <th>접근권한 그룹명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="auth_name" id="auth_name" style="width:200px;" value="<%=StringUtil.convertString(authInfo.getAcctGrpNm()) %>">
                        </td>
                    </tr>
                    <tr>
                        <th>접근권한 그룹 설명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="auth_desc" id="auth_desc" style="width:200px;" value="<%=StringUtil.convertString(authInfo.getAcctGrpDesc()) %>">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<!-- 
<div class="contents">
    <div class="dialogContent">
        <div class="contents">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="160"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>계정 그룹명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="group_name" id="group_name" style="width:200px;">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

 -->