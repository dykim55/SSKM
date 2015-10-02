function stop() {
	return;
}
PG = (function() {
	var rows=15, page=1, parent;
	
	return {
		reload : function(p, func) {
			if(p) parent = p;
			console.log("parent:"+parent);
        	$("#files-grid-div").load("/files/file_ajax", {
        		rows: rows,
        		parent: parent,
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
			console.log("group:"+group);
			console.log("parent:"+parent);
        	$("#account-grid-div").load("/account/account_ajax", {
        		rows: rows,
        		grp: group,
        		prts: parent,
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

ARTICLE_PG = (function() {
	var rows=15, page=1, bbsSct;
	
	return {
		reload : function(b, func) {
			if(b) bbsSct = b;
        	$(".dual-right").load("/article/article_list", {
        		rows: rows,
        		bbsSct: bbsSct,
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

function fileDownload(t) {
	$.fileDownload('/files/downloadFile?seq='+t)
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
