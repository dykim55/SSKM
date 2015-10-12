<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
	
<script type="text/javascript">

$(document).ready(function() {

	$('#calendar').fullCalendar({
		height:750,
		header: {
			left: 'prev,next today reg',
			center: 'title',
			right: 'month,basicWeek,basicDay'
		},
		titleFormat: {
			month: "YYYY년 MMMM",
			week: "YYYY년 MMM D일",
			day: "YYYY년 MMM D일"
		},
		eventTimeFormat: "H:mm",
		monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],
		dayNamesShort: ["일","월","화","수","목","금","토"],
		buttonText: {
			today : "오늘",
			reg : "일정등록",
			month : "월간",
			week : "주간",
			day : "일간"
		},
		editable: false,
		eventLimit: true,
        events: function(start, end, timezone, callback) {
            var vDate = new Date($('#calendar').fullCalendar('getDate'));
            var vMonth = vDate.getMonth()+1;
            var searchMonth = vDate.getFullYear() + "-" + (vMonth < 10 ? "0" + vMonth : vMonth);
            $.ajax({
                url: "/schedule/schedule_list",
                dataType: 'json',
                data : {"searchMonth": searchMonth},
                success: function(data, text, request) {
                	console.log(data);
                    callback(data.list);
                }
            });
        },
        eventClick: function(calEvent, jsEvent, view) {
            DIALOG.Open().load("/schedule/schedule_write", {"schd_seq":calEvent.schdSeq, "schd_sct":calEvent.schdSct }, function() {
                REG_SCHEDULE.init($(this));
            });
        },
        dayClick: function(date, allDay, jsEvent, view) {
            DIALOG.Open().load("/schedule/schedule_write", {}, function() {
            	REG_SCHEDULE.init($(this), date);
            });
        }

	});
	
	$(".location .left").html("일정관리");
});

function regSchedule(date) {
    DIALOG.Open().load("/schedule/schedule_write", {}, function() {
    	REG_SCHEDULE.init($(this), date);
    });
}

</script>
	
<!-- depth 1. content -->
<div class="content">		
	<!-- depth 2. section -->
	<div class="section">
		<div class="detail">
			<div id='calendar' class="schedule"></div>
		</div>
	</div>
</div>


<%@ include file="../include/footer.jsp"%>

