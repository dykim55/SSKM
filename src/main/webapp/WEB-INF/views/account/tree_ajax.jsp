<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> acctList = (List<HashMap<String, Object>>)request.getAttribute("acctList");
%>

<script type="text/javascript">

$(document).ready(function() {
	
    $(".account-tree").treetable({ expandable: true });
    
    // Highlight selected row
    $(".account-tree tbody").off("mousedown", "tr");
    $(".account-tree tbody").off("mouseenter", "tr");
    $(".account-tree tbody").off("mouseleave", "tr");
    
    $(".account-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    	
    	ACCT_PG.reload($(this).attr('data-tt-id'), $(this).attr('data-tt-parent-id'), function() {});
    });

    $(".account-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".account-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    });
    
    $(".account-tree tbody").on("mouseenter", "tr", function() {
		$(this).find(".option").show();
		$(this).addClass("hover");
    });

    $(".account-tree tbody").on("mouseleave", "tr", function() {
		$(this).find(".option").hide();
		$(this).removeClass("hover");
    });
    
});

</script>

<table class="account-tree">
    <tbody>
<% if (acctList.size() > 0) {
       for (HashMap<String, Object> map : acctList) { 
       		if ((Long)map.get("level") > 1) { %>
          <tr class="accordionContent" level='<%=map.get("level") %>' data-tt-id='<%=map.get("acctGrpCd") %>' <% if ((Integer)map.get("acctPrntCd") > 0) { %> data-tt-parent-id='<%=map.get("acctPrntCd") %>' <% } %>>
              <td>
                  <span class=''><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
                  <div class="option">
               <% if ((Long)map.get("level") == 2) { %>
                  	<a href="#" onclick="javascript:createAcctGrp(this);" title="계정그룹추가"><i class="fa fa-users"></i></a>
                  	<a href="#" onclick="javascript:modifyAuthGrp(this);" title="권한그룹수정"><i class="fa fa-pencil-square-o"></i></a>
				<a href="#" onclick="javascript:deleteAuthGrp(this);" title="권한그룹삭제"><i class="fa fa-trash-o"></i></a>
		 <% } else { %>
                  	<a href="#" onclick="javascript:createAcct(this);" title="계정추가"><i class="fa fa-user-plus"></i></a>
                  	<a href="#" onclick="javascript:modifyAcctGrp(this);" title="계정그룹수정"><i class="fa fa-pencil-square-o"></i></a>
				<a href="#" onclick="javascript:deleteAcctGrp(this);" title="계정그룹삭제"><i class="fa fa-trash-o"></i></a>
		 <% } %>
                  </div>
              </td>
          </tr>
<%     		}
       } 
   } %>        
    </tbody>
</table>
