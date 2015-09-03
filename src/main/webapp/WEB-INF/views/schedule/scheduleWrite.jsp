<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.scourt.utils.StringUtil"
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
	var bProcessing = false;
	
	$("#schd_frm").ajaxForm({
        beforeSubmit: function(data, form, option) {
            return true;
        },
        success: function(res, status) {
        	$("#calendar").fullCalendar("refetchEvents");
        	_Dlg.dialog("close");
        },
        error: function() {
        }
    });

    $("#schd_del_frm").ajaxForm({
        success: function(res, status) {
            $("#calendar").fullCalendar("refetchEvents");
            _Dlg.dialog("close");
        }
    });
	
    return {
        done: function(Dlg) {
            if (bProcessing) return;    
            bProcessing = true;
            return new FormData(Dlg.find("#schd_frm")[0]);
        },
        init: function(Dlg, date) {
        	_Dlg = Dlg;
        	
        	_Dlg.find("#schd_sdate, #schd_edate").datepicker({
                dateFormat: "yy-mm-dd",
            });
        	
            _Dlg.find("#schd_stime, #schd_etime").timepicker({
                timeFormat: "HH:mm",
            });

            if (date) {
                _Dlg.find("#schd_sdate, #schd_edate").val(date.format());
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
                    	$("#schd_frm").submit();
                    }
<% if (!StringUtil.isEmpty(schedule.get("schdSeq"))) { %>                    
                    ,"삭제": function() {
                    	$("#schd_del_frm").submit();
                    }
<% } %>                    
                    ,"취소": function() {
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
        <form name="schd_frm" id="schd_frm" action="/scheduleReg" method="POST">
            <input type="hidden" name="schd_seq" value="<%=StringUtil.convertString(schedule.get("schdSeq")) %>">
            <input type="hidden" name="schd_sct" value="<%=StringUtil.convertString(schedule.get("schdSct")) %>">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="80"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>일정기간</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="schd_sdate" id="schd_sdate" readonly style="width:100px;" tabindex="-1" value="<%=StringUtil.convertString(schedule.get("startDate")) %>">
                            <input type="text" class="normal focus_e" name="schd_stime" id="schd_stime" readonly style="width:100px;" tabindex="-1" value="<%=StringUtil.convertString(schedule.get("startTime")) %>">
                             ~ 
                            <input type="text" class="normal focus_e" name="schd_edate" id="schd_edate" readonly style="width:100px;" tabindex="-1" value="<%=StringUtil.convertString(schedule.get("endDate")) %>">
                            <input type="text" class="normal focus_e" name="schd_etime" id="schd_etime" readonly style="width:100px;" tabindex="-1" value="<%=StringUtil.convertString(schedule.get("endTime")) %>">
                        </td>
                    </tr>
                    <tr>
                        <th>일정내용</th>
                        <td class="left">
                            <textarea id="schd_cont" name="schd_cont" class="normal focus_e" style="width:97%; height:50px;"><%=StringUtil.convertString(schedule.get("title")) %></textarea>                        
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
    </div>
</div>

<form name="schd_del_frm" id="schd_del_frm" action="/scheduleDel" method="POST">
    <input type="hidden" name="schd_seq" value="<%=StringUtil.convertString(schedule.get("schdSeq")) %>">
    <input type="hidden" name="schd_sct" value="<%=StringUtil.convertString(schedule.get("schdSct")) %>">
</form>