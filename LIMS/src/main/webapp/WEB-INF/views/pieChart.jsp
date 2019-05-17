<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/pieChart.scss"/>">
</head>
<BODY>

<%@include file="logOffButton.jspf" %>
<%@include file="navigationMenu.jspf" %>
<div id="horizontal-container">
    <%--<section>--%>
        <%--<h1>COUNT OF PATIENTS FOR DOCTORS</h1>--%>
        <%--<div class="pieID 2 pie 2">--%>

        <%--</div>--%>
        <%--<ul class="pieID 2 legend 2">--%>
        <%--<c:forEach items="${doctorValues}" var="dvalue">--%>
            <%--<li>--%>
                <%--<em>${dvalue.getKey()}</em>--%>
                <%--<span>${dvalue.getValue()}</span>--%>
            <%--</li>--%>
        <%--</c:forEach>--%>
        <%--</ul>--%>

    <%--</section>--%>
    <section>
        <h1>CANCELED EVENTS CAUSE</h1>
        <div class="pieID 4 pie 4">
        </div>
        <ul class="pieID 4 legend 4">
            <c:forEach items="${eventValues}" var="dvalue">
                <li>
                    <em>${dvalue.getKey().replace("_"," ")}</em>
                    <span>${dvalue.getValue()}</span>
                </li>
            </c:forEach>
        </ul>
    </section>
</div>
<script src="<c:url value="/resources/js/pieChart.js"/>"></script>
</BODY>