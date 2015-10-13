<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "com.cyberone.sskm.utils.StringUtil"
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
<style>
	* {
		margin:0; padding:0px;
	}
	html, body {
		width:100%; height:100%;
		min-width:1000px; min-height:650px;
	}
	img {
		vertical-align:middle;
	}
	div.center-fix {
		display:table;
		width:100%; height:100%;
		background:#f6f6f6;
	}
	div.center-fix div.cell-fix {
		display:table-cell;
		width:100%; height:100%;
		text-align:center; vertical-align:middle;
	}
	div.center-fix div.cell-fix div.msg-box {
		display: table;
	    margin: 5px auto;
	    border: 2px solid #C5C5C5;
	    border-radius: 10px;
	    padding: 20px 40px 20px 40px;
	    background-color: #fff;	
	}
	div.center-fix div.cell-fix div.msg-box div.cell {
		display:table-cell;
	}
	p.title {
		margin-top: 10px;
	    margin-bottom: 24px;
	    font-family: "malgun gothic","dotum",sans-serif;
	    font-size: 24px;
	    font-weight: bold;
	    color: #005596;
	    letter-spacing: -2px;
	}
	p.msg {
		font-family:"malgun gothic","dotum",sans-serif; font-size:16px; color:#333; line-height:28px; letter-spacing:-1px;
	}

</style>
</head>
<body>
	<div class="center-fix">
		<div class="cell-fix">
			<img src="/images/common/page_error.png">
			<div class="msg-box">
				<div class="cell">
					<p class="title">이 페이지를 표시할 수 없습니다.</p>
					<p class="msg">존재하지 않는 페이지거나 오류로 인하여 페이지를 볼 수 없습니다.</p>
					<p class="msg">잠시 후 다시 시도해 주십시요.</p>
					<p class="msg">같은 문제가 지속되면 관리자에게 문의하세요.</p>
					<a href="javascript:window.history.back('-1')" class="btn btn-default btn-sm" style="text-decoration:none;"><i class="fa fa-undo" style="font-size:14px;color:#666;margin:2px;"></i> 이전 페이지</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>  