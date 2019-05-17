<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>

<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/schedule.css"/>"/>
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div id="content">
    <select id="period">
        <option value="All" selected>All</option>
        <option value="ByDay">ByDay</option>
        <option value="ByHour">ByHour</option>
    </select>
    <select id="patients-select">
    </select>
    <input id="searchButton" type="button" value="SEARCH">
        <table id="selectionTable">
            <tbody>

            </tbody>
        </table>
    </form>
    <table id="listTable" class="listTable">
        <thead>
        <tr>
            <th>PATIENT</th>
            <th>DATE</th>
            <th>CURE</th>
            <th>STATUS</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/schedule.js"/>"></script>
</BODY>