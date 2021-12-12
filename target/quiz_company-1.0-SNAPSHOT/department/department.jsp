<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Подразделения</title>
</head>
<body>
<h1>Подразделения</h1>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>Организация</th>
        <th>Подразделение</th>
        <th>Своб.график</th>
        <th>Синхр.график</th>
        <th>Начало работы</th>
        <th> </th>
    </tr>
    </thead>
    <tbody>
      <c:forEach items="${departments}" var="d">
        <tr>
            <td>${d.departmentId}</td>
            <td>${d.company.name}</td>
            <td>${d.departmentName}</td>
            <td>${d.freeSchedule}</td>
            <td>${d.syncSchedule}</td>
            <td>${time_start + d.scheduleOffset}:00</td>
            <td>
                <a href='<c:url value="/department-edit?id=${d.departmentId}"/>'>Изменить</a>
                <a href='<c:url value="/department-remove?id=${d.departmentId}"/>'>Удалить</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="7">
            <a href="./department-edit">Добавить подразделение</a>
        </td>
    </tr>
    </tfoot>
</table>
<a href="./index.jsp">Основное меню</a>
</body>
</html>
