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
    <form action="${pageContext.request.contextPath}/doctors" method="POST">
        <table class="entityTable">
            <input type="hidden" name="id" value="${doctor.getId()}">
            <tr>
                <td></td>
                <td><b>DOCTOR</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Name</b></td>
                <td><input class="text-input" type="text" name="name" value="${doctor.getName()}" <c:if test="${doctor != null}">readonly</c:if>/></td>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input required type="button" id="editBtn" value="EDIT" <c:if test="${doctor == null}">hidden</c:if>>
                    <input required type="submit" id="saveBtn" value="SAVE" <c:if test="${doctor != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/doctors/${doctor.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/common.js"/>"></script>
</BODY>