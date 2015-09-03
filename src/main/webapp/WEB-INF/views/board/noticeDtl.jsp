<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.Constants
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.model.AppxFile
            , com.cyberone.scourt.utils.StringUtil
            , com.cyberone.scourt.utils.Encryption"
    
%>

<%@ include file="../include/header.jsp"%>

<%
    int nPage = Integer.valueOf(request.getParameter("page"));
    String sSearchWord = request.getParameter("searchWord");
    
    @SuppressWarnings("unchecked")
    HashMap<String, Object> mapDetail = (HashMap<String, Object>)request.getAttribute("mapDetail");
    
    @SuppressWarnings("unchecked")
    List<AppxFile> appxFiles = (List<AppxFile>)request.getAttribute("appxFiles");
    
    Boolean bEditable = (Boolean)request.getAttribute("editable");
    
%>

<script type="text/javascript">

NOTICE_DTL = (function() {

	return {
		   init: function() {
			   console.log('NOTICE_DTL.init()');
			   $("#editorTd").load("/daumeditor/editor.html", function() {
				   
	                $.ajax({
	                    url : "/attachInfo",
	                    type : 'POST',
	                    data : {"bbs": '<%=StringUtil.convertString(mapDetail.get("bbsId"))%>'},
	                    dataType: 'json',
	                    success : function(data) {
	                        if (data.status == "success") {

	                            var attachments = {};
	                            attachments['image'] = [];
	                            $.each(data.attachInfo, function(idx, p) {
	                                console.log(p);
	                                attachments['image'].push(p);   
	                            });
	                            
	                            Editor.modify({
	                                "attachments": function () { /* 저장된 첨부가 있을 경우 배열로 넘김, 위의 부분을 수정하고 아래 부분은 수정없이 사용 */
	                                    var allattachments = [];
	                                    for (var i in attachments) {
	                                        allattachments = allattachments.concat(attachments[i]);
	                                    }
	                                    return allattachments;
	                                }(),
	                                "content": document.getElementById("contents_source") /* 내용 문자열, 주어진 필드(textarea) 엘리먼트 */
	                            });
	                        }
	                    }
	                });
				   
			   });
			   
		   }
	};
	
})();

$(document).ready(function() {
	NOTICE_DTL.init();
});

function delSubmit() {
    document.delForm.submit();
}

function saveContent() {
    Editor.save(); // 이 함수를 호출하여 글을 등록하면 된다.
}

</script>

<div class="board_pane" style="background-color:lightgray;font: 12px 맑은 고딕;padding: 10px 0px;">

    <h1 style="margin: 0px auto 10px auto;width: 200px;text-align: center;">공지사항</h1>

<% if (bEditable != null && bEditable) { // 수정 %>

<form name="tx_editor_form" id="tx_editor_form" action="/noticeReg" method="post" enctype="multipart/form-data">
    <input type="hidden" name="bbs" value="<%=StringUtil.convertString(mapDetail.get("bbsId"))%>">
    <input type="hidden" name="page" value="<%=nPage%>">
    <input type="hidden" name="searchWord" value="<%=sSearchWord%>">
    <table style="width: 100%;">
        <colgroup>
            <col style="width: 20%">
            <col style="width: *">
        </colgroup>
        <tbody>
            <tr class="firstTh">
                <th scope="row">제목</th>
                <td>
                    <input name="p_title" id="p_title" type="text" maxlength="70" style="width:99%; *width:95% /* IE7 */; ime-mode:active;" value="<%=StringUtil.convertString(mapDetail.get("bbsTit"))%>">
                </td>
            </tr>
        <% if (appxFiles.size() > 0) { %>
            <tr>
                <th>첨부파일</th>
                <td colspan="5" class="left file">
                <% for (AppxFile af : appxFiles) { %>
                    <p style="margin-bottom: 5px;">
                    <img src="/images/file_icon/<%=Constants.getFileExtension(af.getFileOrgNm()) %>.png"><a href="javascript:fileDownload(<%=af.getFileId()%>)"><%=af.getFileOrgNm()%></a>
                    </p>
                <% } %>
                </td>
            </tr>
        <% } %>
            <tr>
                <th>첨부파일</th>
                <td>
                    <input type="file" name="file-data" class="file-data" style="width: 95%">
                </td>
            </tr>
            <tr class="last">
                <th>내용</th>
                <td id="editorTd"></td>
            </tr>
        </tbody>
    </table>

</form>
    
    <div class="tableBtnBox">
        <a href="#" onclick="javascript:saveContent();">
            수정
        </a>&nbsp;
        <a href="#" onclick="javascript:delSubmit();">
            삭제
        </a>&nbsp;
        <a href="/notice?page=<%=nPage %>&searchWord=<%=sSearchWord %>">
            취소
        </a>
    </div>

<form name="delForm" method="post" action="/noticeDel" onsubmit="return delSubmit();">
    <input type="hidden" name="bbs" value="<%=StringUtil.convertString(mapDetail.get("bbsId"))%>">
</form>    

<textarea id="contents_source" style="display:none;"><%=mapDetail.get("bbsCont")%></textarea>


<% } else { %>

    <table class="formWrap">
        <colgroup>
            <col style="width: 20%">
            <col style="width: *">
        </colgroup>
        <tbody>
            <tr class="firstTh">
		        <th scope="row">제목</th>
		        <td><%=StringUtil.convertString(mapDetail.get("bbsTit")) %></td>
            </tr>
            <tr>
                <th>작성일</th>
                <td><%=StringUtil.convertDate(mapDetail.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
            </tr>
            <tr>
                <th>조회</th>
                <td><%=StringUtil.convertString(mapDetail.get("qryCnt")) %></td>
            </tr>
        <% if (appxFiles.size() > 0) { %>
            <tr>
                <th>첨부파일</th>
                <td colspan="5" class="left file">
                <% for (AppxFile af : appxFiles) { %>
                    <p style="margin-bottom: 5px;">
                    <img src="/images/file_icon/<%=Constants.getFileExtension(af.getFileOrgNm()) %>.png"><a href="javascript:fileDownload(<%=af.getFileId()%>)"><%=af.getFileOrgNm()%></a>
                    </p>
                <% } %>
                </td>
            </tr>
        <% } %>
            <tr class="last">
                <th>내용</th>
                <td>
                    <%=StringUtil.convertString(mapDetail.get("bbsCont")) %>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="tableBtnBox">
        <a href="/notice?page=<%=nPage %>&searchWord=<%=sSearchWord %>">
            목록
        </a>
    </div>

<% } %>

</div>
  


<%@ include file="../include/footer.jsp"%>