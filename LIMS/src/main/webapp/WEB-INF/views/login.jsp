<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login.css"/>">
</head>
<BODY>
<div class="logo"></div>
<div class="login-block">
    <div class="logo"></div>
    <form action="${pageContext.request.contextPath}/login/process" method="POST">
        <input type="text" name="username" value="" placeholder="Username" id="username"/>
        <input type="password" name="password" value="" placeholder="Password" id="password"/>
        <c:if test="${error != null}">
            <div class="error"><b><c:out value="${error}"/></b></div>
        </c:if>
        <button>Submit</button>
    </form>
</div>
</BODY>
</html>