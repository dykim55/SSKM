<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.scourt.utils.StringUtil"
%>

<%
    @SuppressWarnings("unchecked")
    HashMap<String, Object> category = (HashMap<String, Object>)request.getAttribute("category");
    if (StringUtil.isEmpty(category)) {
    	category = new HashMap<String, Object>(); 
    }
%>

<script type="text/javascript">

CREATE_FOLDER = (function() {
	var _Dlg;
	var bProcessing = false;
	
    return {
        init: function(Dlg, loc) {
        	_Dlg = Dlg;
        	
        	_Dlg.find("#path_name").html($(".location").html());
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: <% if (!category.containsKey("ctgId")) { %> "폴더 생성" <% } else { %> "폴더 수정" <% } %>,
                buttons: {
<% if (!category.containsKey("ctgId")) { %> "생성" <% } else { %> "수정" <% } %> : function() {                	
                    	stop();
                        $.ajax({
                            url: "/files/folder_register",
                            dataType: 'json',
                            data : {
                            	id: '<%=StringUtil.convertString(category.get("ctgId")) %>',
                            	gubun: $('.accordion').find('.accordionHeaders.ac_selected').attr("ref-sct"), 
                            	parent: $(".left-tree .selected").attr('data-tt-id') ? $(".left-tree .selected").attr('data-tt-id') : $('.accordion').find('.accordionHeaders.ac_selected').attr("ref-id"), 
                            	name: $("#folder_name").val(),
                            	desc: $("#folder_desc").val()
                            },
                            success: function(data, text, request) {
                            	$('.accordion').find('.accordionHeaders.ac_selected').next().load("/files/tree_ajax", {"gubun": data.gubun}, function() {
                            		$(this).find(".left-tree").treetable("reveal", data.parent);
								});
								_Dlg.dialog("close");
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

<div id="dir">
	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>폴더명</th>
				<td colspan="3">
					<input type="text" name="folder_name" id="folder_name" value="<%=StringUtil.convertString(category.get("ctgNm")) %>">
				</td>
			</tr>
			<tr>
				<th>폴더설명</th>
				<td colspan="3">
					<textarea name="folder_desc" id="folder_desc"><%=StringUtil.convertString(category.get("ctgDesc")) %></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
