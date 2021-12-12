<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Company edit</title>
</head>
<body>
<form method="post" action="./company-edit">
    <label for="company_name">Организация</label>
    <input type="text" hidden="hidden" name="company_id" value="${company.companyId}">
    <input type="text" id="company_name" name="company_name" value="${company.name}">
    <input type="submit" value="Save">
</form>
<a href="./company-servlet">Назад</a>
</body>
</html>
