<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title edit</title>
</head>
<body>
<form method="post" action="./title-edit">
    <input type="text" hidden="hidden" name="title_id" value="${title.titleId}">
    <label for="title_name">Должность</label>
    <input type="text" id="title_name" name="title_name" value="${title.titleName}">
     <input type="checkbox" id="free_schedule" name="free_schedule"
            <c:if test="${title.freeSchedule == true}">
                checked
            </c:if>
            value="free_schedule">Свободный график
    <input type="submit" value="Save">
</form>
<a href="./title-servlet">Назад</a>
</body>
</html>
