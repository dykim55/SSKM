<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , java.util.Date
        , com.cyberone.scourt.model.UserInfo
        , com.cyberone.scourt.model.AcctGrp
        , com.cyberone.scourt.utils.StringUtil"
%>

<%
UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");
AcctGrp authInfo = (AcctGrp)request.getAttribute("authInfo");
authInfo = StringUtil.isEmpty(authInfo) || authInfo.getAcctPrntCd() == 0 ? new AcctGrp() : authInfo; 
%>

<script type="text/javascript">

CREATE_AUTH_GROUP = (function() {
	var _Dlg;
	var bProcessing = false;
	
	$("#auth_frm").ajaxForm({
        beforeSubmit: function(data, form, option) {
            return true;
        },
        success: function(data, status) {
        	if (data.status=="success") {
				$("#account-tree-div").load("/account/tree_ajax", {}, function() {
				    $(".account-tree tr").each(function() {
				    	if ($(this)[0].getAttribute('data-tt-id')==data.id) {
				    	    $(".selected").not(this).removeClass("selected");
				    	    $(this).addClass("selected");
				    		return false;
				    	}
					});
				});
				_Dlg.dialog("close");
        	} else {
        		_alert(data.message);	
        	}
        }
    });
	
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
					  
						$("#auth_frm").submit();
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

<div id="addGroup">
	<div class="dia-insert">
		<form name="auth_frm" id="auth_frm" action="/account/register_auth_group" method="POST">
		<input type="hidden" name="auth_grp" id="auth_grp" value="<%=authInfo.getAcctGrpCd()%>"/>
		<table class="board-insert">
			<colgroup>
				<col width="20%">
				<col width="30%">
				<col width="20%">
				<col width="*">
			</colgroup>
			<tr>
				<th>만든 사람</th>
				<td>
					<%=authInfo.getAcctPrntCd() == 0 ? userInfo.getAcct().getAcctNm() : StringUtil.convertString(authInfo.getRegrNm()) %>					
				</td>
				<th>만든 날짜</th>
				<td>
					<%=authInfo.getAcctPrntCd() == 0 ? StringUtil.convertDate(new Date(),"yyyy-MM-dd HH:mm:ss") : StringUtil.convertDate(authInfo.getRegDtime(),"yyyy-MM-dd HH:mm:ss") %>
				</td>
			</tr>
			<tr>
				<th>권한그룹명</th>
				<td colspan="3">
					<input type="text" class="important" name="auth_name" id="auth_name" value="<%=StringUtil.convertString(authInfo.getAcctGrpNm()) %>" alt="그룹명은">
				</td>
			</tr>
			<tr>
				<th>권한그룹 설명</th>
				<td colspan="3">
					<textarea name="auth_desc" id="auth_desc"><%=StringUtil.convertString(authInfo.getAcctGrpDesc()) %></textarea>
				</td>
			</tr>
		</table>
		</form>
  	</div>
</div>
