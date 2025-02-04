function validate(){
    registerUser();
}

function registerUser(){
    let myForm = document.getElementById('signup-form');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);

    var xhr = new XMLHttpRequest();

    xhr.onload = function (){
        if(xhr.readyState === 4 && xhr.status === 200){
            console.log("Successful Registration.");
            window.location.replace("index.html");
        }else if(xhr.status !== 200){
            console.log("Invalid information.");
        }
    };

    xhr.open('POST' , 'Customer');
    xhr.setRequestHeader('Content-type' , 'application/json');
    xhr.send(jsonData);
}