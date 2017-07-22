$(document).ready(function () {

    // submit reserve form
    $("#reserve-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        reserve();
    });
});

function isActive() {
    return $.active > 0;
}

function reserve() {

    var reservation = {}
    reservation["date"] = $("#date").val();
    reservation["departure"] = $("#departure").val();
    reservation["earliestStartTime"] = $("#earliestStartTime").val();
    reservation["destination"] = $("#destination").val();
    reservation["latestStartTime"] = $("#latestStartTime").val();

    $("#reserve").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/reservation",
        data: JSON.stringify(reservation),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            $('#reserve').removeClass("btn-primary btn-warning");
            $('#reserve').addClass("btn-success");

            var reservationHtml = "<h2>Reservierung erfolgreich!</h2>";
            reservationHtml += "<table class='table table-striped'>";
            reservationHtml += "<thead><tr><th>Zeitraum</th><th>Mitfahrer</th><th>Preis</th></tr></thead>";
            reservationHtml += "<tbody>";
            $(data.reservationPrices).each(function (index) {
                var reservationPrice = data.reservationPrices[index];
                reservationHtml += "<tr><td>" + reservationPrice.startTime + " - " + reservationPrice.endTime + " Uhr</td><td>" + reservationPrice.passengers + "</td><td><strong>" + reservationPrice.price + " € </strong></td></tr>";
            });
            reservationHtml += "</tbody>";
            reservationHtml += "</table>";
            $('#reservation').html(reservationHtml);

            console.log("SUCCESS : ", data);
            $("#reserve").prop("disabled", false);
        },
        error: function (e) {

            $('#reserve').removeClass("btn-primary btn-success");
            $('#reserve').addClass("btn-warning");

            var reservationHtml = "<h4>Reservierung nicht möglich!</h4>";
            $('#reservation').html(reservationHtml);

            console.log("ERROR : ", e);
            $("#reserve").prop("disabled", false);
        }
    });

}