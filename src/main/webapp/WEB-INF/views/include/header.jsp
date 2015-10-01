<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import = "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil
    		, com.cyberone.scourt.Common"
%>

<%
UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

List<HashMap<String, Object>> menuList = Common.selectMenu();

%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>대법원 지식관리 홈페이지</title>

<link rel="stylesheet" href="/css/font.css">
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/section.css">
<link rel="stylesheet" href="/css/icon.css">
<link rel="stylesheet" href="/css/jquery.treetable.fix.css">
<link rel="stylesheet" href="/css/jquery-ui-1.10.2.custom.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.10.2.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.treetable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.library.js"></script>
<script type="text/javascript" src="/js/fileDownload.js"></script>
<script type="text/javascript" src="/js/utils.js"></script>
    
<script>

$(document).ready(function(){
	
    $("#navigation li a").click(function() {
        $("#navigation li a").removeClass("active");
        $(this).addClass("active");
    });
	
});

</script>

</head>

<body>
	<!-- depth 1. header -->
	<div class="header">
		
		<!-- depth 2. top menu ui -->
		<div class="nav">
			<!-- depth3. top menu -->
			<div class="menu">
				<dl>
			<% for (HashMap<String, Object> hMap : menuList) { 
					if ("0".equals(StringUtil.convertString(hMap.get("prtsCd"))) && userInfo.isMenuAuth(StringUtil.convertString(hMap.get("menuCd")))) { %>
					    <dt code="<%=StringUtil.convertString(hMap.get("menuCd")) %>" href="<%=StringUtil.convertString(hMap.get("execUrl")) %>"><%=StringUtil.convertString(hMap.get("menuNm")) %></dt>
			<%		}
			   } %>
				</dl>

			<% for (HashMap<String, Object> hMap : menuList) { 
					if ("0".equals(StringUtil.convertString(hMap.get("prtsCd"))) && userInfo.isMenuAuth(StringUtil.convertString(hMap.get("menuCd")))) { %>
						<ol class="sub<%=StringUtil.convertString(hMap.get("menuCd"))%>">
						<% for (HashMap<String, Object> sMap : menuList) { 
								if (StringUtil.convertString(sMap.get("prtsCd")).equals(StringUtil.convertString(hMap.get("menuCd")))) { %>
						
									<li href="<%=StringUtil.convertString(sMap.get("execUrl")) %>"><%=StringUtil.convertString(sMap.get("menuNm")) %></li>
						
						<%      }
						   } %>
						</ol>
			<%		}
			   } %>
			</div>

			<!-- depth 3. location-->
			<div class="location">
			</div>
		</div>
	</div>

	