<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calculate</title>
</head>
<body>
<h1>Калькуляция эффективности</h1>
<form method="post" action="./calculate-servlet">
    <p><b>Рассчитать эффективность по</b></p>
    <p><input type="radio" name="option1" value="1" checked>Организации
        <select name="company_id">
            <c:forEach items="${companies}" var="c">
              <option
                    value="${c.companyId}">${c.name}</option>
            </c:forEach>
        </select>
        <Br>
        <input type="radio" name="option1" value="2">Подразделению
        <select name="department_id">
            <c:forEach items="${departments}" var="d">
                <option
                        value="${d.departmentId}">${d.departmentName}</option>
            </c:forEach>
        </select>
        <Br>
        <input type="radio" name="option1" value="3">Сотруднику
        <select name="employee_id">
            <c:forEach items="${employees}" var="e">
                <option
                        value="${e.employeeId}">${e.lastName} ${e.firstName}</option>
            </c:forEach>
        </select>
        <Br>
     <p><input type="submit" value="Рассчитать"></p>
</form>
<a href="./index.jsp">Основное меню</a>
</body>
</html>
