<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
	
<script type="text/javascript">

$(document).ready(function() {

	$('#calendar').fullCalendar({
        header: {
            left: 'prev, next today',
            center: 'title',
            right: 'month, basicWeek, basicDay'
        },
        lang: 'ko',
        editable: false, // 달력에서 스케줄을 이동하여 수정할지 여부
        eventLimit: true, // 한 날짜에 스케줄이 많은 경우 "more"버튼 보이도록.
        events: function(start, end, timezone, callback) {
            var vDate = new Date($('#calendar').fullCalendar('getDate'));
            var vMonth = vDate.getMonth()+1;
            var searchMonth = vDate.getFullYear() + "-" + (vMonth < 10 ? "0" + vMonth : vMonth);
            $.ajax({
                url: "/scheduleList",
                dataType: 'json',
                data : {"searchMonth": searchMonth},
                success: function(data, text, request) {
                	console.log(data);
                    callback(data.list);
                }
            });
        },
        // 등록된 이벤트 클릭
        eventClick: function(calEvent, jsEvent, view) {
            DIALOG.Open().load("/scheduleWrite", {"schd_seq":calEvent.schdSeq, "schd_sct":calEvent.schdSct }, function() {
                REG_SCHEDULE.init($(this));
            });
        },
        // 날짜 클릭
        dayClick: function(date, allDay, jsEvent, view) {
            DIALOG.Open().load("/scheduleWrite", {}, function() {
            	REG_SCHEDULE.init($(this), date);
            });
        }
    });

});

</script>
	
<style>

    #calendar {
        max-width: 900px;
        margin: 0 auto;
    }
    
</style>	
	
<div id="calendar"></div>

<%@ include file="../include/footer.jsp"%>

