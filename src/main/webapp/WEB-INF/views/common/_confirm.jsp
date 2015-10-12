<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">

_CONFIRM = (function() {
	var _Dlg;
	var bProcessing = false;
	
    return {
        init: function(Dlg, msg, func) {
        	_Dlg = Dlg;

        	_Dlg.find(".diabox pre").html(msg);
        	
        	_Dlg.dialog({
                autoOpen: false,
                resizable: false,
                modal: true,
                width: "auto",
                title: "알림",
                buttons: {
                    "확인": function() {
                    	func();
                        $(this).dialog("close");
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
            _Dlg.dialog("option", "position", "center");
        }
    };
    
})();

</script>

<div id="delGroup">
	<div class="diabox ac"><pre style="font-family: inherit;">내용</pre></div>
</div>
