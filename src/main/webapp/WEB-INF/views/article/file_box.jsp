<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.Constants
	        , com.cyberone.scourt.model.AppxFile
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<AppxFile> appxList = (List<AppxFile>)request.getAttribute("appxList");
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
<% for (AppxFile f : appxList) { %>
	<li style="padding: 0px 0px 3px 20px;background:url(/images/file_icon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat;"><a href="javascript:appxDownload(<%=f.getFileId() %>)"><%=f.getFileOrgNm() %></a></li>
<% } %>
	<a href="#none" class="close"></a>
<!-- /ul -->
