function stop() {
	return;
}
PG = (function() {
	var rows=15, page=1, parent, searchSel=1, searchWord="";
	
	return {
		reload : function(p, func) {
			if(p) {
				parent = p;
				searchSel=1;
				searchWord="";
			} else {
        		searchSel = $("#nt_selbox").val(); 
        		searchWord = $("#searchWord").val();
			}
        	$("#files-grid-div").load("/files/file_ajax", {
        		rows: rows,
        		parent: parent,
        		searchSel : searchSel, 
        		searchWord : searchWord,
        		page : page
        	}, func);
		},
		move : function(p) {
			if(p) page = p;
			this.reload();
		},
		rows : function(r) {
			if(r) { rows = r; page = 1; }
			this.reload();
		}
		
	};
})();

ALL_PG = (function() {
	var rows=15, page=1, parent, searchWord="";
	
	return {
		reload : function(p, s, func) {
			if(p) parent = p;
			if(s) searchWord = s;
        	$("#files-grid-div").load("/files/allover_ajax", {
        		rows: rows,
        		searchWord : $(".dir-search input").val(),
        		page : page
        	}, func);
		},
		move : function(p) {
			if(p) page = p;
			this.reload();
		},
		rows : function(r) {
			if(r) { rows = r; page = 1; }
			this.reload();
		}
		
	};
})();

ACCT_PG = (function() {
	var rows=15, page=1, parent, group;
	
	return {
		reload : function(g, p, func) {
			if(g) group = g;
			if(p) parent = p;
        	$("#account-grid-div").load("/account/account_ajax", {
        		rows: rows,
        		grp: group,
        		prts: parent,
        		page: page,
	    		searchSel: $("#ac_selbox").val(), 
        		searchWord: $("#searchWord").val(),
        	}, func);
		},
		move : function(p) {
			if(p) page = p;
			this.reload();
		},
		rows : function(r) {
			if(r) { rows = r; page = 1; }
			this.reload();
		}
	};
})();

ARTICLE_PG = (function() {
	var rows=15, page=1, bbsSct, searchSel=1, searchWord="";
	
	return {
		reload : function(b, func) {
			if(b) {
				bbsSct = b;
				searchSel=1;
				searchWord="";
			} else {
	    		searchSel = $("#nt_selbox").val(); 
	    		searchWord = $("#searchWord").val();
			}
        	$(".dual-right").load("/article/article_list", {
        		rows: rows,
        		bbsSct: bbsSct,
        		searchSel : searchSel, 
        		searchWord : searchWord,
        		page : page
        	}, func);
		},
		move : function(p) {
			if(p) page = p;
			this.reload();
		},
		rows : function(r) {
			if(r) { rows = r; page = 1; }
			this.reload();
		}
	};
})();

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

function removeAppxFile(p,t,r,s) {
	stop();
	if(confirm("첨부파일을 삭제 하시겠습니까?")) {
	    $.ajax({
	        url : "/files/removeAppxFile",
	        type : 'POST',
	        data : {"type":t, "ref":r, "seq":s},
	        success : function(data){
	        	if (data.status == 'success') {
	        		p.parent('u').remove();
	        	} else {
	        		alert(data.message);
	        	}
	        }
	    });
	}
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
