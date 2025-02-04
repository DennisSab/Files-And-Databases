function loadEvents(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Events loaded.");
            document.getElementById("events_table").innerHTML = xhr.responseText;
        }else if(xhr.status !== 200){
            console.log("Cannot load events.");
        }
    };

    var url = 'EventsTable?name=' + encodeURIComponent("customer");
    xhr.open('GET' , url , true);
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}

function loadBookings(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Bookings loaded.");
            document.getElementById("bookings_table").innerHTML = xhr.responseText;
        }else if(xhr.status !== 200){
            console.log("Cannot load bookings.");
        }
    };

    xhr.open('GET' , "BookingsTable");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}

function selectEvent(event_id){
    document.getElementById("book_event").innerHTML = "<hr>\n" +
        "                <h2>Book an Event</h2>\n" +
        "                <form id=\"event-booking-form\">\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <label for=\"ticket-type\">Select Ticket Type</label>\n" +
        "                        <select id=\"ticket-type\" name=\"type\" oninput=\"selectTickets()\">\n" +
        "                            <option value=\"\" disabled selected>Choose ticket type</option>\n" +
        "                            <option value=\"VIP\">VIP</option>\n" +
        "                            <option value=\"General\">General</option>\n" +
        "                        </select>\n" +
        "                    </div>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <label for=\"availability\">Available Tickets</label>\n" +
        "                        <input type=\"text\" id=\"availability\" placeholder=\"Available tickets\" readonly>\n" +
        "                    </div>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <label for=\"number-of-tickets\">Number of Tickets</label>\n" +
        "                        <input type=\"number\" id=\"number-of-tickets\" placeholder=\"Enter number of tickets\" min=\"1\" oninput=\"calculateCost()\" required>\n" +
        "                    </div>\n" +
        "                    <div class=\"input-group\">\n" +
        "                        <label for=\"ticket-cost\">Cost</label>\n" +
        "                        <input type=\"text\" id=\"ticket-cost\" name=\"cost\" placeholder=\"Cost will appear here\" readonly>\n" +
        "                    </div>\n" +
        "                    <button type=\"button\" class=\"btn\" id=\"book-ticket\" onclick=\"booking()\">Book</button>\n" +
        "                </form>";

    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Event selected.");
        }else if(xhr.status !== 200){
            console.log("Cannot select event.");
        }
    };

    xhr.open('POST' , "SelectEvent");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send(event_id);
}

function selectTickets(){
    var ticket_type = document.getElementById("ticket-type").value;
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Tickets selected.");
            getAvailability();
        }else if(xhr.status !== 200){
            console.log("Cannot select tickets.");
        }
    };

    xhr.open('POST' , "SelectTicket");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send(ticket_type);
}

function calculateCost(){
    var amount = document.getElementById("number-of-tickets").value;

    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Cost calculated.");
            document.getElementById("ticket-cost").value = parseInt(xhr.responseText);
        }else if(xhr.status !== 200){
            console.log("Cannot calculate cost.");
        }
    };

    var url = 'Cost?amount=' + encodeURIComponent(amount);
    xhr.open('GET' , url , true);
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}

function selectBooking(booking_id){
    document.getElementById("cancel_booking").innerHTML = "<div class=\"refund-section\">\n" +
        "                <label for=\"refund-amount\">Refund Amount</label>\n" +
        "                <input type=\"text\" id=\"refund-amount\" name=\"refund\" placeholder=\"Refund amount will appear here\" readonly>\n" +
        "            </div>\n" +
        "\n" +
        "            <button type=\"button\" class=\"btn\" id=\"cancel-booking\" onclick=\"cancelBooking()\">Cancel Booking</button>";

    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Booking selected.");
            calculateRefund();
        }else if(xhr.status !== 200){
            console.log("Cannot select Booking.");
        }
    };

    xhr.open('POST' , "SelectBooking");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send(booking_id);
}

function calculateRefund(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Refund calculated.");
            document.getElementById("refund-amount").value = parseInt(xhr.responseText);
        }else if(xhr.status !== 200){
            console.log("Cannot calculate refund.");
            document.getElementById("refund-amount").value = "Refund is not offered";
        }
    };

    xhr.open('GET' , 'Refund');
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}