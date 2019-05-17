var offset = 0;
var count = 25;
var currentCount = 0;
var finish = false;

addRows();

function addRows() {
    $.ajax({
        url: base_url() + 'rest/doctors?offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href =' + base_url() + 'doctors/' + val.id + '>' + val.name + '</td></tr>');
                $('#listTable').find('tbody').append($row);
            });
            offset = offset + count;
        }
    });
}

$(window).scroll(function () {
    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 10) {
        var tmp = currentCount;
        if (!finish)
            addRows();
        finish = (currentCount == tmp);
    }
});