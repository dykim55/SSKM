<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "com.cyberone.sskm.utils.StringUtil"
%>

<% 
    String sMessage = (String)request.getAttribute("message");
    String sCode = StringUtil.nullToStr((String)request.getAttribute("code"));
%>

<script>
alert('<%=sMessage%>');
location.href = "/";
</script>