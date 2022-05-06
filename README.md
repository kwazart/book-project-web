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
    <li>
        <p>Postgres (Docker)</p>
<code>
docker run --name book-project-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=bookdb -d postgres
4db3f102a6e498ed52bdcedb323f4483bf5f90b957f67688ec5f2314ce9c9951
</code>
    </li>
</ul>

