<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
	String sBbsSct = StringUtil.convertString(request.getParameter("bbsSct"));

    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> bbsList = (List<HashMap<String, Object>>)request.getAttribute("list");
    
    int nRows =  (Integer)request.getAttribute("rows");
    int nTotalRecord =  (Integer)request.getAttribute("record");
    int nTotalPage = (Integer)request.getAttribute("total");
    int nGroupPos = (Integer)request.getAttribute("group");
    int nGroupEndPos = (Integer)request.getAttribute("groupEnd");
    int nGroupStartPos = (Integer)request.getAttribute("groupStart");
    int nPrevPageNo = (Integer)request.getAttribute("prev");
    int nNextPageNo = (Integer)request.getAttribute("next");
    int nPage = (Integer)request.getAttribute("page");
    
    String sSearchWord = StringUtil.convertString(request.getParameter("searchWord"));
%>

<script type="text/javascript">

$(document).ready(function() {

	$(".board").find("tbody").on("click", "tr", function() {
		console.log("click!");
		articleWrite(<%=sBbsSct%>, $(this).attr("data-tt-id"));
	});

	$(".board").find("tbody").on("mouseenter", "td", function() {
		$(this).parent().find("td").css({background:"#f2f2f2"});
	});

	$(".board").find("tbody").on("mouseleave", "td", function() {
		$(this).parent().find("td").css({background:"#ffffff"});
	});
	
	$("#pg_selbox").change(function() {
		ARTICLE_PG.rows($(".paging select").val());
	});

});

</script>

	<table class="board">
        <colgroup>
            <col width="5%">
            <col width="*">
            <col width="8%">
            <col width="20%">
            <col width="10%">
            <col width="8%">
        </colgroup>
        <thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>첨부</th>
              <th>작성일</th>
              <th>작성자</th>
              <th>조회</th>
            </tr>
         </thead>
        <tbody>
    <%  if (bbsList.size() > 0) {
    	    int m = (nPage - 1) * nRows; 
            for (HashMap<String, Object> map : bbsList) { %>
            <tr data-tt-id='<%=map.get("bbsId") %>'>
                <td><%=nTotalRecord - m++ %></td>
                <td style="text-align: left;padding-left: 10px;"><%=StringUtil.replaceHtml(StringUtil.convertString(map.get("bbsTit"))) %></td>
                <td>
					<% if (StringUtil.convertString(map.get("fileYn")).equals("y")) { %>
							<!-- file list -->
							<div class="filebox">
								<a href="#" onclick="javascript:fileBox(24)" class="clip"></a>
							</div>
					<% } %>
                </td>
                <td><%=StringUtil.convertDate(map.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
                <td><%=StringUtil.convertString(map.get("regrNm")) %></td>
                <td><%=StringUtil.convertString(map.get("qryCnt")) %></td>
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
	
<% if (bbsList.size() > 0) { %>
    <div class="paging">
    	<div>
        	<a class="prev" href="javascript:ARTICLE_PG.move(<%=nPrevPageNo %>);"></a>
        </div>
        <div class="pl15 pr15">
<% for (int i=nGroupStartPos; i<=nGroupEndPos; i++) { %>
    <% if (nPage == i) { %>
        	<u class="selected"><%=i %></u>
    <% } else { %>
        	<a class="page" href="javascript:ARTICLE_PG.move(<%=i %>);"><%=i %></a>
    <% }  %>
<% } %>
		</div>
		<div>
        	<a class="next" href="javascript:ARTICLE_PG.move(<%=nNextPageNo %>);"></a>
        </div>
        <div>
			<select id="pg_selbox" style="margin-left: 10px;">
				<option <%= nRows==15 ? "selected" : "" %>>15</option>
				<option <%= nRows==30 ? "selected" : "" %>>30</option>
				<option <%= nRows==50 ? "selected" : "" %>>50</option>
				<option <%= nRows==100 ? "selected" : "" %>>100</option>
			</select>        
		</div>
    </div>
<% } %>
