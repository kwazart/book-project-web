let sections = [
    "info",
    "genres",
    "authors",
    "books",
    "edit-genre",
    "edit-author",
    "edit-book"]

let currentMainPage = ""

$(function () {
    switch (currentMainPage) {
        case "genres":
            showGenres();
            break;
        case "authors":
            showAuthors();
            break;
        case "books":
            showBooks();
            break;
        case "info":
        default:
            showInfo();
    }

    $.get('/user').done(function (username) {
        $('#username').text(username);
        login();
    })
})

// AUTHORS
function showAuthors() {
    doSectionVisible("authors");
    getAuthors();
}

function showAuthor(id) {
    doSectionVisible("edit-author");
    editAuthor(id);
}

// GENRES
function showGenres() {
    doSectionVisible("genres");
    getGenres();
}

function showGenre(id) {
    doSectionVisible("edit-genre");
    editGenre(id);
}

// BOOKS
function showBooks() {
    doSectionVisible("books");
    getBooks();
}

function showBook(id) {
    doSectionVisible("edit-book");
    editBook(id);
}

// INDEX PAGE
function showInfo() {
    doSectionVisible("info");
}

function doSectionVisible(sectionName) {
    for (let section of sections) {
        if (section !== sectionName) {
            $("#" + section).css('display', 'none')
            $("#btn-" + section).removeClass("active")
        }
    }
    $("#" + sectionName).css('display', 'block')
    $("#btn-" + sectionName).addClass("active");
}

function login() {
    $('#loginBtn').html("Выйти")
}

function logout() {
    $('#loginBtn').html("Войти")
}


