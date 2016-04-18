<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
<head>
    <title>Актер ${actor.firstName} ${actor.lastName}</title>
</head>
<body>
<h1>Актер ${actor.firstName} ${actor.lastName}</h1>
<c:if test="${not empty films}">
    <h3>Список фильмов</h3>
    <div>
        <c:forEach items="${requestScope.films}" var="film">
            <a href="film?id=${film.id}">${film.title} (${film.releaseYear})</a><br/>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
