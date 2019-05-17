<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>

<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>"/>
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div class="content">
    <form action="${pageContext.request.contextPath}/cures" method="POST">
        <table class="entityTable">
            <input type="hidden" name="id" value="${cure.getId()}">
            <tr>
                <td></td>
                <td><b>CURE</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Name</b></td>
                <td><input required class="text-input" type="text" name="name" value="${cure.getName()}" <c:if test="${cure != null}">readonly</c:if>/></td>
                <td><b>Type</b></td>
                <td><select required id="type-select" name="type" <c:if test="${cure != null}">disabled</c:if>>
                    <option value="${cure.getType().toString()}" selected>${cure.getType().toString()}</option>
                    <c:forEach items="${types}" var="type">
                        <c:if test="${!cure.getType().toString().equals(type.toString())}">
                            <option value="${type.toString()}">${type.toString()}</option>
                        </c:if>
                    </c:forEach>
                </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="button" id="editBtn" value="EDIT" <c:if test="${cure == null}">hidden</c:if>>
                    <input type="submit" id="saveBtn" value="SAVE" <c:if test="${cure != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/cures/${cure.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script src="<c:url value="/resources/js/cure.js"/>"></script>
<script src="<c:url value="/resources/js/common.js"/>"></script>
</BODY>