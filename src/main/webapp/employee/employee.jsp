<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сотрудники</title>
</head>
<body>
<h1>Сотрудники</h1>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>Подразделение</th>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Должность</th>
        <th>Желаемый график</th>
        <th> </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${employees}" var="e">
        <tr>
            <td>${e.employeeId}</td>
            <td>${e.department.departmentName}</td>
            <td>${e.lastName}</td>
            <td>${e.firstName}</td>
            <td>${e.title.titleName}</td>
            <td>${e.employeeSchedule.typeSchedule.description}</td>
            <td>
                <a href='<c:url value="/employee-edit?id=${e.employeeId}"/>'>Изменить</a>
                <a href='<c:url value="/employee-remove?id=${e.employeeId}"/>'>Удалить</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="7">
            <a href="./employee-edit">Добавить сотрудника</a>
        </td>
    </tr>
    </tfoot>
</table>
<a href="./index.jsp">Основное меню</a>

</body>
</html>
