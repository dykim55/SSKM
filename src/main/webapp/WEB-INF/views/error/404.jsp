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
    
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.css?20150528">
    <link rel="stylesheet" type="text/css" href="/css/content.css?20150528">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css?20150528">
    
    <script type="text/javascript" src="/js/jquery-1.11.2.js?20150528"></script>
    <script type="text/javascript" src="/js/jquery-ui.js?20150528"></script>
    <script type="text/javascript" src="/js/util.js?20150528"></script>
</head>    
<body>
    <section>
        <div class="error-box" style="margin: 150px auto 0 auto;">
            <div class="error" style="padding: 20px;">
            
                <div style="margin:0px auto 0 auto;width:500px;border: 2px solid #FD8663;background:#fbfbfb;border-radius:5px;text-align:center;padding: 8px 30px;">
                    <table style="width:100%">
                        <tr>
                            <td>
                                <img src="/img/err_icon.jpg" width="120">
                            </td>
                        </tr>    
                        <tr>
                            <td style="height: 100px;background: rgb(255, 255, 255);border-radius: 5px;color: rgb(69, 68, 66);">
                                <span style="font-size: 26px;color: red;font-weight: bold;">이 페이지를 표시할 수 없습니다.</span>
                                <p></p>
                                <span style="display:inline-block;margin:20px;font-size:14px;">
                                            존재하지 않는 페이지거나 오류로 인하여 페이지를 볼 수 없습니다.<br>
                                            잠시 후 다시 시도해 주십시요<br>
                                            같은 문제가 지속되면 관리자에게 문의하세요.(070-7017-1950)
                                </span>                            
                            </td>
                        </tr>    
                        <tr>
                            <td>
                                <a href="javascript:window.history.back('-1')" class="btn btn-default btn-sm" style="text-decoration:none;"><i class="fa fa-undo" style="font-size:14px;color:#666;margin:2px;"></i> 이전 페이지</a>
                            </td>
                        </tr>
                    </table>    
                </div>
            </div>
        </div>

  </section>
</body>
</html>
  