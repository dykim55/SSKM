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
    
    String sCtgSct = request.getParameter("gubun");
%>

<script type="text/javascript">

UPLOAD_FILE = (function() {
	var _Dlg;
	var bProcessing = false;
	
	$("#tx_editor_form").ajaxForm({
        success: function(res, status) {
    		$("#product-grid-div").load("/file_pane", {"parent": res.parent}, function() {
            	_Dlg.dialog("close");
    		});
        },
        error: function() {
        }
    });
	
	$("#uf-file-data").change(function() {
		var str = $(this).val().substr($(this).val().lastIndexOf("\\") + 1);
		$("#uf_title").val(str.substring(0, str.lastIndexOf(".")));
	});
	
    return {
        init: function(Dlg, parent) {
        	_Dlg = Dlg;

        	_Dlg.find("#editorTd").load("/daumeditor/editor.html");
        	
        	_Dlg.find("#path_name").html($("#product-tree").treetable('pathName', parent));
        	
        	_Dlg.find("#uf_parent").val(parent);
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: "파일 등록",
                buttons: {
                    "등록" : function() {
						//Editor.getSaver().save();
						
						$("#tx_editor_form").submit();
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


<div class="board_pane" style="background-color:lightgray;font: 12px 맑은 고딕;padding: 10px 0px;">

	<span id="path_name"></span>

	<form name="tx_editor_form" id="tx_editor_form" action="product/file_register" method="post" enctype="multipart/form-data">
	
		<input type="hidden" id="uf_parent" name="uf_parent">
		
	    <table>
	        <colgroup>
	            <col style="width: 20%">
	            <col style="width: *">
	        </colgroup>
	        <tbody>
	            <tr>
	                <th>첨부파일</th>
	                <td>
	                    <input type="file" name="uf-file-data" id="uf-file-data" class="file-data" style="width: 95%">
	                </td>
	            </tr>
	            <tr class="firstTh">
	                <th scope="row">제목</th>
	                <td>
	                    <input name="uf_title" id="uf_title" type="text" maxlength="70" style="width:99%; *width:95% /* IE7 */; ime-mode:active;">
	                </td>
	            </tr>
	            <!-- 
	            <tr class="last">
	                <th>내용</th>
	                <td id="editorTd"></td>
	            </tr>
	            -->
	            <tr class="last">
	                <th>내용</th>
	                <td>
	                	<textarea id="uf_content" name="uf_content" style="height: 222px; max-height: 222px;"></textarea>
	                </td>
	            </tr>
	        </tbody>
	    </table>
	    
	</form>
    
</div>
