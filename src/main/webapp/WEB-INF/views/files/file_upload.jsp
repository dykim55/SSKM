<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.sskm.model.UserInfo
        , com.cyberone.sskm.Constants
        , com.cyberone.sskm.model.Files
        , com.cyberone.sskm.utils.DateUtil
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
	UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

    @SuppressWarnings("unchecked")
    HashMap<String, Object> filesMap = (HashMap<String, Object>)request.getAttribute("filesMap");
    if (StringUtil.isEmpty(filesMap)) {
    	filesMap = new HashMap<String, Object>(); 
    }
    List<Files> filesList = (List<Files>)request.getAttribute("filesList");
%>

<script type="text/javascript">

FILE_UPLOAD = (function() {
	var _Dlg;
	var _bProcessing = false;
	
	$("#file_upload_form").ajaxForm({
        beforeSubmit: function(data, form, option) {
        	if ($(".file-add-write .file-list u").length > 0 || $(".file-add-write input").length > 1) return true;
        	_bProcessing = false;
        	_alert("등록 할 파일을 선택하세요.");
        	return false;
        },
        success: function(data, status) {
        	if (data.status=="success") {
        		if ($(".detail .left-set button").length > 0) {
        			PG.reload("NaN", function() { _Dlg.dialog("close"); });
        		} else {
        			ALL_PG.reload(false, function() { _Dlg.dialog("close"); });
        		}
        	} else {
        		_bProcessing = false;
        		_alert(data.message);
        	}
        }
    });
	
	$("#file-data").change(function() {
		var str = $(this).val().substr($(this).val().lastIndexOf("\\") + 1);
		if ($("#uf_title").val().length == 0) {
			$("#uf_title").val(str.substring(0, str.lastIndexOf(".")));
			$("#uf_title").select();
		}
	});
	
    return {
        init: function(Dlg) {
        	_bProcessing = false;
        	_Dlg = Dlg;

            file_data();
            
            function file_data(target) {
                if (!target) {
                	_Dlg.find(".file-data").bind({
                        change : function(e) {
                            if ($(e.target).parent().next().find('.file-data').length==0) {
                                $(e.target).parent().append('&nbsp;&nbsp;<a href="#none" class="remove"></a>');
                                _Dlg.find(".file-add-write").append('<p><input type="file" name="file-data" id="file-data" class="file-data"></p>');
                                file_data(_Dlg.find(".file-data").last());
                            }
                        }
                    });
                } else {
                    target.bind({
                        change : function(e) {
                            if ($(e.target).parent().next().find('.file-data').length==0) {
                                $(e.target).parent().append('&nbsp;&nbsp;<a href="#none" class="remove"></a>');
                                _Dlg.find(".file-add-write").append('<p><input type="file" name="file-data" id="file-data" class="file-data">');
                                file_data(_Dlg.find(".file-data").last());
                            }
                        }
                    });
                }
            }

            _Dlg.find(".file-add-write").on("click",".remove",function() {
                $(this).parent().remove();
            });
        	
        	<% if (filesMap.containsKey("pId")) { %>
        	<% } else { %>
	    		_Dlg.find("#uf_parent").val($(".left-tree .selected").attr('data-tt-id'));
			<% } %>
			
			<% if (filesMap.containsKey("pId")) { %>
				_Dlg.closest('.ui-dialog').find('.ui-dialog-title').html('<%=StringUtil.convertString(filesMap.get("pathNm"))%>');
			<% } else { %>
				_Dlg.closest('.ui-dialog').find('.ui-dialog-title').html($('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(".left-tree .selected").closest("table").treetable('pathName', $(".left-tree .selected").attr('data-tt-id')));
			<% } %>
			
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                buttons: {
<% if (!filesMap.containsKey("pId") || (filesMap.containsKey("pId") && StringUtil.convertString(filesMap.get("regr")).equals(userInfo.getAcct().getAcctId()))) { //수정/상세 %>
                	
	<% if (filesMap.containsKey("pId")) { %> "수정" <% } else { %> "등록" <% } %>: function() {
					  	var flag=false;
						_Dlg.find(".important:visible").each(function() {
					        if ($(this).val().length == 0) {
					            $(this).select();
					            flag = true;
					            _alert($(this).attr("alt")+" 필수 입력값입니다.");
					            return false;
					        }
					    });
					    if (flag) return false;
		
						if (!_bProcessing) {
							_bProcessing = true;
							$("#file_upload_form").submit();
						}
                    },
                    "취소": function() {
                        $(this).dialog("close");
                    }
<% } else { %>

					"닫기": function() {
					    $(this).dialog("close");
					}

<% } %>
                },
                close: function( event, ui ) {
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

function delFile(el, id) {
	_confirm("삭제 하시겠습니까?", function() {
		$("#uf_del").val($("#uf_del").val() + id + ",");
		el.parent('u').remove();
	});
}
</script>



<% if (filesMap.containsKey("pId")) { //수정/상세 %>

	<% if (StringUtil.convertString(filesMap.get("regr")).equals(userInfo.getAcct().getAcctId())) { %>

<div id="register">
	
<form name="file_upload_form" id="file_upload_form" action="/files/file_register" method="post" enctype="multipart/form-data">
	<input type="hidden" id="uf_id" name="uf_id" value="<%=(Integer)filesMap.get("pId") %>">
	<input type="hidden" id="uf_parent" name="uf_parent" value="<%=(Integer)filesMap.get("parent") %>">
	<input type="hidden" id="uf_del" name="uf_del" value="">
	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="100">
				<col width="170">
			</colgroup>
			<tr>
				<th>등록일자</th>
				<td><%=StringUtil.convertDate(filesMap.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
				<th>등록자</th>
				<td><%=StringUtil.convertString(filesMap.get("regrNm")) %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3" class="file-add-write">
			<% if (filesList != null && filesList.size() > 0) { %>
					<div class="file-list">
				<% for (Files f : filesList) { %>
						<u style="padding: 5px 5px 5px 16px;background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat 2px;"><a href="javascript:fileDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> <a onclick="javascript:delFile($(this), <%=f.getFileId()%>)" class="del"></a></u>
				<% } %>
					</div>
			<% } %>	
					<p><input type="file" name="file-data" id="file-data" class="file-data"></p>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">
					<input class="important" name="uf_title" id="uf_title" type="text" value="<%=StringUtil.convertString(filesMap.get("title")) %>" alt="제목은 ">
				</td>
			</tr>
			<tr>
				<th>첨부설명</th>
				<td colspan="3">
					<textarea id="uf_content" name="uf_content"><%=StringUtil.convertString(filesMap.get("content")) %></textarea>
				</td>
			</tr>
		</table>
	</div>
</form>	
	
</div>	
	
	
	<% } else { %>
	
<div id="boardview">
	<div class="dia-view">
		<table class="board-view">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="100">
				<col width="170">
			</colgroup>
			<tr>
				<th>등록일자</th>
				<td><%=StringUtil.convertDate(filesMap.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
				<th>등록자</th>
				<td><%=StringUtil.convertString(filesMap.get("regrNm")) %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
			<% if (filesList != null && filesList.size() > 0) { %>
					<div class="file-list">
				<% for (Files f : filesList) { %>
						<u style="background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat;"><a href="javascript:fileDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> </u>
				<% } %>
					</div>
			<% } %>	
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><%=StringUtil.replaceHtml(StringUtil.convertString(filesMap.get("title"))) %></td>
			</tr>
			<tr>
				<th>첨부설명</th>
				<td colspan="3"><pre><%=StringUtil.replaceHtml(StringUtil.convertString(filesMap.get("content"))) %></pre></td>
			</tr>
		</table>
  	</div>
</div>
	
	<% } %>



<% } else { %>

<div id="register">

<form name="file_upload_form" id="file_upload_form" action="/files/file_register" method="post" enctype="multipart/form-data">
	<input type="hidden" id="uf_parent" name="uf_parent">
	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="100">
				<col width="170">
			</colgroup>
			<tr>
				<th>등록일자</th>
				<td><%=DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss")%></td>
				<th>등록자</th>
				<td><%=userInfo.getAcct().getAcctNm() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3" class="file-add-write">
			<% if (filesList != null && filesList.size() > 0) { %>
					<div class="file-list">
				<% for (Files f : filesList) { %>
						<u style="padding: 5px 5px 5px 16px;background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat 2px;"><a href="javascript:fileDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> <a href="#" class="del"></a></u>
				<% } %>
					</div>
			<% } %>	
					<p><input type="file" name="file-data" id="file-data" class="file-data"></p>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">
					<input class="important" name="uf_title" id="uf_title" type="text" value="<%=StringUtil.convertString(filesMap.get("title")) %>" alt="제목은 ">
				</td>
			</tr>
			<tr>
				<th>첨부설명</th>
				<td colspan="3">
					<textarea id="uf_content" name="uf_content"><%=StringUtil.convertString(filesMap.get("content")) %></textarea>
				</td>
			</tr>
		</table>
	</div>
</form>	

</div>

<% } %>



