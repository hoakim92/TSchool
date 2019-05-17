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
    <form action="${pageContext.request.contextPath}/prescriptions/createprescription" method="GET">
        <input class="createButton" type="submit" value="CREATE">
    </form>
    <table id="listTable" class="listTable">
        <thead>
        <tr>
            <th><input id="patient-input" class = "search-input" type="text" placeholder="PATIENT"></th>
            <th><input id="cure-input" class = "search-input" type="text" placeholder="CURE"></th>
            <th>DATE OF BEGIN</th>
            <th>DATE OF END</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/prescriptionsList.js"/>"></script>
</BODY>
</html>