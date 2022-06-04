<h1>Book project (Web)</h1>
<h3>Описание</h3>
<p>Проект имитирующий учет книг. Поддержка CRUD на всех сущностях.</p>
<p>Основные сущности (текущий вариант): </p>
<ol>
    <li><b>Книга</b></li>
    <ul>
        <li>id</li>
        <li>Наименование</li>
        <li>Автор</li>
        <li>Жанр</li>
    </ul>
    <li><b>Автор</b></li>
    <ul>
        <li>id</li>
        <li>Имя</li>
    </ul>
    <li><b>Жанр</b></li>
    <ul>
        <li>id</li>
        <li>Название</li>
    </ul>
    <li><b>Комментарий</b></li>
    <ul>
        <li>id</li>
        <li>Текст</li>
        <li>Книга</li>
    </ul>
</ol>

<h3>Стек</h3>
<ul>
    <li>Java 11</li>
    <li>Spring Boot 2.6.6</li>
    <li>Spring MVC</li>
    <li>Spring Data</li>
    <li>Spring Security</li>
    <li>Postgres</li>
    <li>Docker</li>
</ul>

<h3>Для запуска приложения неообходимо:</h3>
<ol>
    <li>Поднять бд: <code>docker run --name book-project-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=bookdb -d postgres</code></li>
    <li>Запустить приложение: <code>./mvnw spring-boot:run</code></li>
</ol>

<h3>Существующие пользователи:</h3>
<ol>
    <li><code>USER</code>
        <ul>
            <li>login: user@mail.com</li>
            <li>password: user</li>
            <li>Доступно: просмотр главной страницы, страницы логина и списка книг c комментариями</li>
            <li>Статус: BANNED</li>
        </ul>
    </li>
    <li>
        <code>CLIENT</code>
        <ul>
            <li>login: client@mail.com</li>
            <li>password: client</li>
            <li>Доступно: То же, что и <code>user</code> + просмотр жанров и авторов</li>
            <li>Статус: ACTIVE</li>
        </ul>
    </li>
    <li>
        <code>ADMIN</code>
        <ul>
            <li>login: admint@mail.com</li>
            <li>password: admin</li>
            <li>Доступно: То же, что и <code>client</code> + CRUD всех сущностей</li>
            <li>Статус: ACTIVE</li>
        </ul>
    </li>
</ol>

