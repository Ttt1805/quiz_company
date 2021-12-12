
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
    <head>
        <title>Органицации</title>
    </head>
    <body>
        <h1>Оганизации</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>id</th>
                    <th>Название</th>
                    <th> </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${companies}" var="e">
                    <tr>
                        <td>${e.companyId}</td>
                        <td>${e.name}</td>
                        <td>
                            <a href='<c:url value="/company-edit?id=${e.companyId}"/>'>Edit</a>
                            <a href='<c:url value="/company-remove?id=${e.companyId}"/>'>Remove</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td> </td>
                    <td colspan="2">
                        <a href="./company-edit">Add company</a>
                    </td>
                </tr>
            </tfoot>
        </table>
        <a href="./index.jsp">Основное меню</a>
    </body>
</html>
