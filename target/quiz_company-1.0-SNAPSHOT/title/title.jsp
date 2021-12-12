<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Должности</title>
</head>
<body>
<h1>Должности</h1>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>Должность</th>
        <th>Свободный график</th>
        <th> </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${titles}" var="t">
        <tr>
            <td>${t.titleId}</td>
            <td>${t.titleName}</td>
            <td>${t.freeSchedule}</td>
            <td>
                <a href='<c:url value="/title-edit?id=${t.titleId}"/>'>Изменить</a>
                <a href='<c:url value="/title-remove?id=${t.titleId}"/>'>Удалить</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="4">
            <a href="./title-edit">Добавить должность</a>
        </td>
    </tr>
    </tfoot>
</table>
<a href="./index.jsp">Основное меню</a>
</body>
</html>

