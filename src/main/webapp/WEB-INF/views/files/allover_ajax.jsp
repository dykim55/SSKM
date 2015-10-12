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

	$(".board").find("tbody").on("click", "tr", function() {
		if ($(this).attr("data-tt-id")) {
			fileUpload($(this).attr("data-tt-id"));
		}
	});

	$(".board").find("tbody").on("mouseenter", "td", function() {
		$(this).parent().find(".option").show();
		$(this).parent().find("td").css({background:"#f2f2f2"});
	});

	$(".board").find("tbody").on("mouseleave", "td", function() {
		$(this).parent().find(".option").hide();
		$(this).parent().find("td").css({background:""});
	});

	$("#pg_selbox").change(function() {
		ALL_PG.rows($(".paging select").val());
	});
	
});

</script>

<div class="detail" style="max-width: inherit;">
	<div class="content-head" style="max-width: inherit;">
		<div class="head-end">
			<div class="set-table">				
		
				<div class="left-set">
					검색 결과
				</div>
				<div class="right-set">
				</div>
		
			</div>
		</div>
	</div>
	
<% if (productList != null && productList.size() > 0) { %>
	<table class="board" style="table-layout: fixed;max-width: inherit;">
		<colgroup>
			<col width="5%">
			<col width="30%">
			<col width="*">
			<col width="5%">
			<col width="15%">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>파일위치</th>
				<th>제목</th>
				<th>첨부파일</th>
				<th>등록일자</th>
			</tr>
		</thead>
		<tbody>
    <%
		   int n=0;
           for (HashMap<String, Object> map : productList) { %>
            <tr data-tt-id='<%=map.get("pId") %>'>
				<td><%=((nTotalRecord - ((nPage-1) * nRows))- n) %></td>
				<td style="text-overflow:ellipsis; overflow:hidden; text-align:left;padding-left:10px;"><nobr><%=StringUtil.convertString(map.get("pathNm")) %></nobr></td>
				<td>
					<div class="subject">
						<%=StringUtil.convertString(map.get("title")) %>
						<div class="option">
							<a href="#" onclick="deleteFile(<%=StringUtil.convertString(map.get("pId")) %>);" class="delete" title="삭제"></a>
						</div>
					</div>
				</td>
				<td>
			<% if (StringUtil.convertString(map.get("fileYn")).equals("y")) { %>
					<!-- file list -->
					<div class="filebox">
						<a href="#" onclick="javascript:fileBox(<%=StringUtil.convertString(map.get("pId")) %>)" class="clip"></a>
					</div>
			<% } %>
				</td>
				<td><%=StringUtil.convertDate(map.get("regDtime"), "yyyy-MM-dd HH:mm:ss") %></td>
			</tr>
	<%     
				n++;
			} %>
		
		</tbody>
	</table>

<% if (productList.size() > 0) { %>
    <div class="paging">
    	<div>
        	<a class="prev" href="javascript:ALL_PG.move(<%=nPrevPageNo %>);"></a>
        </div>
        <div class="pl15 pr15">
<% for (int i=nGroupStartPos; i<=nGroupEndPos; i++) { %>
    <% if (nPage == i) { %>
        	<u class="selected"><%=i %></u>
    <% } else { %>
        	<a class="page" href="javascript:ALL_PG.move(<%=i %>);"><%=i %></a>
    <% }  %>
<% } %>
		</div>
		<div>
        	<a class="next" href="javascript:ALL_PG.move(<%=nNextPageNo %>);"></a>
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

	<table class="board" style="max-width: inherit;">
		<colgroup>
			<col width="5%">
			<col width="35%">
			<col width="*">
			<col width="5%">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>파일위치</th>
				<th>제목</th>
				<th>첨부파일</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4">
					<h2>검색 결과가 없습니다.</h2>
				</td>
			</tr>
		</tbody>
	</table>
			
<% } %>
	
</div>
