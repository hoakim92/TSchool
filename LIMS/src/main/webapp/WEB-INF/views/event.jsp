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
    <form action="${pageContext.request.contextPath}/schedule" method="POST">
        <table class="entityTable">
            <input type="hidden" name="id" value="${event.getId()}">
            <tr>
                <td></td>
                <td><b>THERAPY EVENT</b></td>
            </tr>
            <tr>
                <td></td>
                <td><b><a href="${pageContext.request.contextPath}/prescriptions/${event.getPrescription()}">Prescription</a></b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Date</b></td>
                <td><input required type="text" value="${event.getDate().toString().replace("T", " ")}" readonly/></td>
                <td><b><a href="${pageContext.request.contextPath}/patients/${event.getPatient().getId()}">Patient</a></b></td>
                <td><input required type="text" value="${event.getPatient().getName()}" readonly/>
                </td>
            </tr>
            <tr>
                <td><b>Status</b></td>
                <td><select required id="status-select" name="status" <c:if test="${!event.getStatus().equals('PLANNED')}"> disabled </c:if>>
                    <option value="${event.getStatus()}" selected>${event.getStatus()}</option>
                    <c:if test="${event.getStatus().equals('PLANNED')}">
                        <option value="EXECUTED">EXECUTED</option>
                        <option value="CANCELED">CANCELED</option>
                    </c:if>
                </select></td>
                <td>
                    <b><a href="${pageContext.request.contextPath}/cures/${event.getCure().getId()}">Cure</a></b>
                    </td>
                <td><input type="text" value="${event.getCure().getName()}" readonly/></td>
            </tr>

            <c:if test="${event.getCause() != null}">
            <tr id="cause-tr">
                <td><b>Cause</b></td>
                <td><select disabled class="cause">
                    <option selected>${event.getCause().replace("_"," ")}</option>
                </select></td>
            </tr>
            </c:if>

            <tr hidden id="cause-tr">
                <td><b>Cause</b></td>
                <td><select required id="cause-select" name="cause">
                    <option value="NO_PATIENT">NO PATIENT</option>
                    <option value="NO_CURE">NO CURE</option>
                    <option value="OTHER">OTHER</option>
                </select></td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <input type="submit" id="saveBtn" value="SAVE" hidden>
                    <a href="${pageContext.request.contextPath}/schedule/${event.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script src="<c:url value="/resources/js/event.js"/>"></script>
<script src="<c:url value="/resources/js/common.js"/>"></script>
</BODY>
