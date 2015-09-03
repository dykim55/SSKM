<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
    int nPage = Integer.valueOf(request.getParameter("page"));
%>

<script type="text/javascript">

RSSFEED_WRITE = (function() {

    return {
           init: function() {
               console.log('RSSFEED_WRITE.init()');
               $("#editorTd").load("/daumeditor/editor.html");
           }
    };
    
})();

$(document).ready(function() {
	RSSFEED_WRITE.init();
});

function saveFeed() {
    document.feedForm.submit();
}

</script>

<div class="board_pane" style="background-color:lightgray;font: 12px 맑은 고딕;padding: 10px 0px;">

    <h1 style="margin: 0px auto 10px auto;width: 200px;text-align: center;">RSS피드 등록</h1>

<form name="feedForm" id="feedForm" action="/rssFeedReg" method="post">
    <input type="hidden" name="page" value="<%=nPage%>">
    <table class="popTable" cellpadding="0" cellspacing="0">
        <colgroup>
            <col width="15%"><col width="35%">
            <col width="15%"><col width="35%">
        </colgroup>
        <tbody>
        
            <tr>
                <th>Guid 명</th>
                <td class="left" colspan="3">
                    <input type="text" name="guidParam" id="guidParam" value="" class="normal focus_e" style="width: 36%; color: rgb(82, 82, 82); background: rgba(0, 0, 0, 0.2);">
                    <br>RSS 주소를 부라우저에 실행시켜 RSS XML 데이터에서 아래 내용을 확인한다
                    <br>curPage=1&amp;menu_dist=2&amp;<span style="color: #FF0000;font-weight: bold;">seq</span>=23239, url에 Guid명이 있는 경우 <span style="color: #FF0000;font-weight: bold;">seq</span> 입력
                    <br>http://www.etnews.com/20150114000098, url에 Guid명이 없는 경우 <span style="color: #FF0000;font-weight: bold;">guid</span> 입력 
                </td>
            </tr>
            
            <tr>
                <th>RSS 출처</th>
                <td class="left" colspan="3"><input type="text" name="rssSrc" id="rssSrc" value="" class="normal focus_e" style="width:96%;"></td>
            </tr>
            <tr>
                <th>RSS 주소</th>
                <td class="left" colspan="3"><input type="text" name="rssFeed" id="rssFeed" value="" class="normal focus_e" style="width:96%;"></td>
            </tr>
        </tbody>
    </table>    
    
    <div class="tableBtnBox">
        <a href="#" onclick="javascript:saveFeed();">
            등록
        </a>&nbsp;
        <a href="/rssFeedMng?page=<%=nPage %>">
            취소
        </a>
    </div>
    
</form>
    
</div>

<%@ include file="../include/footer.jsp"%>