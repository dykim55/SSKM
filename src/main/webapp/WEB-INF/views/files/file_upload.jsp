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

FILE_UPLOAD = (function() {
	var _Dlg;
	var bProcessing = false;
	
	$("#file_upload_form").ajaxForm({
        success: function(res, status) {
    		$("#product-grid-div").load("/file_pane", {"parent": res.parent}, function() {
            	_Dlg.dialog("close");
    		});
        },
        error: function() {
        }
    });
	
	$("#uf_file").change(function() {
		var str = $(this).val().substr($(this).val().lastIndexOf("\\") + 1);
		$("#uf_title").val(str.substring(0, str.lastIndexOf(".")));
	});
	
    return {
        init: function(Dlg, tr) {
        	_Dlg = Dlg;

        	_Dlg.find("#editorTd").load("/daumeditor/editor.html");
        	
        	_Dlg.find("#uf_path").html(tr.closest('table').treetable('pathName', tr.attr('data-tt-id')));
        	_Dlg.find("#uf_parent").val(tr.attr('data-tt-id'));
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: "파일 등록",
                buttons: {
                    "등록" : function() {
						//Editor.getSaver().save();
						
						$("#file_upload_form").submit();
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

	<span id="uf_path"></span>

	<form name="file_upload_form" id="file_upload_form" action="/files/file_register" method="post" enctype="multipart/form-data">
	
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
	                    <input type="file" name="uf_file" id="uf_file" class="file-data" style="width: 95%">
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
