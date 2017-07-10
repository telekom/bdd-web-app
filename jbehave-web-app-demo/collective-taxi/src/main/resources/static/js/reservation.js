$(document).ready(function () {

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

            $('#reserve').removeClass("btn-primary btn-warning");
            $('#reserve').addClass("btn-success");

            var reservationHtml = "<h2>Reservierung erfolgreich!</h2>";
            reservationHtml += "<div class='list-group'>";
            $(data.reservationPrices).each(function (index) {
                var reservationPrice = data.reservationPrices[index];
                reservationHtml += " <a href='#' class='list-group-item'><p>Zeitraum: " + reservationPrice.startTime + " - " + reservationPrice.endTime + "<p>Preis: <strong>" + reservationPrice.price + " €</strong></p></a>";
            });
            reservationHtml += "</div>";
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