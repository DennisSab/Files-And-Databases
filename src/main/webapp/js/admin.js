function admin(){
    document.getElementById("admin_id").value = 1;
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Events table created.");
            document.getElementById("events_table").innerHTML = xhr.responseText;
        }else if(xhr.status !== 200){
            console.log("Cannot create events table.");
        }
    };

    var url = 'EventsTable?user_type=' + encodeURIComponent("admin");
    xhr.open('GET' , url , true);
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}

function admin_stats() {
    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Success.");
            document.getElementById("table").innerHTML = xhr.responseText;
        }else if(xhr.status !== 200){
            console.log("Error.");
        }
    };

    xhr.open('GET' , 'AdminStats');
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}