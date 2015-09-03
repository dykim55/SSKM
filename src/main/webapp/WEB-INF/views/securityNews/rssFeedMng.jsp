<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> feedList = (List<HashMap<String, Object>>)request.getAttribute("list");
    
    int nRows =  (Integer)request.getAttribute("rows");
    int nTotalRecord =  (Integer)request.getAttribute("record");
    int nTotalPage = (Integer)request.getAttribute("total");
    int nGroupPos = (Integer)request.getAttribute("group");
    int nGroupEndPos = (Integer)request.getAttribute("groupEnd");
    int nGroupStartPos = (Integer)request.getAttribute("groupStart");
    int nPrevPageNo = (Integer)request.getAttribute("prev");
    int nNextPageNo = (Integer)request.getAttribute("next");
    int nPage = (Integer)request.getAttribute("page");
%>

<script type="text/javascript">

RSS_FEED = (function() {

	return {
		   init: function() {
			   console.log('RSS_FEED.init()');
		   }
	};
	
})();

$(document).ready(function() {

	RSS_FEED.init();
    
});

</script>

<div class="board_pane" style="background-color:lightgray;font: 12px 맑은 고딕;padding: 10px 0px;">

    <h1 style="margin: 0px auto 10px auto;width: 200px;text-align: center;">RSS 피드관리</h1>

    <table cellpadding="0" cellspacing="0" border="1" style="width: 70%;margin: 0 auto;">
        <colgroup>
            <col width="5%">
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="10%">
            <col width="10%">
            <col width="*">
        </colgroup>
        <thead>
            <tr>
              <th>번호</th>
              <th>출처</th>
              <th>RSS Feed</th>
              <th>Guid Keyword</th>
              <th>Last Guid</th>
              <th>등록자</th>
              <th>등록일자</th>
            </tr>
         </thead>
        <tbody>
    <%  if (feedList.size() > 0) {
    	    int m = (nPage - 1) * nRows; 
            for (HashMap<String, Object> map : feedList) { %>
            <tr>
                <td><%=m++ %></td>
                <td><%=StringUtil.convertString(map.get("rssSrc")) %></td>
                <td><a class="pre" href="/rssFeedDtl?page=<%=nPage%>&seq=<%=StringUtil.convertString(map.get("feedSeq"))%>"><%=StringUtil.convertString(map.get("rssFeed")) %></a></td>
                <td><%=StringUtil.convertString(map.get("guidParam")) %></td>
                <td><%=StringUtil.convertString(map.get("guidLast")) %></td>
                <td><%=StringUtil.convertString(map.get("regr")) %></td>
                <td><%=StringUtil.convertDate(map.get("regDtime"), "yyyy-MM-dd") %></td>
            </tr>
    <%      }
        } else { %>
            <tr align="center">
                <td colspan="6">
                    <font color="tomato"><b>등록된 데이터가 없습니다!</b></font>
                </td>
            </tr>
    <% } %>
            
        </tbody>
    </table>

<% if (feedList.size() > 0) { %>
    <div style="margin: 5px auto 0px auto;text-align: center;">
        <a class="pre" href="/rssFeedMng?page=<%=nPrevPageNo%>"><img src="/images/btn_page_prev.gif" width="16" height="16" alt="이전"></a>
<% for (int i=nGroupStartPos; i<=nGroupEndPos; i++) { %>
    <% if (nPage == i) { %>
        <strong><span><%=i %></span></strong>
    <% } else { %>
        <a href="/rssFeedMng?page=<%=i %>"><span><%=i %></span></a>
    <% }  %>
<% } %>
        <a class="next" href="/rssFeedMng?page=<%=nNextPageNo%>"><img src="/images/btn_page_next.gif" width="16" height="16" alt="다음"></a>
    </div>
<% } %>

    <div class="tableBtnBox">
        <a href="/rssFeedWrite?page=<%=nPage %>">
            RSS피드 등록
        </a>
    </div>

</div>
  


<%@ include file="../include/footer.jsp"%>