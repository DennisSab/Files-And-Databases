function createTicketJSON(event_name , type , price , availability){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("JSON for tickets created.");
            var event_id = parseInt(xhr.responseText.toString());
            var ticket_JSON = JSON.stringify({event_id: event_id , type: type , price: price , availability: availability});
            createTickets(ticket_JSON);
        }else if(xhr.status !== 200){
            console.log("Error.");
        }
    };

    var url = 'Event?name=' + encodeURIComponent(event_name);
    xhr.open('GET' , url , true);
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}

function getAvailability(){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Availability printed.");
            document.getElementById("availability").value = parseInt(xhr.responseText);
        }else if(xhr.status !== 200){
            console.log("Cannot print availability.");
        }
    };

    xhr.open('GET' , 'SelectTicket');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send();
}

function createTickets(jsonData){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Tickets created.");
            window.location.reload();
        }else if(xhr.status !== 200){
            console.log("Cannot create tickets.");
        }
    };

    xhr.open('POST' , 'Ticket');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send(jsonData);
}

function deleteTickets(event_id){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Tickets deleted.");
            deleteEvent(event_id);
        }else if(xhr.status !== 200){
            console.log("Cannot delete tickets.");
        }
    };

    xhr.open('DELETE' , 'Ticket');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send(event_id);
}