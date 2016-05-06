<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Детальная информация о фильме ${film.title}</title>
</head>
<body>
<h1>${film.title}</h1> <span>${film.releaseYear}</span>
<div>Rating: ${film.mpaaRating}</div>
<div>${film.description}</div>

<div>Language: ${film.language.language}</div>

<c:if test="${not empty requestScope.film.categories}">
    <div>
        <c:forEach items="${requestScope.film.categories}" var="category">
            <span>${category.name}</span>
        </c:forEach>
    </div>
</c:if>

<div>
    <a href="film/edit?id=${film.id}">редактировать</a>
</div>

<c:if test="${not empty film.actors}">
    <div>
        <h1>Актеры фильма</h1>
        <c:forEach items="${requestScope.film.actors}" var="actor" varStatus="st">${st.first ? '' : ', '}<a href="actor?id=${actor.id}">${actor.firstName} ${actor.lastName}</a></c:forEach>
    </div>
</c:if>
</body>
</html>
