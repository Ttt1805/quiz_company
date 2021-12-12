<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Employee edit</title>
</head>
<body>
<form method="post" action="./employee-edit">
    <input id="employee_id" type="text" hidden="hidden" name="employee_id" value="${employee.employeeId}">
    <label for="last_name">Фамилия</label>
    <input id="last_name" type="text" name="last_name" value="${employee.lastName}">
    <label for="first_name">Имя</label>
    <input id="first_name" type="text" name="first_name" value="${employee.firstName}">
    <br>
    <label for="department_id">Подразделение</label>
    <select id="department_id" name="department_id">
<%--       <option
                disabled>Выберите подразделение</option> --%>
        <c:forEach items="${departments}" var="d">
            <option
                    <c:if test = "${0 == employee.department.departmentId && d.departmentId == 1}">
                        selected
                    </c:if>
                    <c:if test = "${d.departmentId == employee.department.departmentId}">
                        selected
                    </c:if>
                    value="${d.departmentId}">${d.departmentName}</option>
        </c:forEach>
    </select>
    <br>

    <label for="title_id">Должность</label>
    <select id="title_id" name="title_id">
        <c:forEach items="${titles}" var="t">
            <option
                    <c:if test = "${t.titleId == employee.title.titleId}">
                        selected
                    </c:if>
                    value="${t.titleId}">${t.titleName}</option>
        </c:forEach>
    </select>
    <br>

    <c:if test = "${0 < employee.employeeId}">
        <label for="type_schedule">Предпочитаемый график</label>
        <select id="type_schedule" name="type_schedule">
            <c:forEach items="${schedules}" var="sc">
                <option
                        <c:if test = "${sc.index == employee.employeeSchedule.typeSchedule.index}">
                            selected
                        </c:if>
                        value="${sc.index}">${sc.description}</option>
            </c:forEach>
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
        <label for="type_salary">Зарплата</label>
        <select id="type_salary" name="type_salary">
            <c:forEach items="${salaries}" var="sal">
                <option
                        <c:if test = "${sal.index == employee.employeeSalary.salaryType.index}">
                            selected
                        </c:if>
                        value="${sal.index}">${sal.description}</option>
            </c:forEach>
        </select>

        <label for="salary">оклад </label>
        <input id="salary" type="text" name="salary" value="${employee.employeeSalary.salary}">

        <label for="percent">процент </label>
        <input id="percent" type="text" name="percent" value="${employee.employeeSalary.percent}">


        <label for="sdate">дата последнего изменения </label>
        <input id="sdate" type="date" name="sdate" value="${employee.employeeSalary.salary_day}">
    </c:if>

    <input type="submit" value="Save">
</form>
<a href="./employee-servlet">Назад</a>
</body>
</html>

