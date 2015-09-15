<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> acctList = (List<HashMap<String, Object>>)request.getAttribute("list");
%>

<script type="text/javascript">

function acctDetail(id) {
	console.log(id);
    DIALOG.Open().load("/account/create_account", {id : id}, function() {
    	CREATE_ACCOUNT.init($(this), undefined);
    });
}	

$(document).ready(function() {
	
});

</script>

    <table id="account-grid" class="treetable">
        <colgroup>
            <col style="width: 10%">
            <col style="width: 20%">
            <col style="width: 25%">
            <col style="width: 25%">
            <col style="width: 20%">
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>계정ID</th>
            <th>성명</th>
            <th>부서</th>
            <th>직급</th>
          </tr>
        </thead>
        <tbody>
    <% if (acctList.size() > 0) {
			int n=1;
           for (HashMap<String, Object> map : acctList) { %>
            <tr data-tt-id='<%=map.get("acctId") %>'>
                <td>
                    <span><%=n++ %></span>
                </td>
                <td>
                    <span><a href="javascript:acctDetail('<%=StringUtil.convertString(map.get("acctId")) %>')"><%=StringUtil.convertString(map.get("acctId")) %></a></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("acctNm")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("deptCd")) %></span>
                </td>
                <td>
                    <span><%=StringUtil.convertString(map.get("oflvCd")) %></span>
                </td>
            </tr>
    <%     } 
       } %>        
        </tbody>
    </table>
