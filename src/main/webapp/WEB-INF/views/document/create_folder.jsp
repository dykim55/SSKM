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

CREATE_FOLDER = (function() {
	var _Dlg;
	var bProcessing = false;
	
    return {
        done: function(Dlg) {
            if (bProcessing) return;    
            bProcessing = true;
            return new FormData(Dlg.find("#schd_frm")[0]);
        },
        init: function(Dlg, parent) {
        	_Dlg = Dlg;
        	
        	_Dlg.find("#h_parent").val(parent);
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: "폴더 생성",
                buttons: {
                    "생성" : function() {
                    	
                        $.ajax({
                            url: "product/folder_register",
                            dataType: 'json',
                            data : {"parent": $("#h_parent").val(), "name" : $("#folder_name").val() },
                            success: function(data, text, request) {
                                console.log(data);
                            }
                        });
                    	
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
            _Dlg.dialog('option', 'position', 'center');
        }
    };
    
})();

</script>


<div class="contents">
    <div class="dialogContent">
        <div class="contents">
            <input type="hidden" id="h_parent" name="h_parent">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="80"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>폴더명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="folder_name" id="folder_name" style="width:100px;">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
