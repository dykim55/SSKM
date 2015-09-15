<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "com.cyberone.scourt.utils.StringUtil"
%>
<% 
String sMessage = (String)request.getAttribute("message");
String sCode = StringUtil.nullToStr((String)request.getAttribute("code"));
%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Content-Language" content="utf-8">
    
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.css?20150801">
    
    <script type="text/javascript" src="/js/jquery-1.11.2.js?20150801"></script>
    <script type="text/javascript" src="/js/jquery-ui.js?20150801"></script>
    
    <script>

    $(document).ready(function(){
    	
		$("#login_btn").button().click(function(e) {
		    //if ($("#loginId").val().length == 0 || $("#loginPw").val().length == 0) return;

		    document.loginFrm.submit();
	        $("#login").children().remove();
	        $("#login").append("<p style=\"padding: 20px;\"><span style=\"font-size: 12px;color: rgb(181, 228, 247);\">접속중입니다...</span></p>");
		});

    });
    </script>

</head>    
<body>
<h1><%=StringUtil.convertString(sMessage) %></h1>
<section>
    <article>
        <div>
            <div id="login">
                <form action="/verify_account" name="loginFrm" method="post">
                <p><input type="text" style="width:150px" id="loginId" name="loginId" value="" placeholder="아이디를 입력하세요"></p>
                <p><input type="password" style="width:150px" id="loginPw" name="loginPw" placeholder="비밀번호를 입력하세요"></p>
                </form>
                <p><button id="login_btn">Log In</button></p>
            </div>
        </div>
    </article>
</section>

</body>
</html>
