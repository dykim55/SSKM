<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil
            , com.cyberone.scourt.utils.Encryption"
    
%>

<%@ include file="../include/header.jsp"%>

<%
    int nPage = Integer.valueOf(request.getParameter("page"));
    String sSearchWord = request.getParameter("searchWord");
%>

<script type="text/javascript">

$(document).ready(function() {
	$("#editorTd").load("/daumeditor/editor.html");
});

function saveContent() {
    Editor.save(); // 이 함수를 호출하여 글을 등록하면 된다.
}

</script>

<div class="board_pane" style="background-color:lightgray;font: 12px 맑은 고딕;padding: 10px 0px;">

    <h1 style="margin: 0px auto 10px auto;width: 200px;text-align: center;">개인정보관리</h1>

<form name="tx_editor_form" id="tx_editor_form" action="/privacyReg" method="post" enctype="multipart/form-data">
    <input type="hidden" name="page" value="<%=nPage%>">
    <input type="hidden" name="searchWord" value="<%=sSearchWord%>">
    <table>
        <colgroup>
            <col style="width: 20%">
            <col style="width: *">
        </colgroup>
        <tbody>
            <tr class="firstTh">
                <th scope="row">제목</th>
                <td>
                    <input name="p_title" id="p_title" type="text" maxlength="70" style="width:99%; *width:95% /* IE7 */; ime-mode:active;">
                </td>
            </tr>
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
    
    <div class="tableBtnBox">
        <a href="#" onclick="javascript:saveContent();">
            등록
        </a>&nbsp;
        <a href="/privacy?page=<%=nPage %>&searchWord=<%=sSearchWord %>">
            취소
        </a>
    </div>
    
</form>
    
</div>

<%@ include file="../include/footer.jsp"%>