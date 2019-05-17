var offset = 0;
var count = 30;

addRows(null);

function addRows(name) {
    $.ajax({
        url: base_url()+'rest/diagnosis?' + (name == null ? '' : ('name=' + name)) + '&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href = '+base_url()+'diagnosis/' + val.id + '>' + val.name + '</td>' +
                    '<td>' + (val.description == null ? '' : val.description) + '</td>' +
                    '</tr>');
                $('#listTable').find('tbody').append($row);
            });
            offset = offset + count;
        }
    });
}

$(window).scroll(function () {
    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 10) {
        addRows($('#name-input').text());
    }
});

$('#name-input').on('input', function (e) {
    offset = 0;
    $("#listTable tbody").empty();
    addRows($('#name-input').val())
});