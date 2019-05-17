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
    <form action="${pageContext.request.contextPath}/diagnosis" method="POST">
        <table class="entityTable">
            <input type="hidden" name="id" value="${diagnosis.getId()}">
            <tr>
                <td></td>
                <td><b>DIAGNOSIS</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Name</b></td>
                <td><input required class="text-input" type="text" name="name" value="${diagnosis.getName()}" <c:if test="${diagnosis != null}">readonly</c:if>/></td>
                <td><b>Description</b></td>
                <td><input required class="text-input" type="text" name="description" value="${diagnosis.getDescription()}" <c:if test="${diagnosis != null}">readonly</c:if>/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="button" id="editBtn" value="EDIT" <c:if test="${diagnosis == null}">hidden</c:if>>
                    <input type="submit" id="saveBtn" value="SAVE" <c:if test="${diagnosis != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/diagnosis/${diagnosis.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/common.js"/>"></script>
</BODY>