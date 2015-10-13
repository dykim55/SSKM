function stop() { return; }

$.fn.toPhone = function() {
	return this.each(function() {
		$(this).keydown(function(e) {
			var key = e.charCode || e.keyCode || 0;
			console.log(key);
			return ((!e.shiftKey) && (
				key == 36 || 				//home
				key == 35 || 				//end
				key == 8 || 				//backspace
				key == 46 ||				//delete
				key == 109 ||				
				key == 189 ||				
				(key >= 37 && key <= 40) || //arrows
				(key >= 48 && key <= 57) || //number
				(key >= 96 && key <= 105))) ;
		});
	});
};

DIALOG = (function() { 
    return {
        Open : function() { 
            var dlg = undefined;
            var len = $('body').find('.templateDialog').length;
            $('body').find('.templateDialog').each(function() {
                if (!$(this).dialog("isOpen")) {
                    dlg = $(this);
                }
            });
            
            if (dlg == undefined) {
                $('body').append('<div id="templateDialog'+(len+1)+'" class="templateDialog" title="템플릿다이얼로그" style="display:none;"></div>');
                $("#templateDialog"+(len+1)).dialog({autoOpen: false});
                dlg = $("#templateDialog"+(len+1));
            }
            
            return dlg;
        }
    };
})();

_alert = function (msg) {
    DIALOG.Open().load("/common/_alert", function() {
        _ALERT.init($(this), msg);
    });
}

_confirm = function (msg, func) {
    DIALOG.Open().load("/common/_confirm", function() {
        _CONFIRM.init($(this), msg, func);
    });
}

function fileDownload(t) {
	$.fileDownload('/files/downloadFile?seq='+t)
	.done(function () { })
	.fail(function () { 
	    alert('파일을 찾을 수가 없습니다.'); 
	});
}

function appxDownload(t) {
	$.fileDownload('/article/downloadFile?id='+t)
	.done(function () { })
	.fail(function () { 
	    alert('파일을 찾을 수가 없습니다.'); 
	});
}

function yyyymmdd(time) {
	  now = new Date(time);
	  year = "" + now.getFullYear();
	  month = "" + (now.getMonth() + 1); if (month.length == 1) { month = "0" + month; }
	  day = "" + now.getDate(); if (day.length == 1) { day = "0" + day; }
	  return year + "-" + month + "-" + day;
}

function toCurrentTime(type) {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var min = date.getMinutes();
	var sec = date.getSeconds(); 

	if (("" + month).length == 1) { month = "0" + month; }
	if (("" + day).length == 1) { day = "0" + day; }
	if (("" + hour).length == 1) { hour = "0" + hour; }
	if (("" + min).length == 1) { min = "0" + min; }
	if (("" + sec).length == 1) { sec = "0" + sec; }

	if (type == 0) {
		return ("" + year + month + day + hour + min + sec);
	} else if (type == 1) {
		return (year + "-" + month + "-" + day);
	} else if (type == 2) { 
		return (year + "-" + month + "-" + day + " " +  hour + ":" + min + ":" + sec);
	}
}
