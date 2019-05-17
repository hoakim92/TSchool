<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div id="content">
    <form action="${pageContext.request.contextPath}/users/createuser" method="GET">
        <input class="createButton" type="submit" id="newBtn" value="CREATE">
    </form>
    <table class="listTable">
        <thead>
        <tr>
            <th>USERNAME</th>
            <th>ROLE</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td><a href="${pageContext.request.contextPath}/users/<c:out value='${u.getId()}'/>"><c:out value="${u.getUsername()}"/></a></td>
                <td><c:out value="${u.getRole().getName().toString()}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</div>
</BODY>
</html>