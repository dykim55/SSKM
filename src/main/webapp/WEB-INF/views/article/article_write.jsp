<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , com.cyberone.sskm.model.UserInfo
        , com.cyberone.sskm.Constants
        , com.cyberone.sskm.model.AppxFile
        , com.cyberone.sskm.utils.DateUtil
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
	UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

	String sBbsSct = StringUtil.convertString(request.getParameter("bbsSct"));

    @SuppressWarnings("unchecked")
    HashMap<String, Object> bbsMap = (HashMap<String, Object>)request.getAttribute("bbsMap");
    if (StringUtil.isEmpty(bbsMap)) {
    	bbsMap = new HashMap<String, Object>(); 
    }
    List<AppxFile> appxFiles = (List<AppxFile>)request.getAttribute("appxFiles");
%>

<script type="text/javascript">

ARTICLE_DLG = (function() {
	var _Dlg;
	var _bProcessing = false;
	
	$("#article_form").ajaxForm({
        success: function(res, status) {
        	ARTICLE_PG.reload(<%=sBbsSct %>, function() { _Dlg.dialog("close"); });
        	_bProcessing = false;
        }
    });
	
	$("#uf_file").change(function() {
		var str = $(this).val().substr($(this).val().lastIndexOf("\\") + 1);
		$("#uf_title").val(str.substring(0, str.lastIndexOf(".")));
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
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 800,
                modal: true,
                title: <% if (sBbsSct.equals("1")) { %> "공지사항" <% } else if (sBbsSct.equals("2")) { %> "인수인계" <% } %>,
                buttons: {
<% if (!bbsMap.containsKey("bbsId") || (bbsMap.containsKey("bbsId") && StringUtil.convertString(bbsMap.get("regr")).equals(userInfo.getAcct().getAcctId()))) { //수정/상세 %>
                	
	<% if (bbsMap.containsKey("bbsId")) { %> "수정" <% } else { %> "등록" <% } %>: function() {
		
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
							$("#article_form").submit();
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



<% if (bbsMap.containsKey("bbsId")) { //수정/상세 %>

	<% if (StringUtil.convertString(bbsMap.get("regr")).equals(userInfo.getAcct().getAcctId())) { %>

<div id="register">
	
<form name="article_form" id="article_form" action="/article/article_register" method="post" enctype="multipart/form-data">
	<input type="hidden" id="uf_id" name="uf_id" value="<%=(Integer)bbsMap.get("bbsId") %>">
	<input type="hidden" id="uf_sct" name="uf_sct" value="<%=sBbsSct %>">
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
				<th>작성일자</th>
				<td><%=StringUtil.convertDate(bbsMap.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
				<th>작성자</th>
				<td><%=StringUtil.convertString(bbsMap.get("regrNm")) %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">
					<input class="important" name="uf_title" id="uf_title" type="text" value="<%=StringUtil.convertString(bbsMap.get("bbsTit")) %>"  alt="제목은 ">
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<textarea class="important" id="uf_content" name="uf_content" style="height: 200px;" alt="내용은 "><%=StringUtil.convertString(bbsMap.get("bbsCont")) %></textarea>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3" class="file-add-write">
			<% if (appxFiles != null && appxFiles.size() > 0) { %>
					<div class="file-list">
				<% for (AppxFile f : appxFiles) { %>
						<u style="padding: 5px 5px 5px 16px;background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat 2px;"><a href="javascript:appxDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> <a onclick="javascript:delFile($(this), <%=f.getFileId()%>)" class="del"></a></u>
				<% } %>
					</div>
			<% } %>	
					<p><input type="file" name="file-data" id="file-data" class="file-data"></p>
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
				<th>작성일자</th>
				<td><%=StringUtil.convertDate(bbsMap.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
				<th>작성자</th>
				<td><%=StringUtil.convertString(bbsMap.get("regrNm")) %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><%=StringUtil.replaceHtml(StringUtil.convertString(bbsMap.get("bbsTit"))) %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><pre style="min-height:200px;white-space: pre-wrap;font-family: inherit;"><%=StringUtil.replaceHtml(StringUtil.convertString(bbsMap.get("bbsCont"))) %></pre></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
			<% if (appxFiles != null && appxFiles.size() > 0) { %>
					<div class="file-list">
				<% for (AppxFile f : appxFiles) { %>
						<u style="background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat;"><a href="javascript:appxDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> </u>
				<% } %>
					</div>
			<% } %>	
				</td>
			</tr>
		</table>
  	</div>
</div>
	
	<% } %>



<% } else { %>

<div id="register">

<form name="article_form" id="article_form" action="/article/article_register" method="post" enctype="multipart/form-data">
	<input type="hidden" id="uf_sct" name="uf_sct" value="<%=sBbsSct %>">

	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
				<col width="100">
				<col width="170">
			</colgroup>
			<tr>
				<th>작성일자</th>
				<td><%=DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss")%></td>
				<th>작성자</th>
				<td><%=userInfo.getAcct().getAcctNm() %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">
					<input class="important" name="uf_title" id="uf_title" type="text" value="<%=StringUtil.convertString(bbsMap.get("bbsTit")) %>" alt="제목은 ">
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<textarea class="important" id="uf_content" name="uf_content" style="height: 200px;" alt="내용은 "><%=StringUtil.convertString(bbsMap.get("bbsCont")) %></textarea>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3" class="file-add-write">
			<% if (appxFiles != null && appxFiles.size() > 0) { %>
					<div class="file-list">
				<% for (AppxFile f : appxFiles) { %>
						<u style="padding: 5px 5px 5px 16px;background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat 2px;"><a href="javascript:appxDownload(<%=f.getFileId()%>)"><%=f.getFileOrgNm() %></a> <a href="#" class="del"></a></u>
				<% } %>
					</div>
			<% } %>	
					<p><input type="file" name="file-data" id="file-data" class="file-data"></p>
				</td>
			</tr>
		</table>
	</div>
</form>	

</div>

<% } %>



