var websocket = null;

function connect() {
    var wsProtocol = window.location.protocol == "https:" ? "wss" : "ws";
    var wsURI = wsProtocol + '://' + window.location.host + window.location.pathname + 'websocket/message';
    websocket = new WebSocket(wsURI);
    websocket.onopen = function () {
    };
    websocket.onmessage = function (event) {
        updateSchedule();
    };
    websocket.onerror = function (event) {
    };
    updateSchedule();
}

function updateSchedule() {
    $("#table tr").remove();
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/LIMS_war_exploded/rest/eventsByDay', false);
    xhr.send();
    if (xhr.status != 200) {
        alert(xhr.status + ': ' + xhr.statusText); // пример вывода: 404: Not Found
    } else {
        obj = JSON.parse(xhr.responseText);
        for (var i = 0; i &lt; obj.length; i++) {
            var $row = $('<tr><td>' + obj[i].patient.name + '</td><td>' + obj[i].date.hour + ':' + obj[i].date.minute + '</td></tr>');
            $('#table').find('tbody').append($row);
        }
    }
}