function getAuthors() {
    currentMainPage = "authors"
    $.get('/api/author').done(function (authors) {
        $(`#authors`).html('').append(`
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>Имя</td>
                        <td class="d-flex flex-row-reverse">
                            <button onclick="showAuthor(0)" class="btn btn-outline-dark">Добавить</button>
                        </td>
                    </tr>
                </thead>
                <tbody id="author__rows">
                </tbody>
            </table>
        `)
        let authorRowsElem = $('#author__rows');

        authors.forEach(function (author) {
            $(authorRowsElem).append(`
                    <tr id="author-${author.id}" class="data-row">
                        <th>${author.id}</th>
                        <td>${author.name}</td>
                        <td class="btn-actions">
                            <button onclick="deleteAuthor(${author.id})" class="btn btn-outline-dark btn-sm">Удалить</button>
                            <button onclick="showAuthor(${author.id})" class="btn btn-outline-dark btn-sm">Изменить</button>
                        </td>
                    </tr>
                `)
        });
    })
}

function deleteAuthor(id) {
    $.ajax({
        url: '/api/author/' + id,
        type: 'DELETE',
        success: function () {
            $('#author-' + id).css('display', 'none');
        },
        error: function () {
            alert("Не удалось удалить автора. Возможно наличие связанных объектов")
        }
    })

}

function editAuthor(id) {
    let authorSectionElem = $('#edit-author');
    authorSectionElem.css('visibility', 'visible');
    $.get('/api/author/' + (id === 0 ? 1 : id)).done(function (author) {
        if (id === 0) {
            author = {"id": 0, "name": ""}
        }
        $(authorSectionElem).html('').append(`
            <form id="edit-author-form">
                <div class="mb-3">
                    <label for="edit-author-form_name" class="form-label">Наименование</label>
                    <input type="text" class="form-control" id="edit-author-form_id" value="${author.id}" hidden>
                    <input type="text" class="form-control" id="edit-author-form_name" value="${author.name}" placeholder="Имя автора">
                </div>
                <button class="btn btn-dark" onclick="sendAuthor()">Подтвердить</button>
            </form>
        `)
    })
}

function sendAuthor() {
    let authorId = $("#edit-author-form_id").val();
    let formData = {
        id: authorId,
        name: $("#edit-author-form_name").val()
    };
    console.log(formData);
    $.ajax({
        type: "PUT",
        url: '/api/author/' + authorId,
        data: JSON.stringify(formData),
        contentType: 'application/json',
        encode: true,
    }).done(function () {
        let authorSectionElem = $('#edit-author');
        authorSectionElem.css('visibility', 'hidden');
        showAuthors()
    });

}


