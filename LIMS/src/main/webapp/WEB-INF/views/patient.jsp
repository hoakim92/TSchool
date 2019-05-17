<%@ page language="java" contentType="text/html;" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/patient.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
</head>
<BODY>
<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div class="content">
    <form action="${pageContext.request.contextPath}/patients" method="POST">
        <table class="entityTable" id="entityTable">
            <tbody>
            <input type="hidden" id="patientId" name="id" value="${patient.getId()}">
            <tr>
                <td></td>
                <td><b>PATIENT</b></td>
            </tr>
            <tr>
            </tr>
            <tr>
                <td><b>Name</b></td>
                <td><input required class="text-input" type="text" name="name" value="${patient.getName()}"
                           <c:if test="${patient != null}">readonly</c:if>/></td>
                <td><b>Insurance</b></td>
                <td><input required class="text-input" type="text" name="insurance" value="${patient.getInsurance()}"
                           <c:if test="${patient != null}">readonly</c:if>/>
                </td>
            </tr>
            <tr>
                <td><b>Date of birth</b></td>
                <td><input required class="date-input" type="date" name="dateOfBirth"
                           value="${patient.getDateOfBirth()}"
                           <c:if test="${patient != null}">readonly</c:if>/>
                </td>
                <td><b>Gender</b></td>
                <td><select required id="gender-select" name="gender" <c:if test="${patient != null}">disabled</c:if>/>>
                    <option value="" ${patient.getGender() == '' ? 'selected' : ''}></option>
                    <option value="MALE" ${patient.getGender() == 'MALE' ? 'selected' : ''}>MALE
                    </option>
                    <option value="FEMALE" ${patient.getGender() == 'FEMALE' ? 'selected' : ''}>
                        FEMALE
                    </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><b>Email</b></td>
                <td><input required class="text-input" type="text" name="email" value="${patient.getEmail()}"
                           <c:if test="${patient != null}">readonly</c:if>/></td>
            </tr>
            <tr>

                <td><b>
                    <c:if test="${patient != null}">
                    <c:if test="${patient.getDoctor() != null}">
                    <a href="${pageContext.request.contextPath}/doctors/${patient.getDoctor().getId()}">

                        </c:if>
                        <c:if test="${patient.getDoctor() == null}">
                        <a href="${pageContext.request.contextPath}/doctors/">

                            </c:if>
                            Doctor</a>
                        </c:if>
                        <c:if test="${patient == null}">
                            Doctor
                        </c:if>
                </b></td>
                <td><select required id="doctors-select" name="doctorId"
                            <c:if test="${patient != null}">disabled</c:if>>>
                    <option value="${patient.getDoctor().getId()}" selected>${patient.getDoctor().getName()}</option>
                </select></td>
                <td><b>Status</b></td>
                <td>
                    <select required id="status-select" name="status" <c:if test="${patient != null}">disabled</c:if>/>>
                    <option value="" ${patient.getStatus() == '' ? 'selected' : ''}></option>
                    <option value="TREAT" ${patient.getStatus() == 'TREAT' ? 'selected' : ''}>TREAT
                    </option>
                    <option value="DISCHARGED" ${patient.getStatus() == 'DISCHARGED' ? 'selected' : ''}>
                        CHARGED
                    </option>
                    </select>
                </td>
            </tr>

            <c:if test="${patient == null}">
                <tr>
                    <td>
                        <b>Diagnosis</b>
                    </td>
                    <td>
                        <select required id="firstDiagnosisSelect" class="diagnosis-select" name="diagnosisIds[]"
                                <c:if test="${patient != null}">disabled</c:if>>
                            <option disabled selected value>DIAGNOSIS</option>
                        </select>
                        <button class='addRowButton' type='button' onclick='addRowDiagnosis(this)'
                                <c:if test="${patient != null}">hidden</c:if>>+
                        </button>
                    </td>
                </tr>
            </c:if>

            <c:if test="${patient != null}">
                <c:if test="${patient.getDiagnosisList() == null}">
                    <tr>
                        <td>
                            <b>Diagnosis</b>
                        </td>
                        <td>
                            <select required id="firstDiagnosisSelect" class="diagnosis-select" name="diagnosisIds[]"
                                    <c:if test="${patient != null}">disabled</c:if>>
                                <option disabled selected value>DIAGNOSIS</option>
                            </select>
                            <button class='addRowButton' type='button' onclick='addRowDiagnosis(this)'
                                    <c:if test="${patient != null}">hidden</c:if>>+
                            </button>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${patient.getDiagnosisList() != null}">
                    <c:if test="${patient.getDiagnosisList().size() == 0}">
                        <tr>
                            <td>
                                <b>Diagnosis</b>
                            </td>
                            <td>
                                <select required id="firstDiagnosisSelect" class="diagnosis-select" name="diagnosisIds[]"
                                        <c:if test="${patient != null}">disabled</c:if>>
                                    <option disabled selected value>DIAGNOSIS</option>
                                </select>
                                <button class='addRowButton' type='button' onclick='addRowDiagnosis(this)'
                                        <c:if test="${patient != null}">hidden</c:if>>+
                                </button>
                            </td>
                        </tr>
                    </c:if>
                </c:if>
            </c:if>

            <c:if test="${patient != null}">
                <c:if test="${patient.getDiagnosisList().size() > 0}">
                    <tr>
                        <td>
                            <b>Diagnosis</b>
                        </td>
                        <td>
                            <select required id="firstDiagnosisSelect" class="diagnosis-select" name="diagnosisIds[]"
                                    <c:if test="${patient != null}">disabled</c:if>>
                                <option value="${patient.getDiagnosisList().get(0).getId()}"
                                        selected>${patient.getDiagnosisList().get(0).getName()}</option>
                            </select>
                            <button class='addRowButton' type='button' onclick='addRowDiagnosis(this)'
                                    <c:if test="${patient != null}">hidden</c:if>>+
                            </button>
                        </td>
                    </tr>

                    <c:if test="${patient.getDiagnosisList().size() > 1}">
                        <c:forEach items="${patient.getDiagnosisList().subList(1, patient.getDiagnosisList().size())}"
                                   var="patientDiagn">
                            <tr>
                                <td>
                                    <b>Diagnosis</b>
                                </td>
                                <td>
                                    <select required class="diagnosis-select" name="diagnosisIds[]" disabled>
                                        <option value="${patientDiagn.getId()}"
                                                selected>${patientDiagn.getName()}</option>
                                        <c:forEach items="${diagnosis}" var="diagn">
                                            <c:if test="${patientDiagn.getId() != diagn.getId()}">
                                                <option value="${diagn.getId()}">${diagn.getName()}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <button class='deleteRowButton' type='button' onclick='deleteRowDiagnosis(this)' hidden
                                            <c:if test="${patient != null}">hidden</c:if>>-
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:if>
            </c:if>

            <tr>
                <td></td>
                <td><input type="button" id="editBtn" value="EDIT" <c:if test="${patient == null}">hidden</c:if>>
                    <input type="submit" id="saveBtn" value="SAVE" <c:if test="${patient != null}">hidden</c:if>>
                    <a href="${pageContext.request.contextPath}/patients/${patient.getId()}"><input type="button" id="cancelBtn" value="CANCEL" hidden></a>
                    <a href="${pageContext.request.contextPath}/prescriptions/createprescription?patientId=${patient.getId()}"><input type="button" id="createPrescriptionBtn" value="CREATE PRESCRIPTION" <c:if test="${patient == null}">hidden</c:if>></a>

                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <div id="table-wrapper" <c:if test="${patient == null}">hidden</c:if>>
        <h4>PRESCRIPTIONS</h4>
        <div id="table-scroll">
            <table class="innerListTable" id="prescriptionTable">
                <thead>
                <tr>
                    <th>CURE</th>
                    <th>DATE OF BEGIN</th>
                    <th>DATE OF END</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/common.js"/>"></script>
<script src="<c:url value="/resources/js/patient.js"/>"></script>
</BODY>
