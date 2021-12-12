<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
<h1>Результаты показали эффективность ${ver}</h1>
<a href="./calculate-servlet">Назад</a>
<table border="1">
    <thead >
        <tr>
            <td>Подразделение</td>
            <td>Начало работы</td>
             <td>Своб.график</td>
            <td>Сотрудник</td>
            <td>Должность</td>
            <td>Предпочит.График</td>
            <td>Эффективность в день</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${employees}" var="e">
            <tr>
                <td>${e.employee.department.departmentName}</td>
                <td>${time_start + e.employee.department.scheduleOffset}:00 - ${time_start + e.employee.department.scheduleOffset + time_long}:00</td>
                <td>${e.employee.department.freeSchedule}</td>
                <td>${e.employee.lastName} ${e.employee.firstName}</td>
                <td>${e.employee.title.titleName}</td>
                <td>${time_start + e.employee.employeeSchedule.scheduleOffset}:00 - ${time_start + e.employee.employeeSchedule.scheduleOffset + time_long}:00</td>
                <td>${e.efficiency}%</td>
            </tr>
        </c:forEach>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="6">
                Общая эффективность
            </td>
            <td>${efficiency}%</td>
        </tr>
    </tfoot>
</table>

<p>Результат JSON</p>
${result}
</body>
</html>
