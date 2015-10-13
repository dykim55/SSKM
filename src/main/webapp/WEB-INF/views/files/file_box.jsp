<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.Constants
	        , com.cyberone.sskm.model.Files
            , com.cyberone.sskm.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<Files> appxList = (List<Files>)request.getAttribute("appxList");
%>

<script>

$(".detail ul").on("mouseleave", function() {
	$(".detail ul").remove();
});

$(".detail ul").find(".close").click(function(){
	$(".detail ul").remove();
});

</script>

<!-- ul style="white-space: nowrap;" -->
<% for (Files f : appxList) { %>
	<li style="padding: 0px 0px 3px 20px;background:url(/images/detail/fileicon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat;"><a href="javascript:fileDownload(<%=f.getFileId() %>)"><%=f.getFileOrgNm() %></a></li>
<% } %>
	<a href="#none" class="close"></a>
<!-- /ul -->
