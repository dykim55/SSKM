<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> productList = (List<HashMap<String, Object>>)request.getAttribute("list");
%>

<script type="text/javascript">

$(document).ready(function() {
});

</script>

    <table id="product-grid" class="treetable">
        <colgroup>
            <col style="width: 10%">
            <col style="width: *">
            <col style="width: 20%">
            <col style="width: 20%">
            <col style="width: 10%">
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>등록일</th>
            <th>등록자</th>
            <th>첨부파일</th>
          </tr>
        </thead>
        <tbody>
    <% if (productList.size() > 0) {
			int n=1;
           for (HashMap<String, Object> map : productList) { %>
            <tr data-tt-id='<%=map.get("pid") %>'>
                <td>
                    <span><%=n++ %></span>
                </td>
                <td>
                    <span class='file'><%=StringUtil.convertString(map.get("title")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertDate(map.get("regDtime"),"yyyy-MM-dd") %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("regr")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("fileYn")) %></span>
                </td>
            </tr>
    <%     } 
       } %>        
        </tbody>
    </table>
