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

            var reservationHtml = "<h4>Reservierung erfolgreich!</h4>";
            $(data.reservationPrices).each(function (index) {
                var reservationPrice = data.reservationPrices[index];
                reservationHtml += "<div class='row'>";
                reservationHtml += "<div class='col-md-2'>Startzeitpunkt</div>";
                reservationHtml += "<div class='col-md-2'>" + reservationPrice.startTime + "</div>";
                reservationHtml += "<div class='col-md-2'>Ankunftszeitpunkt</div>";
                reservationHtml += "<div class='col-md-2'>" + reservationPrice.endTime + "</div>";
                reservationHtml += "<div class='col-md-2'>Preis</div>";
                reservationHtml += "<div class='col-md-2'>" + reservationPrice.price + "</div>";
                reservationHtml += "</div>";
            });
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