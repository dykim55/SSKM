function stop() {
	return;
}

DIALOG = (function() { 
    return {
        Open : function() { 
            var dlg = undefined;
            var len = $('body').find('.templateDialog').length;
            $('body').find('.templateDialog').each(function() {
            	try {
            		if ($(this).dialog("isOpen")) {
                    }            		
            	} catch(e) { dlg = $(this); return false; }
            });
            
            if (dlg == undefined) {
                $('body').append('<div id="templateDialog'+(len+1)+'" class="templateDialog" title="" style="overflow: hidden; display:none;"></div>');
                $("#templateDialog"+(len+1)).dialog({autoOpen: false});
                dlg = $("#templateDialog"+(len+1));
            }

            return dlg;
        }
    };
})();
