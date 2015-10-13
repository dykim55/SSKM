<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.model.UserInfo
            , com.cyberone.sskm.utils.StringUtil"
    
%>

<%
	UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

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
		articleWrite(<%=sBbsSct%>, $(this).attr("data-tt-id"));
	});

	$(".board").find("tbody").on("mouseenter", "td", function() {
		$(this).parent().find(".option").show();
		$(this).parent().find("td").css({background:"#f2f2f2"});
	});

	$(".board").find("tbody").on("mouseleave", "td", function() {
		$(this).parent().find(".option").hide();
		$(this).parent().find("td").css({background:"#ffffff"});
	});
	
	$("#pg_selbox").change(function() {
		ARTICLE_PG.rows($(".paging select").val());
	});

});

</script>

	<table class="board">
        <colgroup>
            <col width="7%">
            <col width="*">
            <col width="8%">
            <col width="18%">
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
                <td style="text-align: left;padding-left: 10px;">
                	<div class="subject">
	                	<%=StringUtil.replaceHtml(StringUtil.convertString(map.get("bbsTit"))) %>
	                	
	                <% /*if (StringUtil.convertString(map.get("regr")).equals(userInfo.getAcct().getAcctId())) { */ %>
						<div class="option">
							<a href="#" onclick="deleteArticle(<%=StringUtil.convertString(map.get("bbsId")) %>);" class="delete"></a>
						</div>
					<% /*} */ %>
					
					</div>
                </td>
                <td>
					<% if (StringUtil.convertString(map.get("fileYn")).equals("y")) { %>
							<!-- file list -->
							<div class="filebox">
								<a href="#" onclick="javascript:fileBox(<%=StringUtil.convertString(map.get("bbsId")) %>)" class="clip"></a>
							</div>
					<% } %>
                </td>
                <td><%=StringUtil.convertDate(map.get("regDtime"),"yyyy-MM-dd HH:mm:ss") %></td>
                <td><%=StringUtil.convertString(map.get("regrNm")) %></td>
                <td><%=StringUtil.convertString(map.get("qryCnt")) %></td>
            </tr>
    <%      }
        } else { %>
        	<% if (!StringUtil.isEmpty(sSearchWord)) { %>
            <tr align="center">
                <td colspan="6">
                    <h2>검색 결과가 없습니다.</h2>
                </td>
            </tr>
            <% } %>
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
