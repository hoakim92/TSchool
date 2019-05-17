<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/prescription.css"/>">
</head>
<BODY>

<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div class="content">
    <form action="${pageContext.request.contextPath}/prescriptions" method="POST">
        <table class="entityTable" id="prescriptionTable">
            <tbody>
            <input type="hidden" name="id" id="prescriptionId" value="${prescription.getId()}">
            <tr>
                <td></td>
                <td><b>PRESCRIPTION</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td>
                    <b>
                        <c:if test="${patient != null}">
                            <a href="${pageContext.request.contextPath}/patients/${patient.getId()}">
                        </c:if>
                        <c:if test="${patient == null}">
                            <a href="${pageContext.request.contextPath}/patients/${prescription.getPatient().getId()}">
                        </c:if>
                                Patient</a>
                    </b>
                </td>
                <td>
                    <select required id="patient-select" name="patientId" <c:if test="${prescription != null}">disabled</c:if>/>
                    <c:if test="${patient != null}">
                        <option value="${patient.getId()}" selected>${patient.getName()}</option>
                    </c:if>
                    <c:if test="${patient == null}">
                        <option value="${prescription.getPatient().getId()}" selected>${prescription.getPatient().getName()}</option>
                    </c:if>
                    </select>
                </td>
                <td>
                    <b>
                    <c:if test="${prescription != null}">
                    <c:if test="${prescription.getCure() != null}">
                    <a href="${pageContext.request.contextPath}/cures/${prescription.getCure().getId()}">

                        </c:if>
                        <c:if test="${prescription.getCure() == null}">
                        <a href="${pageContext.request.contextPath}/cures/">

                        </c:if>
                            Cure</a>
                    </c:if>
                    <c:if test="${prescription == null}">
                        Cure
                    </c:if>
                    </b>
                </td>
                <td>
                    <select required id="cure-select" name="cureId"
                            <c:if test="${prescription != null}">disabled</c:if>>
                        <option value="${prescription.getCure().getId()}"
                                selected>${prescription.getCure().getName().toString()}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><b>Date of begin</b></td>
                <td><input required class="date-input" type="date" name="dateOfBegin"
                           value="${prescription.getDateOfBegin()}"
                           <c:if test="${prescription != null}">readonly</c:if>/>
                </td>
                <td><b>Date of end</b></td>
                <td><input required class="date-input" type="date" name="dateOfEnd"
                           value="${prescription.getDateOfEnd()}"
                           <c:if test="${prescription != null}">readonly</c:if>/>
                </td>
            </tr>
            <tr>
            <tr>
                <td><b>TimePattern</b></td>
                <td>
                    <select required id='timePattern' name='periodType'
                            <c:if test="${prescription != null}">disabled</c:if> onchange="timePatternChange()">
                        <option value='ONCE'
                                <c:if test="${prescription.getPeriodType().equals('ONCE')}">selected</c:if>>ONCE
                        </option>
                        <option value='DAILY'
                                <c:if test="${prescription.getPeriodType().equals('DAILY')}">selected</c:if>>DAILY
                        </option>
                        <option value='WEEKLY'
                                <c:if test="${prescription.getPeriodType().equals('WEEKLY')}">selected</c:if>>WEEKLY
                        </option>
                    </select>
                    <button id="addRowButton" class="addRowButton" type="button" <c:if test="${prescription != null}">hidden</c:if>>+
                    </button>
                </td>
                <td><b>Doze</b></td>
                <td><input required type="text" class="text-input" name="doze" value="${prescription.getDoze()}"
                           <c:if test="${prescription != null}">readonly</c:if>/></td>
            </tr>

            <c:if test="${prescription == null}">
                <tr class="tronce">
                    <td><b>Event date</b></td>
                    <td><input required type='datetime-local' name='dates[]'/>
                    </td>
                </tr>
            </c:if>


            <c:if test="${prescription.getPeriodType().equals('ONCE')}">
                <c:forEach items="${prescription.getEvents()}" var="d" varStatus="loop">
                    <tr class="tronce">
                        <td><b>Event date</b></td>
                        <td><input required type='datetime-local' name='dates[]' value="${d.getDate()}" readonly/>
                            <c:if test="${loop.index > 0}">
                                <button class='deleteRowButton' type='button' onclick='deleteRow(this)' hidden>-</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${prescription.getPeriodType().equals('DAILY')}" >
                <c:forEach items="${prescription.getEvents()}" var="d" varStatus="loop">
                    <tr class="trdaily">
                        <td><b>Event date</b></td>
                        <td><input required type='time' name='dates[]' value="${d.getDate()}" readonly/>
                            <c:if test="${loop.index > 0}">
                                <button class='deleteRowButton' type='button' onclick='deleteRow(this)' hidden>-</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>

            <c:if test="${prescription.getPeriodType().equals('WEEKLY')}">
                <c:forEach items="${prescription.getEvents()}" var="event" varStatus="loop">
                    <tr class="trweekly">
                        <td><b>Event date</b></td>
                        <td>
                            <select required class='inputDaysOfWeek' name='daysOfWeek[]'
                                    class=<c:if test="${prescription != null}">"disabled"</c:if>>
                                <option value=1 <c:if test="${event.getDayOfWeek()==1}">selected</c:if>>Monday
                                </option>
                                <option value=2 <c:if test="${event.getDayOfWeek()==2}">selected</c:if>>Tuesday
                                </option>
                                <option value=3 <c:if test="${event.getDayOfWeek()==3}">selected</c:if>>
                                    Wednesday
                                </option>
                                <option value=4 <c:if test="${event.getDayOfWeek()==4}">selected</c:if>>
                                    Thursday
                                </option>
                                <option value=5 <c:if test="${event.getDayOfWeek()==5}">selected</c:if>>Friday
                                </option>
                                <option value=6 <c:if test="${event.getDayOfWeek()==6}">selected</c:if>>
                                    Saturday
                                </option>
                                <option value=7 <c:if test="${event.getDayOfWeek()==7}">selected</c:if>>Sunday
                                </option>
                            </select>
                            <input required type='time' name='dates[]' value="${event.getDate()}" readonly/>
                            <c:if test="${loop.index > 0}">
                                <button class='deleteRowButton' type='button' onclick='deleteRow(this)' hidden>-</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <tr>
                <td></td>
                <td><input type="button" id="editBtn" value="EDIT" <c:if test="${prescription == null}">hidden</c:if>>
                    <input type="submit" id="saveBtn" value="SAVE" <c:if test="${prescription != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/prescriptions/${prescription.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                    <a href="${pageContext.request.contextPath}/prescriptions/cancelPlannedEvents?id=${prescription.getId()}" <c:if test="${prescription.getId() == null}">hidden</c:if>><input type="button" id="cancelPlannedEvents" value="CANCEL EVENTS"></a>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <div id="table-wrapper" <c:if test="${prescription == null}">hidden</c:if>>
        <h4>EVENTS</h4>
        <div id="table-scroll">
            <table class="innerListTable" id="eventsTable">
                <thead>
                <tr>
                    <th>DATE</th>
                    <th>STATUS</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/prescription.js"/>"></script>
</BODY>