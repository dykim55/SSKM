<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import = "com.cyberone.scourt.model.UserInfo"
%>

<%
UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>대법원 지식관리 홈페이지</title>

<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css?20150801">
<link rel="stylesheet" type="text/css" href="/css/fullcalendar.css?20150801">
<link rel="stylesheet" type="text/css" href="/css/fullcalendar.print.css?20150801" media='print'>
<link rel="stylesheet" type="text/css" href="/css/default.css?20150801">
<link rel="stylesheet" type="text/css" href="/css/jquery-ui-timepicker-addon.css?20150801">
<link rel="stylesheet" type="text/css" href="/daumeditor/css/editor.css?20150801" charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="/css/jquery.treetable.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery.treetable.theme.default.css" />
<link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    
<script type="text/javascript" src="/js/jquery-1.11.2.js?20150801"></script>
<script type="text/javascript" src="/js/jquery-ui.js?20150801"></script>
<script type="text/javascript" src="/js/moment.min.js?20150801"></script>
<script type="text/javascript" src="/js/fullcalendar.js?20150801"></script>
<script type="text/javascript" src="/js/fullcalendar-lang-ko.js?20150801"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>
<script type="text/javascript" src="/js/utils.js?20150801"></script>
<script type="text/javascript" src="/daumeditor/js/editor_loader.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/jquery.treetable.js"></script>
<script type="text/javascript" src="/js/jquery.contextmenu-fixed.js"></script>
    
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
    <div id="navigation">
        <ul style="float:left;">
            <li><a href="/notice">공지사항</a></li>
            <li><a href="/files/security_control" >보안관제/운영</a></li>
            <li><a href="/files/security_diagnosis">보안진단</a></li>
            <li><a href="/files/info_protection_manage">정보보호 관리체계</a></li>
            <li><a href="/files/info_protection_policy">정보보호 정책/지침</a></li>
            <li><a href="/files/info_protection_trend">정보보호 동향</a></li>
            <li><a href="/files/security_news">보안뉴스</a></li>
            <li><a href="/schedule">일정관리</a></li>
            <li><a href="/rssFeedMng">인수인계</a></li>
            <li><a href="/account">계정관리</a></li>
        </ul>
    <% if (userInfo != null) { %>
        <ul id="accoutDiv" style="float: right;">
            <li><a href="#"><%=userInfo.getAcct().getAcctId() %>(<%=userInfo.getAcct().getAcctNm() %>)</a></li>
            <li><a href="/logout" class="end"><span class="logout">로그아웃</span></a></li>
        </ul>  
    <% } %>
    </div>    
