function create_Event(){
    let myForm = document.getElementById('create-event-form');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);
    var event_name = document.getElementById("event-name").value;
    var ticket_price = document.getElementById("general_price").value;
    var ticket_amount = parseInt(document.getElementById("general_amount").value);
    var vip_ticket_amount = parseInt(document.getElementById("vip_amount").value);
    var vip_ticket_price = document.getElementById("vip_price").value;
    var capacity = parseInt(document.getElementById("event-capacity").value);
    var xhr = new XMLHttpRequest();


    if((ticket_amount + vip_ticket_amount) === capacity){
        xhr.onload = function (){
            if(xhr.readyState === 4 && xhr.status === 200){
                console.log("Successful event creation.");
                admin();
                createTicketJSON(event_name , "General" , ticket_price , ticket_amount);
                createTicketJSON(event_name , "VIP" , vip_ticket_price , vip_ticket_amount);
            }else if(xhr.status !== 200){
                console.log("Invalid information.");
            }
        };

        xhr.open('POST' , 'Event');
        xhr.setRequestHeader('Content-type' , 'application/json');
        xhr.send(jsonData);
    }else{
        alert("Invalid ticket amounts.");
    }
}

function deleteEverything(event_id){
    deleteBookings(event_id);
}

function deleteEvent(event_id){
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Event deleted.");
            admin();
        }else if(xhr.status !== 200){
            console.log("Cannot delete event.");
        }
    };

    xhr.open('DELETE' , 'Event');
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send(event_id);
}