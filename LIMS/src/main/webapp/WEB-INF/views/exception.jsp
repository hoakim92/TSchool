<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>

<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div class="content">
    <H1>ERROR</H1>
    <b>${error}</b>
</div>
</BODY>