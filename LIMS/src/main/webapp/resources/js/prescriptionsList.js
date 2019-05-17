var offset = 0;
var count = 30;
var currentCount = 0;
var finish = false;

addRows();

function addRows(patient, cure) {
    $.ajax({
        url: base_url()+'rest/prescriptions?' + (patient == null ? '' : ('patient=' + patient)) + (cure == null ? '' : ('&cure=' + cure)) + '&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href ='+base_url()+'prescriptions/' + val.id + '>' + val.patient.name + '</td>' +
                    '<td>' + val.cure.name + '</td>' +
                    '<td>' + val.dateOfBegin.year + '/' + val.dateOfBegin.monthValue + '/' + val.dateOfBegin.dayOfMonth + '</td>' +
                    '<td>' + val.dateOfEnd.year + '/' + val.dateOfEnd.monthValue + '/' + val.dateOfEnd.dayOfMonth + '</td>' +
                    '</tr>');
                $('#listTable').find('tbody').append($row);
            });
            offset = offset + count;
        }
    });
}

$(window).scroll(function () {
    if ($(window).scrollTop() >= $(document).height() - $(window).height() - 10) {
        addRows($('#patient-input').val(), $('#cure-input').val());
    }
});

$('#patient-input').on('input', function (e) {
    offset = 0;
    $("#listTable tbody").empty();
    addRows($('#patient-input').val(), $('#cure-input').val())
});

$('#cure-input').on('input', function (e) {
    offset = 0;
    $("#listTable tbody").empty();
    addRows($('#patient-input').val(), $('#cure-input').val())
});


function timeConverter(t) {
    var a = new Date(t);
    var today = new Date();
    var yesterday = new Date(Date.now() - 86400000);
    var year = a.getFullYear();
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    return a.getDay().toString() + ' ' + a.getMonth().toString() + ' ' + a.getFullYear().toString();
    if (a.setHours(0, 0, 0, 0) == today.setHours(0, 0, 0, 0))
        return 'today, ' + hour + ':' + min;
    else if (a.setHours(0, 0, 0, 0) == yesterday.setHours(0, 0, 0, 0))
        return 'yesterday, ' + hour + ':' + min;
    else if (year == today.getFullYear())
        return date + ' ' + month + ', ' + hour + ':' + min;
    else
        return date + ' ' + month + ' ' + year + ', ' + hour + ':' + min;
}