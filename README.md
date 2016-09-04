# Лабораторные работы и курсовой проект по курсу "Базы данных", часть 2

В осеннем семестре изучаются способы создания систем, использующих базы данных. В курсовом проекте разрабатывается web-интерфейс к базе данных, в качестве базовой технологии разработки используются технологии Java Servlet и JSP.

В этом репозитории выкладываются примеры лабораторных работ, которые являются также и этапами создания курсового проекта:

Папка | описание
---- | ----
[java-db-lab1](java-db-lab1) | Простейший maven-проект, демонстрирующий использование jdbc для выполнения запроса к СУБД
[java-db-lab2](java-db-lab2) | Объектная модель, пример DAO-классов
[java-db-lab3](java-db-lab3) | Сервлеты, отображающие данные из БД
[java-db-lab4](java-db-lab4) | Отображение данных с помощью JSP
[java-db-lab5](java-db-lab5) | Редактирование данных


## Подготовка к выполнению работ
Работы выполняются с использованием СУБД PostgreSQL и web-сервера Tomcat. Для разработки возможно использовать любую среду разработки, поддерживающую технологии Java EE. В компьютерных классах кафедры установлена среда [Netbeans](https://netbeans.org/), можно использовать [Eclipse](https://eclipse.org/) или [IntelliJ IDEA](http://www.jetbrains.com/idea/) в версии Ultimate. Также можно использовать любой текстовый редактор, выполняя сборку проекта в [Maven](https://maven.apache.org/).

Если вы еще не работали с Git, то нужно ознакомиться с основами работы с этим инструментом: на сайте [Git-SCM.com](https://git-scm.com/) опубликован русский перевод книги [Pro Git](https://git-scm.com/book/ru/v2), для начала достаточно будет добраться до главы [Клонирование репозитория](https://git-scm.com/book/ru/v2/%D0%9E%D1%81%D0%BD%D0%BE%D0%B2%D1%8B-Git-%D0%A1%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5-Git-%D1%80%D0%B5%D0%BF%D0%BE%D0%B7%D0%B8%D1%82%D0%BE%D1%80%D0%B8%D1%8F#Клонирование-существующего-репозитория). Адрес для клонирования репозитория указан вверху стартовой страницы репозитория: https://github.com/pgups-ivs/db-labs-2016-2.git

Также можно скачать архив текущего состояния репозитория: https://github.com/pgups-ivs/db-labs-2016-2/archive/master.zip

Для сборки примеров работ необходимо установить [JDK SE8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) и  [Maven](https://maven.apache.org/). Во время сборки maven будет скачивать необходимые jar-библиотеки из центрального репозитория. 

Если вы планируете работу в offline режиме или собирать проект без maven, то в classpath понадобится добавлять несколько библиотек:

  * [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download/postgresql-9.4.1209.jar)
  * Servlet и JSP API: [servlet-api.jar](http://central.maven.org/maven2/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar), [jsp-api.jar](http://central.maven.org/maven2/javax/servlet/jsp/jsp-api/2.2/jsp-api-2.2.jar), [jstl.jar](http://central.maven.org/maven2/jstl/jstl/1.2/jstl-1.2.jar), [standard.jar](http://central.maven.org/maven2/taglibs/standard/1.1.2/standard-1.1.2.jar)
  * JUnit 4.12: [junit.jar](http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar), [hamcrest-core.jar](http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar) (ссылки есть на сайте [junit](https://github.com/junit-team/junit4/wiki/Download-and-Install))

Для запуска примеров web-приложений можно использовать любой Servlet-контейнер, поддерживающий версию 3.0: [Tomcat 8](http://tomcat.apache.org/download-80.cgi) или [Jetty](http://www.eclipse.org/jetty/).

Также, в качестве БД для примеров работ необходимо импортировать образ базы данных с сайта: http://www.postgresqltutorial.com/postgresql-sample-database/

## Использование технологии JDBC для взаимодействия с СУБД
Для выполнения лабораторных работ необходимо ознакомиться с технологией JDBC: [JDBC Overview](https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html), [JDBC Basics](https://docs.oracle.com/javase/tutorial/jdbc/basics/).

Пример в директории [java-db-lab1](java-db-lab1) демонстрирует простейший сценарий работы с СУБД - установление соединения, выполнение запроса и отображение результата. Стандарт JDBC подразумевает, что для этого нужно выполнить следующие действия:
 * регистрация класса драйвера в менеджере драйверов
 * обращение к менеджеру драйверов за соединением
 * создание объекта, исполняющего SQL-выражения
 * исполнение запроса и получение объекта, используемого для доступа к результату запроса
 * обработка результата: последовательный перебор строк, обработка ячеек каждой строки
 * последовательное "закрытие" всех объектов, имеющих состояние: результата запроса, объекта-выражения и соединения с СУБД


В примере показан явный вызов Class.forName(), который было необходимо выполнить при использовании версии JDBC 3.0. Для версии JDBC 4.x выполнять это действие не обязательно, менеджер драйверов самостоятельно сканирует классы в classpath и регистрирует обнаруженные драйверы.

Для получения соединения с СУБД, менеджер драйверов передаёт URL со строкой соединения каждому из драйверов. Если драйвер способен обработать URL, то он установит соединение и вернёт настроенный объект класса Connection.
...

## Создание объектной модели базы данных
Для облегчения работы программы с данными, хранящимися в реляционной БД, нужно разработать набор классов, соответствующих схеме БД. Для каждой таблицы, представляющей сущность, в программе создаётся класс, единственной ответственностью которого является хранение данных. Распространённым обозначением для подобных классов является "POJO" - "Plain Old Java Object" - класс, соответствующий простейшим требованиям:
* имеет конструктор без аргументов
* все поля имеют спецификатор доступа private
* для доступа к данным используются get- и set- методы с названиями, соответствующими спецификации Java Beans.

В примере [java-db-2](java-db-lab2) классы модели можно найти в пакете [model](java-db-lab2/src/main/java/ru/pgups/ivs/rglv/labs/db/model). В качестве примера рассмотрим простейший класс, соответствующий таблице "Актёры":

```java
public class Actor {
    private long id;
    private String firstName;
    private String lastName;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
```

Этот класс не имеет никаких зависимостей от других, поэтому содержит только поля стандартных классов - две строки - фамилия и имя - и целочисленный идентификатор. Для классов, связанных с другими ассоциациями "многие-ко-многим", необходимо добавлять поля с коллекциями соответствующих типов. В случае ассоциации "много-к-одному" используется обычное поле-ссылка. Например, класс "Фильм" хранит списки актёров и жанров и ссылку на объект класса "Язык":

```java
public class Film {
    private long id;

    private String title;
    private String description;
    private int releaseYear;
    private int lenght;
...
    private List<Actor> actors;
    private List<FilmCategory> categories;
    private Language language;
...
    public List<Actor> getActors() { return actors; }
    public void setActors(List<Actor> actors) { this.actors = actors; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    public List<FilmCategory> getCategories() { return categories; }
    public void setCategories(List<FilmCategory> categories) { this.categories = categories; }

}
```

...

## Создание классов доступа к БД в соответствии с шаблоном проектирования DAO (Data Access Object)
[Tutorial - DAO design problems](http://tutorials.jenkov.com/java-persistence/dao-design-problems.html)

...

## Использование технологии Servlet для обеспечения доступа к данным через Web

[Servlets 3.1 tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets.htm)
...

## Использование JSP и JSTL для создания Web-интерфейсов
[JSP tutorial](http://docs.oracle.com/javaee/5/tutorial/doc/bnagx.html)
...
