function getGenres() {
    currentMainPage = "genres"
    $.get('/api/genre').done(function (genres) {
        $(`#genres`).html('').append(`
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>ID</td>
                        <td>Наименование</td>
                        <td class="d-flex flex-row-reverse">
                            <button onclick="showGenre(0)" class="btn btn-outline-dark">Добавить</button>
                        </td>
                    </tr>
                </thead>
                <tbody id="genre__rows">
                </tbody>
            </table>
        `)
        let genreRowsElem = $('#genre__rows');

        genres.forEach(function (genre) {
            $(genreRowsElem).append(`
                    <tr id="genre-${genre.id}" class="data-row">
                        <th>${genre.id}</th>
                        <td>${genre.name}</td>
                        <td class="btn-actions">
                            <button onclick="deleteGenre(${genre.id})" class="btn btn-outline-dark btn-sm">Удалить</button>
                            <button onclick="showGenre(${genre.id})" class="btn btn-outline-dark btn-sm">Изменить</button>
                        </td>
                    </tr>
                `)
        });
    })
}

function deleteGenre(id) {
    $.ajax({
        url: '/api/genre/' + id,
        type: 'DELETE',
        success: function () {
            $('#genre-' + id).css('display', 'none');
        },
        error: function () {
            alert("Не удалось удалить жанр. Возможно наличие связанных объектов")
        }
    })
    $('#genre-' + id).css('display', 'none');
}

function editGenre(id) {
    console.log(id);
    let genreSectionElem = $('#edit-genre');
    genreSectionElem.css('visibility', 'visible')
    $.get('/api/genre/' + (id === 0 ? 1 : id)).done(function (genre) {
        if (id === 0) {
            genre = {"id": 0, "name": ""}
        }
        $(genreSectionElem).html('').append(`
            <form id="edit-genre-form">
                <div class="mb-3">
                    <label for="edit-genre-form_name" class="form-label">Наименование</label>
                    <input type="text" class="form-control" id="edit-genre-form_id" value="${genre.id}" hidden>
                    <input type="text" class="form-control" id="edit-genre-form_name" value="${genre.name}" placeholder="Название жанра">
                </div>
                <button class="btn btn-dark" onclick="sendGenre()">Подтвердить</button>
            </form>
        `)
    })
}

function sendGenre() {
    console.log("GENRE SENDING")
    let genreId = $("#edit-genre-form_id").val();
    let formData = {
        id: genreId,
        name: $("#edit-genre-form_name").val()
    };
    console.log(formData);
    $.ajax({
        type: "PUT",
        url: '/api/genre/' + genreId,
        data: JSON.stringify(formData),
        contentType: 'application/json',
        encode: true,
    }).done(function () {
        console.log("Success genre updating")
        let genreSectionElem = $('#edit-genre');
        genreSectionElem.css('visibility', 'hidden');
        showGenres()
    });

}


