var offset = 0;
var count = 25;
var currentCount = 0;
var finish = false;

$('#patient-select').select2({
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

$('#cure-select').select2({
    ajax: {
        url: function (params) {
            return base_url() + 'rest/cures?name=' + params.term;
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
    placeholder: "Select Cure",
    minimumInputLength: 2
});
$('#timePattern').select2({minimumResultsForSearch: -1, placeholder: "Select TimePattern"});
$('.inputDaysOfWeek').select2();


var editPatientButton = document.getElementById('editBtn');
var savePatientButton = document.getElementById('saveBtn');
var cancelPatientButton = document.getElementById('cancelBtn');
var selects = document.getElementsByTagName("select");
var inputs = document.getElementsByClassName("text-input");
var dateInputs = document.getElementsByClassName("date-input");
var dates = document.getElementsByName('dates[]');
var timePattern = document.getElementById('timePattern');
var addRowButton = document.getElementById('addRowButton');
var deleteRowButtons = document.getElementsByClassName('deleteRowButton');
editPatientButton.addEventListener('click', updateButton);
cancelPatientButton.addEventListener('click', updateButton);

setOnClickEventByPattern(timePattern.options[timePattern.selectedIndex].value);

function updateButton() {
    var editMode = editPatientButton.hidden;
    editPatientButton.hidden = !editMode;
    savePatientButton.hidden = editMode;
    cancelPatientButton.hidden = editMode;
    addRowButton.hidden = editMode;
    for (var i = 0; i < dateInputs.length; i++) {
        if (editMode) {
            dateInputs[i].setAttribute('readonly', 'true');
        } else {
            dateInputs[i].removeAttribute('readonly');
        }
    }
    for (var i = 0; i < inputs.length; i++) {
        if (editMode) {
            inputs[i].setAttribute('readonly', 'true');
        } else {
            inputs[i].removeAttribute('readonly');
        }
    }
    for (var i = 0; i < dates.length; i++) {
        if (editMode) {
            dates[i].setAttribute('readonly', 'true');
        } else {
            dates[i].removeAttribute('readonly');
        }
    }
    $("#timePattern").prop("disabled", editMode);
    $("#patient-select").prop("disabled", editMode);
    $("#cure-select").prop("disabled", editMode);
    $("#doctors-select").prop("disabled", editMode);
    $("#diagnosis-select").prop("disabled", editMode);
    $("#status-select").prop("disabled", editMode);

    for (var i = 0; i < deleteRowButtons.length; i++) {
        if (editMode) {
            deleteRowButtons[i].setAttribute('hidden', 'true');
        } else {
            deleteRowButtons[i].removeAttribute('hidden');
        }
    }
}


function setOnClickEventByPattern(pattern) {
    if (pattern === 'ONCE') {
        addRowButton.setAttribute('onClick', 'addRowOnce(true)');
    }
    if (pattern === 'DAILY') {
        addRowButton.setAttribute('onClick', 'addRowDaily(true)');
    }
    if (pattern === 'WEEKLY') {
        addRowButton.setAttribute('onClick', 'addRowWeekly(true)');
    }
}

function timePatternChange() {
    var pattern = timePattern.options[timePattern.selectedIndex].value;
    setOnClickEventByPattern(pattern)
    if (pattern === 'ONCE') {
        onceClick();
    }
    if (pattern === 'DAILY') {
        dailyClick();
    }
    if (pattern === 'WEEKLY') {
        weeklyClick();
    }
}

function addRowOnce(addButton) {
    var tableRef = document.getElementById('prescriptionTable').getElementsByTagName('tbody')[0];
    var row = tableRef.insertRow(tableRef.rows.length - 1);
    var col = document.createElement('td');
    var col2 = document.createElement('td');
    row.setAttribute("class", "tronce");
    row.appendChild(col);
    row.appendChild(col2);
    col.innerHTML = "<b>Event date</b>";
    col2.innerHTML = "<input class = 'inputDateOnce' type='datetime-local' name='dates[]' value='' />";
    if (addButton)
        col2.innerHTML = col2.innerHTML + "<button class = 'deleteRowButton' type= 'button' onclick='deleteRow(this)'>-</button>";
}

function addRowDaily(addButton) {
    var tableRef = document.getElementById('prescriptionTable').getElementsByTagName('tbody')[0];
    var row = tableRef.insertRow(tableRef.rows.length - 1);
    var col = document.createElement('td');
    var col2 = document.createElement('td');
    var col3 = document.createElement('td');
    row.setAttribute("class", "trdaily");
    row.appendChild(col);
    row.appendChild(col2);
    row.appendChild(col3);
    col.innerHTML = "<b>Event date</b>";
    col2.innerHTML = "<input type='time' name='dates[]' value='' />";
    if (addButton)
        col2.innerHTML = col2.innerHTML + "<button class = 'deleteRowButton' type= 'button' onclick='deleteRow(this)'>-</button>";
}

function addRowWeekly(addButton) {
    var tableRef = document.getElementById('prescriptionTable').getElementsByTagName('tbody')[0];
    var row = tableRef.insertRow(tableRef.rows.length - 1);
    var col = document.createElement('td');
    var col2 = document.createElement('td');
    var col3 = document.createElement('td');
    row.setAttribute("class", "trweekly");
    row.appendChild(col);
    row.appendChild(col2);
    row.appendChild(col3);
    col.innerHTML = "<b>Event date</b>";
    col2.innerHTML = "<select class='inputDaysOfWeek' name='daysOfWeek[]'>\n" +
        "<option value=1>Monday</option>\n" +
        "<option value=2>Tuesday</option>\n" +
        "<option value=3>Wednesday</option>\n" +
        "<option value=4>Thursday</option>\n" +
        "<option value=5>Friday</option>\n" +
        "<option value=6>Saturday</option>\n" +
        "<option value=7>Sunday</option>\n" +
        "</select><input type='time' name='dates[]' value='' />";
    if (addButton)
        col2.innerHTML = col2.innerHTML + "<button class = 'deleteRowButton' type= 'button' onclick='deleteRow(this)'>-</button>";
    $('.inputDaysOfWeek').select2();
}

function deleteRow(btn) {
    var row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function deleteRows(className) {
    var weekelements = document.getElementsByClassName(className);
    for (var i = weekelements.length - 1; i >= 0; i--) {
        var row = weekelements[i];
        row.parentNode.removeChild(row);
    }
}

function onceClick() {
    deleteRows('trdaily');
    deleteRows('trweekly');
    addRowOnce(false);
}

function dailyClick() {
    deleteRows('trweekly');
    deleteRows('tronce');
    addRowDaily(false);
}

function weeklyClick() {
    deleteRows('trdaily');
    deleteRows('tronce');
    addRowWeekly(false);
}

function addEventsRows() {
    $.ajax({
        url: base_url() + 'rest/events?prescriptionId=' + $('#prescriptionId').val() + '&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href = ' + base_url() + 'schedule/' + val.id + '>' + val.date.year + '/' + val.date.monthValue + '/' + val.date.dayOfMonth + ' ' + val.date.hour + ':' + val.date.minute + '</td>' +
                    '<td>' + val.status + '</td>' +
                    '</tr>');
                $('#eventsTable').find('tbody').append($row);
                currentCount++;
            });
            offset = offset + count;
        }
    });
}

addEventsRows();

$('#table-scroll').scroll(function () {
    if (tableScroll.scrollTop + tableScroll.clientHeight >= tableScroll.scrollHeight - 30) {
        var tmp = currentCount;
        if (!finish)
            addEventsRows();
        finish = (currentCount == tmp);

    }
});

