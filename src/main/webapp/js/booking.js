function booking(){
    if(validate()){
        var ticket_amount = document.getElementById("number-of-tickets").value;
        var cost = document.getElementById("ticket-cost").value;
        var xhr = new XMLHttpRequest();

        xhr.onload = function (){
            if(xhr.readyState === 4 && xhr.status === 200){
                console.log("Booking completed.");
                document.getElementById("book_event").innerHTML = "";
                loadBookings();
                decreaseAvailability(ticket_amount);
                resetEventSession();
                resetTicketSession();
            }else if(xhr.status !== 200){
                console.log("Cannot complete booking.");
            }
        };

        xhr.open('POST' , "Booking");
        xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
        xhr.send(cost + "/" + ticket_amount);
    }else{
        alert("Invalid ticket amount.");
    }
}

function validate(){
    var ticket_amount = parseInt(document.getElementById("number-of-tickets").value);
    var availability = parseInt(document.getElementById("availability").value);

    if(ticket_amount > availability){
        return false;
    }else{
        return true;
    }
}

function cancelBooking(){
    increaseAvailability();
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Booking canceled.");
            document.getElementById("cancel_booking").innerHTML = "";
            loadBookings();
            resetBookingSession();
        }else if(xhr.status !== 200){
            console.log("Cannot canceled booking.");
        }
    };

    xhr.open('DELETE' , "Booking");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send("cancel");
}

function deleteBookings(event_id){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Bookings deleted.");
            deleteTickets(event_id);
        }else if(xhr.status !== 200){
            console.log("Cannot delete bookings.");
        }
    };

    xhr.open('DELETE' , "Booking");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send(event_id);
}

function increaseAvailability(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Availability increased.");
        }else if(xhr.status !== 200){
            console.log("Cannot increase availability.");
        }
    };

    xhr.open('PUT' , "Ticket");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send("return/" + 0);
}

function decreaseAvailability(ticket_amount){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Availability decreased.");
        }else if(xhr.status !== 200){
            console.log("Cannot decrease availability.");
        }
    };

    xhr.open('PUT' , "Ticket");
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send("book/" + ticket_amount);
}

function resetEventSession(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Event session has been reset.");
        }else if(xhr.status !== 200){
            console.log("Cannot reset event session.");
        }
    };

    xhr.open('DELETE' , 'SelectEvent');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send();
}

function resetTicketSession(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Ticket session has been reset.");
        }else if(xhr.status !== 200){
            console.log("Cannot reset ticket session.");
        }
    };

    xhr.open('DELETE' , 'SelectTicket');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send();
}

function resetBookingSession(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Booking session has been reset.");
        }else if(xhr.status !== 200){
            console.log("Cannot reset booking session.");
        }
    };

    xhr.open('DELETE' , 'SelectBooking');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send();
}