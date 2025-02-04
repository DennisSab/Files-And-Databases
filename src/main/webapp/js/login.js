function loginUser(){
    let myForm = document.getElementById('loginForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);

    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if (xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful login.");
            if(user_type === "admin"){
                window.location.replace("adminPage.html");
            }else{
                window.location.replace("dashboard.html");
            }
        }else if(xhr.status === 403){
            console.log("Incorrect password.");
        }else if(xhr.status === 404){
            console.log("User does not exist.");
        }
    };

    var user_type = document.getElementById("username").value;

    if(user_type === "admin"){
        xhr.open('POST', 'LoginAdmin');
    }else{
       xhr.open('POST', 'LoginCustomer');
    }

    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(jsonData);
}

function logout(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful Logout");
            window.location.replace("index.html");
        }else if(xhr.status !== 200){
            console.log('Request failed. Returned status of ' + xhr.status);
        }
    };

    xhr.open('POST', 'Logout');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}