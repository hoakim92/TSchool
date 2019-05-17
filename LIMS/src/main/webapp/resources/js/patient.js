var offset = 0;
var count = 25;
var currentCount = 0;
var finish = false;

$('#status-select').select2({placeholder: "Select Status"});
$('#gender-select').select2({placeholder: "Select Gender"});
$('.diagnosis-select').select2({placeholder: "Select Diagnosis"});

var editPatientButton = document.getElementById('editBtn');
var createPrescriptionButton = document.getElementById('createPrescriptionBtn');
var savePatientButton = document.getElementById('saveBtn');
var cancelPatientButton = document.getElementById('cancelBtn');
var inputs = document.getElementsByClassName("text-input");
var tableScroll = document.getElementById("table-scroll");
editPatientButton.addEventListener('click', editButton);
cancelPatientButton.addEventListener('click', cancelButton);
updateSelect2Diagnosis($('#firstDiagnosisSelect'));

$('#doctors-select').select2({
    ajax: {
        url: function (params) {
            return base_url()+'rest/doctors?name=' + params.term;
        },
        dataType: 'json',
        delay: 250,
        data: function (params) {
            return {
                q: params.term
            };
        },
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
    placeholder: "Select Doctor",
    minimumInputLength: 2
});

function updateSelect2Diagnosis(elm) {
    elm.select2({
        ajax: {
            url: function (params) {
                return base_url()+'rest/diagnosis?name=' + params.term;
            },
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    q: params.term
                };
            },
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
        placeholder: "Select Diagnosis",
        minimumInputLength: 4
    });
}

function editButton() {
    updateSelect2Diagnosis($('#firstDiagnosisSelect'));
    updateSelect2Diagnosis($('.diagnosis-select'));
    updateButton();
}

function cancelButton() {
    updateButton();
}

function updateButton() {
    var editMode = editPatientButton.hidden;
    editPatientButton.hidden = !editMode;
    createPrescriptionButton.hidden = !editMode;
    savePatientButton.hidden = editMode;
    cancelPatientButton.hidden = editMode;
    for (var i = 0; i < inputs.length; i++) {
        if (editMode) {
            inputs[i].setAttribute('readonly', 'true');
        } else {
            inputs[i].removeAttribute('readonly');
        }
    }

    $("#patient-select").prop("disabled", editMode);
    $(".date-input").prop("readonly", editMode);
    $("#gender-select").prop("disabled", editMode);
    $("#cure-select").prop("disabled", editMode);
    $("#doctors-select").prop("disabled", editMode);
    $(".diagnosis-select").prop("disabled", editMode);
    $("#status-select").prop("disabled", editMode);
    $("#type-select").prop("disabled", editMode);
    $(".addRowButton").prop("hidden", editMode);
    $(".deleteRowButton").prop("hidden", editMode);
}

function addRowDiagnosis() {
    var tableRef = document.getElementById('entityTable').getElementsByTagName('tbody')[0];
    var row = tableRef.insertRow(tableRef.rows.length - 1);
    var col = document.createElement('td');
    var col2 = document.createElement('td');
    row.setAttribute("class", "tronce");
    row.appendChild(col);
    row.appendChild(col2);
    col.innerHTML = "<b>Diagnosis</b>";
    col2.innerHTML = "<select required class=\"diagnosis-select\" name=\"diagnosisIds[]\">\n" +
        "<option disabled selected value> DIAGNOSIS</option>\n" +
        "</select>\n" +
        "<button class='deleteRowButton' type='button' onclick='deleteRowDiagnosis(this)'>-</button>";
    updateSelect2Diagnosis($('.diagnosis-select').last());
}

function deleteRowDiagnosis(btn) {
    var row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

function addPrescriptionRows() {
    $.ajax({
        url: base_url()+'rest/prescriptions?patientId='+ $('#patientId').val()+'&offset=' + offset + '&count=' + count,
        dataType: "json",
        success: function (data, textStatus) {
            $.each(data, function (item, val) {
                var $row = $('<tr>' +
                    '<td><a href ='+ base_url()+'prescriptions/' + val.id + '>' + val.cure.name + '</td>' +
                    '<td>' + val.dateOfBegin.year + '/' + val.dateOfBegin.monthValue + '/' + val.dateOfBegin.dayOfMonth + '</td>' +
                    '<td>' + val.dateOfEnd.year + '/' + val.dateOfEnd.monthValue + '/' + val.dateOfEnd.dayOfMonth + '</td>' +
                    '</tr>');
                $('#prescriptionTable').find('tbody').append($row);
                currentCount++;
            });
            offset = offset + count;
        }
    });
}

addPrescriptionRows();

$('#table-scroll').scroll(function () {
    if (tableScroll.scrollTop + tableScroll.clientHeight >= tableScroll.scrollHeight - 30) {
        var tmp = currentCount;
        if (!finish)
            addPrescriptionRows();
        finish = (currentCount == tmp);

    }
});
