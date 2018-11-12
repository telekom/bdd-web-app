$(document).ready(function () {
    $("#reserve-form").submit(function (event) {
        event.preventDefault();
        reserve();
    });
});

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
        url: "reservation",
        data: JSON.stringify(reservation),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            $('#reserve').removeClass("btn-primary btn-warning");
            $('#reserve').removeClass("btn-primary btn-success");

            if (data.reservationPrices == null || data.reservationPrices.length == 0) {
                $('#reserve').addClass("btn-warning");
                var reservationHtml = "<h4>The reservation is not possible!</h4>";
                $('#reservation').html(reservationHtml);
            } else {
                $('#reserve').addClass("btn-success");
                var reservationHtml = "<h2>Reservation successful!</h2>";
                reservationHtml += "<table class='table table-striped'>";
                reservationHtml += "<thead><tr><th>Period</th><th>Passengers</th><th>Price</th></tr></thead>";
                reservationHtml += "<tbody>";
                $(data.reservationPrices).each(function (index) {
                    var reservationPrice = data.reservationPrices[index];
                    reservationHtml += "<tr><td>" + reservationPrice.startTime + "h - " + reservationPrice.endTime + "h</td><td>" + reservationPrice.passengers + "</td><td><strong>" + reservationPrice.price + " € </strong></td></tr>";
                });
                reservationHtml += "</tbody>";
                reservationHtml += "</table>";
                $('#reservation').html(reservationHtml);
            }

            console.log("SUCCESS : ", data);
            $("#reserve").prop("disabled", false);
        },
        error: function (e) {

            $('#reserve').removeClass("btn-primary btn-warning");
            $('#reserve').removeClass("btn-primary btn-success");
            $('#reserve').addClass("btn-warning");

            var reservationHtml = "<h4>Sorry, an error has occurred!</h4>";
            $('#reservation').html(reservationHtml);

            console.log("ERROR : ", e);
            $("#reserve").prop("disabled", false);
        }
    });

}