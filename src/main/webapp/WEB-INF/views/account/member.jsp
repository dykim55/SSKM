<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.sskm.Common
        , com.cyberone.sskm.model.Acct
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
	Acct acctInfo = (Acct)request.getAttribute("acctInfo");
%>

<script type="text/javascript">

MEMBER_DLG = (function() {
	var _Dlg;
	var _bProcessing = false;

	$("#acct_mobile").toPhone();
	
	$("#member_frm").ajaxForm({
        beforeSubmit: function(data, form, option) {
            return true;
        },
        success: function(data, status) {
        	if (data.status=="success") {
	        	$(".location .right").find('b').html(data.name);
	        	_Dlg.dialog("close");
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
    		
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: "개인정보 수정",
                buttons: {
                  "수정" : function() {
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
							$("#member_frm").submit();
						}
                    },
                    "취소": function() {
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

<div id="memberModify">
	<div class="dia-insert">
		<form name="member_frm" id="member_frm" action="/account/modify_member" method="POST">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>성명</th>
				<td>
					<input type="text" class="important" name="acct_name" id="acct_name" value="<%=StringUtil.convertString(acctInfo.getAcctNm()) %>" alt="성명은">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="acct_pw" id="acct_pw">
				</td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td>
					<input type="password" name="acct_pw_cf" id="acct_pw_cf">
				</td>
			</tr>
			<tr>
				<th>부서</th>
				<td>
					<input type="text" class="important" name="acct_dept" id="acct_dept" value="<%=StringUtil.convertString(acctInfo.getDeptNm()) %>" alt="부서는">
				</td>
			</tr>
			<tr>
				<th>직급</th>
				<td>
					<input type="text" class="important" name="acct_oflv" id="acct_oflv" value="<%=StringUtil.convertString(acctInfo.getOflvNm()) %>" alt="직급은"> 
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>
					<input type="text" style="width:50%" name="acct_mobile" id="acct_mobile" value="<%=StringUtil.convertString(acctInfo.getMobile()) %>">&nbsp;예)&nbsp;000-0000-0000
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<input type="text" name="acct_email" id="acct_email" value="<%=StringUtil.convertString(acctInfo.getEmail()) %>">
				</td>
			</tr>
		</table>
		</form>
  	</div>
</div>
