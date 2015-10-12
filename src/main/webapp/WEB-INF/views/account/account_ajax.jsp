<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> acctList = (List<HashMap<String, Object>>)request.getAttribute("list");

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

$(document).ready(function() {

	$(".board").find("tbody").on("click", "tr", function() {
		acctDetail($(this).attr("data-tt-id"));
	});
	
	$(".board").find("tbody").on("mouseenter", "td", function() {
		$(this).parent().find(".option").show();
		$(this).parent().find("td").css({background:"#f2f2f2"});
	});

	$(".board").find("tbody").on("mouseleave", "td", function() {
		$(this).parent().find(".option").hide();
		$(this).parent().find("td").removeAttr("style");
	});
	
	$("#pg_selbox").change(function() {
		ACCT_PG.rows($(".paging select").val());
	});
	
});

</script>

    <table id="account-grid" class="board">
		<colgroup>
			<col width="5%">
			<col width="12%">
			<col width="12%">
			<col width="15%">
			<col width="15%">
			<col width="14%">
			<col width="12%">
			<col width="*">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>계정아이디</th>
				<th>성명</th>
				<th>접근계정그룹</th>
				<th>부서</th>
				<th>직급</th>
				<th>등록일</th>
				<th>최근접속일시</th>
			</tr>
		</thead>
        <tbody>
    <% if (acctList.size() > 0) {
			int n=1;
           for (HashMap<String, Object> map : acctList) { %>
            <tr data-tt-id='<%=map.get("acctId") %>'>
                <td>
                    <span><%=((nPage-1) * nRows) + n++ %></span>
                </td>
                <td>
                    <span><a><%=StringUtil.convertString(map.get("acctId")) %></a></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("acctNm")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("deptNm")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("oflvNm")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertDate(map.get("regDtime"),"yyyy-MM-dd") %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertDate(map.get("latestDtime"),"yyyy-MM-dd HH:mm:ss") %></span>
                </td>
                
            </tr>
    <%     } 
       } %>        
        </tbody>
    </table>

<% if (acctList.size() > 0) { %>
    <div class="paging">
    	<div>
        	<a class="prev" href="javascript:ACCT_PG.move(<%=nPrevPageNo %>);"></a>
        </div>
        <div class="pl15 pr15">
<% for (int i=nGroupStartPos; i<=nGroupEndPos; i++) { %>
    <% if (nPage == i) { %>
        	<u class="selected"><%=i %></u>
    <% } else { %>
        	<a class="page" href="javascript:ACCT_PG.move(<%=i %>);"><%=i %></a>
    <% }  %>
<% } %>
		</div>
		<div>
        	<a class="next" href="javascript:ACCT_PG.move(<%=nNextPageNo %>);"></a>
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
    