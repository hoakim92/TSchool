<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div class="content">
    <form action="${pageContext.request.contextPath}/users" method="POST">
        <table class="entityTable">
            <input type="hidden" name="id" value="${user.getId()}">
            <tr>
                <td></td>
                <td><b>USER</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Username</b></td>
                <td><input required class="text-input" type="text" name="username" value="${user.getUsername()}" <c:if test="${user != null}">readonly</c:if>/></td>
                <td><b>Role</b></td>
                <td><select required id="role-select" name="roleId" class=<c:if test="${user != null}">"disabled"</c:if>>
                    <option value="${user.getRole().getId()}" selected>${user.getRole().getName()}</option>
                    <c:forEach items="${roles}" var="role">
                        <c:if test="${!user.getRole().equals(role)}">
                            <option value="${role.getId()}">${role.getName()}</option>
                        </c:if>
                    </c:forEach>
                </select></td>
            </tr>
            <tr <c:if test="${user != null}">hidden</c:if>>
                <td><b>Password</b></td>
                <td><input <c:if test="${user == null}">required</c:if> class="text-input" type="text" name="password"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="button" id="editBtn" value="EDIT" <c:if test="${user == null}">hidden</c:if>>
                    <input type="submit" id="saveBtn" value="SAVE" <c:if test="${user != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/users/${user.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/user.js"/>"></script>
</BODY>
