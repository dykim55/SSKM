<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> productList = (List<HashMap<String, Object>>)request.getAttribute("list");
String sParent = request.getParameter("parent");

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
	
	$(".board").find("tbody").on("mouseenter", "td", function() {
		$(this).parent().find(".option").show();
		$(this).parent().find("td").css({background:"#f2f2f2"});
	});

	$(".board").find("tbody").on("mouseleave", "td", function() {
		$(this).parent().find(".option").hide();
		$(this).parent().find("td").removeAttr("style");
	});

	$(".board").find(".filebox").find(".clip").on("click", function() {
		$(this).parents(".board").find(".clip").removeAttr("style");
		$(this).parents(".board").find("ul").hide();
		$(this).css({background:"url(/images/detail/icon_clip_on.png) no-repeat"});
		$(this).parent().find("ul").show(200);
	});
	
	$("#pg_selbox").change(function() {
		PG.rows($(".paging select").val());
	});
	
});

</script>

<div class="detail">
	<div class="content-head">
		<div class="head-end">
			<div class="set-table">				
		
				<div class="left-set">
				</div>
				<div class="right-set">
				<% if (!StringUtil.isEmpty(sParent)) { %>
					<button type="button" onclick="javascript:fileUpload();">파일등록</button>
				<% } %>
					<button type="button" onclick="javascript:createFolder();">새 폴더</button>
				</div>
		
			</div>
		</div>
	</div>
	
<% if (productList != null && productList.size() > 0) { %>
	<table class="board">
		<colgroup>
			<col width="80">
			<col width="*">
			<col width="100">
			<col width="150">
			<col width="250">
			<col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>첨부파일</th>
				<th>등록자</th>
				<th>등록일자</th>
				<th>조회</th>
			</tr>
		</thead>
		<tbody>
    <%
		   int n=0;
           for (HashMap<String, Object> map : productList) { %>
            <tr data-tt-id='<%=map.get("pId") %>'>
				<td><%=((nTotalRecord - ((nPage-1) * nRows))- n) %></td>
				<td>
					<div class="subject">
						<a href="javascript:fileUpload(<%=StringUtil.convertString(map.get("pId")) %>)"><%=StringUtil.convertString(map.get("title")) %></a>
						<div class="option">
							<a href="#" onclick="deleteFile(<%=StringUtil.convertString(map.get("pId")) %>)" class="delete"></a>
						</div>
					</div>
				</td>
				<td>
			<% if (StringUtil.convertString(map.get("fileYn")).equals("y")) { %>
					<!-- file list -->
					<div class="filebox">
						<a href="javascript:fileBox(<%=StringUtil.convertString(map.get("pId")) %>)" class="clip"></a>
					</div>
			<% } %>
				</td>
				<td><%=StringUtil.convertString(map.get("regrNm")) %></td>
				<td><%=StringUtil.convertDate(map.get("regDtime"), "yyyy-MM-dd HH:mm:ss") %></td>
				<td><%=StringUtil.convertString(map.get("query")) %></td>
			</tr>
	<%     
				n++;
			} %>
		
		</tbody>
	</table>

<% if (productList.size() > 0) { %>
    <div class="paging">
    	<div>
        	<a class="prev" href="javascript:PG.move(<%=nPrevPageNo %>);"></a>
        </div>
        <div class="pl15 pr15">
<% for (int i=nGroupStartPos; i<=nGroupEndPos; i++) { %>
    <% if (nPage == i) { %>
        	<u class="selected"><%=i %></u>
    <% } else { %>
        	<a class="page" href="javascript:PG.move(<%=i %>);"><%=i %></a>
    <% }  %>
<% } %>
		</div>
		<div>
        	<a class="next" href="javascript:PG.move(<%=nNextPageNo %>);"></a>
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

<% } else { %>

	<div id="emptycontent" style="font-size: 16px;    color: #888;    position: absolute;    text-align: center;    top: 30%;    width: 85%;">
		<div class="icon-folder"></div>
		<h2>파일이 없습니다.</h2>
	</div>

<% } %>
	
</div>
