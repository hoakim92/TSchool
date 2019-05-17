var offset = 0;
var count = 25;
var currentCount = 0;
var finish = false;

function addRows(name) {
    $.ajax({
        url: base_url() + 'rest/patients?' + (name == null ? '' : ('name=' + name)) + '&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr >' +
                    '<td><a href =' + base_url() + 'patients/' + val.id + '>' + val.name + '</a></td>' +
                    // '<td>' + val.name + '</td>' +
                    '<td>' + val.insurance + '</td>' +
                    '<td>' + (val.doctor == null ? '' : val.doctor.name) + '</td>' +
                    // '<td>' + (val.diagnosis == null ? '' : val.diagnosis.name) + '</td>' +
                    '</tr>');
                $('#listTable').find('tbody').append($row);
            });
            offset = offset + count;
        }
    });
}

addRows(null);

$('tr[data-href]').on("click", function () {
    document.location = $(this).data('href');
});

$(window).scroll(function () {
    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 10) {
        addRows($('#name-input').val());
    }
});

$('#name-input').on('input', function (e) {
    offset = 0;
    $("#listTable tbody").empty();
    addRows($('#name-input').val())
});
