$(document).ready(function () {

    $("#reserve-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        reserve();
    });

});

function reserve() {

    var reservation = {}
    reservation["departure"] = $("#departure").val();
    reservation["startTime"] = $("#startTime").val();
    reservation["destination"] = $("#destination").val();
    reservation["endTime"] = $("#endTime").val();

    $("#reserve").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/reservation",
        data: JSON.stringify(reservation),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#reservation').html(json);

            console.log("SUCCESS : ", data);
            $("#reserve").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#reservation').html(json);

            console.log("ERROR : ", e);
            $("#reserve").prop("disabled", false);

        }
    });

}