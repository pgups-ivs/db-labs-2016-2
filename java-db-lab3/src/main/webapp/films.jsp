<%@ page import="ru.pgups.ivs.rglv.labs.db.model.Film" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список фильмов</title>
</head>
<body>
<h1>Список фильмов</h1>
<table>
    <%
        for (Film film : (List<Film>) request.getAttribute("films")) {
            %>
    <tr>
        <td>
            <%=String.format("<a href='/film?id=%d'>%s</a>", film.getId(), film.getTitle())%>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
