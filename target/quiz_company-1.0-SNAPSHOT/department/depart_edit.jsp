<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Department edit</title>
</head>
<body>
<form method="post" action="./department-edit">
    <input id="department_id" type="text" hidden="hidden" name="department_id" value="${department.departmentId}">
    <label for="department_name">Подразделение</label>
    <input id="department_name" type="text" name="department_name" value="${department.departmentName}">
    <br>
    <label for="company_id">Организация</label>
     <select id="company_id" name="company_id">
        <c:forEach items="${companies}" var="c">
            <option
                <c:if test = "${c.companyId == department.company.companyId}">
                    selected
                </c:if>
                    value="${c.companyId}">${c.name}</option>
        </c:forEach>
    </select>
    <br>
    <label for="type_schedule">График</label>
    <select id="type_schedule" name="type_schedule">
        <option
                <c:if test = "${1 == schedule}">
                    selected
                </c:if>
                value="1">Свободный график</option>
        <option
                <c:if test = "${2 == schedule}">
                    selected
                </c:if>
                value="2">Стандартный график</option>
        <option
                <c:if test = "${3 == schedule}">
                    selected
                </c:if>
                value="3">Синхронный график</option>
     </select>
    <br>
    <label for="time_start">Начало работы</label>
    <select id="time_start" name="time_start">
        <c:forEach var="t" begin="7" end="13">
            <option
                <c:if test = "${t == time1}">
                        selected
                </c:if>
                    value="${t}">${t}:00</option>>
        </c:forEach>>
    </select>
    <label for="time2">продолжительность </label>
    <input id="time2" type="text" readonly="readonly" name="time2" value="${time2}">
    <br>
    <input type="submit" value="Save">
</form>
<a href="./department-servlet">Назад</a>
</body>
</html>
