function isLoggedIn(user_type){
    var xhr = new XMLHttpRequest();
    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Welcome again " + xhr.responseText);
            if(user_type === "admin"){
                window.location.replace("adminPage.html");
            }else{
                window.location.replace("dashboard.html");
            }
        }else if(xhr.status !== 200){
            console.log('Request failed. Returned status of ' + xhr.status);
        }
    };

    if(user_type === "customer"){
        xhr.open('GET' , 'LoginCustomer');
    }else if(user_type === "admin"){
        xhr.open('GET' , 'LoginAdmin');
    }
    xhr.send();
}

function initDB(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful Initialization");
        }else if(xhr.status !== 200){
            console.log("Error Occurred");
        }
    };

    xhr.open('GET' , 'InitDB');
    xhr.setRequestHeader('Content-type' ,'application/x-www-form-urlencoded');
    xhr.send();
}

function deleteDB(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful Deletion");
        }else if(xhr.status !== 200){
            console.log("Error Occurred");
        }
    };

    xhr.open('GET' , 'DeleteDB');
    xhr.setRequestHeader('Content-type' , 'application/x-www-form-urlencoded');
    xhr.send();
}