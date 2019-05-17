<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div id="content">
    <form action="${pageContext.request.contextPath}/cures/createcure" method="GET">
        <input class="createButton" type="submit" id="newBtn" value="CREATE">
    </form>
    <table id="listTable" class="listTable">
        <thead>
        <tr>
            <th><input id="name-input" class = "search-input" type="text" placeholder="NAME"></th>
            <th>TYPE</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/cureList.js"/>"></script>
</BODY>
</html>