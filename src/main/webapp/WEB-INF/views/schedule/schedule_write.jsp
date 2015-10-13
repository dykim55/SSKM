<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
    @SuppressWarnings("unchecked")
    HashMap<String, Object> schedule = (HashMap<String, Object>)request.getAttribute("schedule");
    if (StringUtil.isEmpty(schedule)) {
    	schedule = new HashMap<String, Object>(); 
    }
%>

<script type="text/javascript">

REG_SCHEDULE = (function() {
	var _Dlg;
	var _bProcessing = false;
	
	$("#schd_frm").ajaxForm({
        beforeSubmit: function(data, form, option) {
            return true;
        },
        success: function(data, status) {
        	if (data.status=="success") {
	        	$("#calendar").fullCalendar("refetchEvents");
	        	_Dlg.dialog("close");
        	} else {
        		_bProcessing = false;
        		_alert(data.message);
        	}
        }
    });

    $("#schd_del_frm").ajaxForm({
        success: function(res, status) {
            $("#calendar").fullCalendar("refetchEvents");
            _Dlg.dialog("close");
        }
    });
	
    return {
        init: function(Dlg, date) {
        	_bProcessing = false;
        	_Dlg = Dlg;

        	_Dlg.find("#schd_sdate, #schd_edate").datepicker({
                dateFormat: "yy-mm-dd",
				monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
				dayNames: ['일','월','화','수','목','금','토'],
				dayNamesShort: ['일','월','화','수','목','금','토'],
				dayNamesMin: ['일','월','화','수','목','금','토'],
				showMonthAfterYear: true,
				yearSuffix: '년'
            });
        	
            _Dlg.find("#schd_stime, #schd_etime").timepicker({
                timeFormat: "HH:mm",
                showButtonPanel: false,
            });

            if (date) {
                _Dlg.find("#schd_sdate, #schd_edate").val(yyyymmdd(date._d.getTime()));
                _Dlg.find("#schd_stime").val("00:00");
                _Dlg.find("#schd_etime").val("23:59");
            }
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: '<%= StringUtil.isEmpty(schedule.get("schdSeq")) ? "일정 등록" : "일정 수정" %>',
                buttons: {
'<%= StringUtil.isEmpty(schedule.get("schdSeq")) ? "등록" : "수정" %>' : function() {
	
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
	                    	$("#schd_frm").submit();
						}
                    }
<% if (!StringUtil.isEmpty(schedule.get("schdSeq"))) { %>                    
                    ,"삭제": function() {
                    	_confirm("삭제 하시겠습니까?", function() {
    						if (!_bProcessing) {
    							_bProcessing = true;
                    			$("#schd_del_frm").submit();
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

<div id="regSchedule">
	<div class="dia-insert">
    <form name="schd_frm" id="schd_frm" action="/schedule/schedule_register" method="POST">
        <input type="hidden" name="schd_seq" value="<%=StringUtil.convertString(schedule.get("schdSeq")) %>">
        <input type="hidden" name="schd_sct" value="<%=StringUtil.convertString(schedule.get("schdSct")) %>">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>기간</th>
				<td>
                    <input type="text" class="insertDate" name="schd_sdate" id="schd_sdate" readonly tabindex="-1" value="<%=StringUtil.convertString(schedule.get("startDate")) %>">
                    <input type="text" class="insertTime" name="schd_stime" id="schd_stime" readonly tabindex="-1" value="<%=StringUtil.convertString(schedule.get("startTime")) %>">
					~
                    <input type="text" class="insertDate" name="schd_edate" id="schd_edate" readonly tabindex="-1" value="<%=StringUtil.convertString(schedule.get("endDate")) %>">
                    <input type="text" class="insertTime" name="schd_etime" id="schd_etime" readonly tabindex="-1" value="<%=StringUtil.convertString(schedule.get("endTime")) %>">
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea class="important" id="schd_cont" name="schd_cont" alt="내용은 "><%=StringUtil.convertString(schedule.get("title")) %></textarea>
				</td>
			</tr>
		</table>
	</form>
  	</div>
</div>

<form name="schd_del_frm" id="schd_del_frm" action="/schedule/schedule_delete" method="POST">
    <input type="hidden" name="schd_seq" value="<%=StringUtil.convertString(schedule.get("schdSeq")) %>">
    <input type="hidden" name="schd_sct" value="<%=StringUtil.convertString(schedule.get("schdSct")) %>">
</form>