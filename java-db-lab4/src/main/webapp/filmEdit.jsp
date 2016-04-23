<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <input type="submit" value="Сохранить">
</form>
</body>
</html>
