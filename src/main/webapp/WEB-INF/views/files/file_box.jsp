<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.Constants
	        , com.cyberone.scourt.model.Files
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<Files> appxList = (List<Files>)request.getAttribute("appxList");
%>

<script>
	listfileview(); //board list for file view
	function listfileview(){
		var boxPath = $(".board").find(".filebox");
		
		boxPath.find(".close").click(function(){
			$(this).parents(".board").find(".clip").removeAttr("style");
			$(this).parents(".board").find("ul").hide();
		});
	}
</script>

<ul style="white-space: nowrap;">
<% for (Files f : appxList) { %>
	<li style="padding: 0px 0px 3px 20px;background:url(/images/file_icon/<%=Constants.getFileExtension(f.getFileOrgNm()) %>.png) no-repeat;"><a href="javascript:fileDownload(<%=f.getFileId() %>)"><%=f.getFileOrgNm() %></a></li>
<% } %>
	<a href="#none" class="close"></a>
</ul>
