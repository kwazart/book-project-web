function getBooks() {
    currentMainPage = "books"
    $.get('/api/book').done(function (books) {
        $(`#books`).html('').append(`
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>Название</td>
                        <td>Автор</td>
                        <td>Жанр</td>
                        <td class="d-flex flex-row-reverse">
                            <button onclick="showBook(0)" class="btn btn-outline-dark">Добавить</button>
                        </td>
                    </tr>
                </thead>
                <tbody id="book__rows">
                </tbody>
            </table>
            
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="staticBackdropLabel">Комментарии</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <ul id="modalCommentsById"></ul>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">Выйти</button>
                        </div>
                    </div>
                </div>
            </div>
        `)
        let bookRowsElem = $('#book__rows');

        books.forEach(function (book) {
            $(bookRowsElem).append(`
                    <tr id="book-${book.id}" class="data-row">
                        <th>${book.id}</th>
                        <td>${book.bookName}</td>
                        <td>${book.authorDto.name}</td>
                        <td>${book.genreDto.name}</td>
                        <td class="btn-actions">
                            <button onclick="deleteBook(${book.id})" class="btn btn-outline-dark btn-sm">Удалить</button>
                            <button onclick="showBook(${book.id})" class="btn btn-outline-dark btn-sm">Изменить</button>
                            <button type="button" data-bs-toggle="modal" onclick="showComments(${book.id})" data-bs-target="#staticBackdrop" class="btn btn-outline-dark btn-sm">Комментарии</button>
                        </td>
                    </tr>
                `)
        });
    })
}

function deleteBook(id) {
    $.ajax({
        url: '/api/book/' + id,
        type: 'DELETE',
        success: function () {
            $('#book-' + id).css('display', 'none');
        },
        error: function () {
            alert("Не удалось удалить книгу. Возможно наличие связанных объектов")
        }
    })

}

function editBook(id) {
    let bookSectionElem = $('#edit-book');
    bookSectionElem.css('visibility', 'visible')
    $.get('/api/book/' + (id === 0 ? 1 : id)).done(function (book) {
        if (id === 0) {
            book = {
                "id": 0,
                "bookName": "",
                "authorDto": {
                    "id": 1
                },
                "genreDto": {
                    "id": 1
                }
            }
        }
        $(bookSectionElem).html('').append(`
            <form id="edit-book-form">
                <div class="mb-3">
                    <input type="text" class="form-control" id="edit-book-form_id" value="${book.id}" hidden>
                    
                    <label for="edit-author-form_name" class="form-label">Название</label>
                    <input type="text" class="form-control" id="edit-book-form_name" value="${book.bookName}" placeholder="Название книги">
                    
                    <label for="edit-author-form_author" class="form-label">Автор</label>
                    <select class="form-select" id="editableBookAuthorName" aria-label="Default select example">
                    </select>
                    
                    <label for="edit-author-form_genre" class="form-label">Жанр</label>
                    <select class="form-select" id="editableBookGenreName" aria-label="Default select example">
                    </select>
                </div>
                <button class="btn btn-dark" onclick="sendBook()">Подтвердить</button>
            </form>
        `)
        $.get('/api/author').done(function (authors) {
            authors.forEach(function (author) {
                $("#editableBookAuthorName").append(`
                    <option id="author-option-${author.id}" value="${author.id}">${author.name}</option>
                `)
                if (author.id === book.authorDto.id) {
                    $("#author-option-" + author.id).prop('selected', true);
                } else {
                    $("#author-option-" + author.id).prop('selected', false);
                }
            })
        })
        $.get('/api/genre').done(function (genres) {
            genres.forEach(function (genre) {
                $("#editableBookGenreName").append(`
                    <option id="genre-option-${genre.id}" value="${genre.id}">${genre.name}</option>
                `)
                if (genre.id === book.genreDto.id) {
                    $("genre-option-" + genre.id).prop("selected", true);
                } else {
                    $("#genre-option-" + genre.id).prop('selected', false);
                }
            })
        })
    })
}

function sendBook() {
    let bookId = $("#edit-book-form_id").val();
    let formData = {
        id: bookId,
        bookName: $("#edit-book-form_name").val(),
        authorDto: {"id": $("#editableBookAuthorName option:selected").val(), "name": ""},
        genreDto: {"id": $("#editableBookGenreName option:selected").val(), "name": ""},
    };
    $.ajax({
        type: "PUT",
        url: '/api/book/' + bookId,
        data: JSON.stringify(formData),
        contentType: 'application/json',
        encode: true,
    }).done(function () {
        let bookSectionElem = $('#edit-book');
        bookSectionElem.css('visibility', 'hidden');
        showBooks()
    });
}


function showComments(bookId) {
    $.get('/api/comments/' + bookId).done(function (comments) {
        $('#modalCommentsById').html('')
        comments.forEach(function (comment) {
            $('#modalCommentsById').append(`
                    <tr>
                        <th class="col-3">${comment.id}</th>
                        <td>${comment.text}</td>
                    </tr>
                `)
        });
    })
}

