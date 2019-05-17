var offset = 0;
var count = 30;
var currentCount = 0;
var finish = false;

$('#patients-select').select2({
    allowClear: true,
    multiple: true,
    ajax: {
        url: function (params) {
            return base_url() + 'rest/patients?name=' + params.term;
        },
        dataType: 'json',
        delay: 250,
        processResults: function (data, params) {
            var resData = [];
            data.forEach(function (value) {
                resData.push(value)
            })
            return {
                results: $.map(resData, function (item) {
                    return {
                        text: item.name,
                        id: item.id
                    }
                })
            };
        },
        cache: true
    },
    placeholder: "Select Patient",
    minimumInputLength: 2
});

$('#period').select2({minimumResultsForSearch: -1});

const searchButton = document.getElementById('searchButton');
searchButton.addEventListener('click', search);


addRows($('#period').val());

function search() {
    offset = 0;
    $("#listTable tbody").empty();
    addRows($('#period').val(), $('#patients-select').val());
}

function addRows(period, patients) {
    $.ajax({
        url: base_url() + 'rest/eventsBySchedule?' + 'period=' + period + (patients == null ? '' : ('&patients=' + patients)) + '&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href =' + base_url() + 'schedule/' + val.id + '>' + val.patient.name + '</a></td>' +
                    '<td>' + val.date.year + '/' + addzero(val.date.monthValue) + '/' + addzero(val.date.dayOfMonth) + ' ' + addzero(val.date.hour) + ':' + addzero(val.date.minute) + '</td>' +
                    '<td>' + val.cure.name + '</td>' +
                    '<td>' + val.status + '</td>' +
                    '</tr>');
                $('#listTable').find('tbody').append($row);
                currentCount++;
            });
            offset = offset + count;
        }
    });
}

function addzero(number){
    if (number < 10)
        return '0'+number;
    else
        return number;
}

$(window).scroll(function () {
    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 10) {
        var tmp = currentCount;
        // if (!finish) {
        addRows($('#period').val(), $('#patients-select').val());
        finish = (currentCount == tmp);
        // }
    }
});
