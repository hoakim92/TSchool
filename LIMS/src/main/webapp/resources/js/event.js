$('#status-select').select2({minimumResultsForSearch: -1});
$('.cause').select2({minimumResultsForSearch: -1});
$('#status-select').on('select2:select', function (e) {
    $('#saveBtn').attr('hidden', false);
    $('#cancelBtn').attr('hidden', false);
    if (e.params.data.text === 'CANCELED')
    {
        $('#cause-tr').attr('hidden', false);
        $('#cause-select').select2({minimumResultsForSearch: -1});
    } else {
        $('#cause-tr').attr('hidden', true);
    }
});