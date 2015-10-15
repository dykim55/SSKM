<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "com.cyberone.sskm.utils.StringUtil"
%>

<% 
String sMessage = (String)request.getAttribute("message");
String sCode = StringUtil.nullToStr((String)request.getAttribute("code"));
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>대법원 지식관리 홈페이지</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="utf-8">
<link rel="stylesheet" href="/css/font.css">
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/section.css">
<script language="javascript" type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script language="javascript" type="text/javascript" src="/js/jquery/jquery-ui-1.10.2.custom.js"></script>
<script language="javascript" type="text/javascript" src="/js/jquery/jquery.library.js"></script>

<script>

$(document).ready(function(){
    
	$("#loginPw").keydown(function(e) {
        if (e.keyCode==13) {
            $("#login_btn").click();
            return false;
        }
    });
  	
    $("#login_btn").button().click(function(e) {
    if ($("#loginId").val().length == 0 || $("#loginPw").val().length == 0) return;
        document.loginFrm.submit();
        $(".post").remove();
        $(".insert").children().remove();
        $(".insert").append("<p style=\"padding: 20px;\"><span style=\"font-size: 12px;color: rgb(0, 0, 0);\">접속중입니다...</span></p>");
    });

    $("#idSaveCheck").change(function() {
        if ($("#idSaveCheck").is(":checked")) {
            var userId = $("input[name='loginId']").val();
            setCookie("userId", userId, 7);
        } else {
            deleteCookie("userId");
        }
    });
    $("input[name='loginId']").keyup(function() {
        if($("#idSaveCheck").is(":checked")) {
            var userId = $("input[name='loginId']").val();
            setCookie("userId", userId, 7);
        }
    });
    
    var userId = getCookie("userId");
    $("input[name='loginId']").val(userId);
    
    if ($("input[name='loginId']").val() != "") {
    	$("input[name='loginId']").css({background:"url(/images/login/input_id_on.png) no-repeat",color:"white"});
    	$("input[name='loginPw']").focus();
        $("#idSaveCheck").attr("checked", true);
    }
    
});

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
</script>

</head>
<body>
	<div class="align-box" onselectstart="return false" ondragstart="return false">
		<div class="align-cell">
			<div class="login-box">
				<div class="ci"><img src="/images/common/ci.png"></div>
				<form action="/verify" name="loginFrm" method="post">
				<div class="insert">
					<p><input id="loginId" name="loginId" type="text"></p>
					<p><input id="loginPw" name="loginPw" type="password"></p>
				</div>
				</form>
				<div class="post">
					<button id="login_btn" type="button">로그인</button>
				</div>
				<div class="post">
					<input type="checkbox" id="idSaveCheck"><label for="idSaveCheck" style="font-size: 12px;">아이디 저장</label>
				</div>
			<% if (!StringUtil.isEmpty(sMessage)) { %>
				<div class="msg"><%=StringUtil.convertString(sMessage) %></div>
			<% } %>
			</div>
		</div>
	</div>
	
</body>
</html>