$(document).ready(function () {
    $("#reserve-form").submit(function (event) {
        event.preventDefault();
        reserve();
    });
});

$.SSE('api/reservations', {
    onMessage: function (event) {
        updateReservation(JSON.parse(event.data));
    },
    onError: function (e) {
        onReservationError(e);
    }
}).start();

function updateReservation(data) {
    let $reserve = $('#reserve');
    $reserve.removeClass("btn-primary btn-warning");
    $reserve.removeClass("btn-primary btn-success");

    if (data.reservationPrices == null || data.reservationPrices.length === 0) {
        $reserve.addClass("btn-warning");
        let reservationHtml = "<h4>The reservation is not possible!</h4>";
        $('#reservation').html(reservationHtml);
    } else {
        $reserve.addClass("btn-success");
        let reservationHtml = "<h2>Reservation successful!</h2>";
        reservationHtml += "<table class='table table-striped'>";
        reservationHtml += "<thead><tr><th>Period</th><th>Passengers</th><th>Price</th></tr></thead>";
        reservationHtml += "<tbody>";
        $(data.reservationPrices).each(function (index) {
            let reservationPrice = data.reservationPrices[index];
            reservationHtml += "<tr><td>" + reservationPrice.startTime + "h - " + reservationPrice.endTime + "h</td><td>" + reservationPrice.passengers + "</td><td><strong>" + reservationPrice.price + " € </strong></td></tr>";
        });
        reservationHtml += "</tbody>";
        reservationHtml += "</table>";
        $('#reservation').html(reservationHtml);
    }

    console.log("SUCCESS : ", data);
    $("#reserve").prop("disabled", false);
}

function onReservationError(e) {
    let $reserve = $('#reserve');
    $reserve.removeClass("btn-primary btn-warning");
    $reserve.removeClass("btn-primary btn-success");
    $reserve.addClass("btn-warning");

    var reservationHtml = "<h4>Sorry, an error has occurred!</h4>";
    $('#reservation').html(reservationHtml);

    console.log("ERROR : ", e);
    $("#reserve").prop("disabled", false);
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
        url: "api/reservations",
        data: JSON.stringify(reservation),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            updateReservation(data);
        },
        error: function (e) {
            onReservationError(e);
        }
    });

}