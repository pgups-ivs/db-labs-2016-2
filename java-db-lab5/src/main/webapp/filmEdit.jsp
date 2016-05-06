<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Редактор фильма ${film.title}</title>
</head>
<body>
<h1>Редактирование информации о фильме "${film.title}"</h1>
<form method="post" action="/film">
    <input type="hidden" name="id" value="${film.id}"/>
    <div>
        Название: <input type="text" name="title" value="${film.title}"/>
    </div>
    <div>
        Год выпуска: <input type="number" name="release_year" value="${film.releaseYear}"/>
    </div>
    <div>
        Описание: <textarea name="description" style="height:6em;width: 250px;">${film.description}</textarea>
    </div>

    <div>
        Язык:
        <select name="languageId">
            <c:forEach items="${requestScope.languages}" var="lang">
                <option value="${lang.id}" ${film.language.id == lang.id ? 'selected' : ''}>
                    ${lang.language}
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        Категории:
        <div style="margin-left: 32px;">
            <c:forEach items="${film.categories}" var="cat">
                <span>${cat.name}</span> <a href="/filmCategory?filmId=${film.id}&categoryId=${cat.id}" title="Убрать категорию" style="color:red;font-weight: bold;font-size:8px;">X</a> <br/>
            </c:forEach>
        </div>
        Добавить категорию:
        <select name="categoryId">
            <option value="">- - - - -</option>
            <c:forEach items="${requestScope.categories}" var="cat">
                <option value="${cat.id}">${cat.name}</option>
            </c:forEach>
        </select>
    </div>

    <input type="submit" value="Сохранить">
</form>
</body>
</html>
