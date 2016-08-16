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

## Создание объектной модели базы данных
...

## Использование технологии JDBC для взаимодействия с СУБД
...

## Использование технологии Servlet для обеспечения доступа к данным через Web
...

## Использование JSP и JSTL для создания Web-интерфейсов
...
