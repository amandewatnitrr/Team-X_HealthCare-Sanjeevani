firebase.auth().onAuthStateChanged((user)=>{
    if(!user){
        location.replace("index.html")
    }
})

var database = firebase.database();


function logout(){
    firebase.auth().signOut()
}

function findContainer(){
    const kitID = document.getElementById("kitID").value;
    if(kitID!=null){
        var x = document.getElementById("valueContainer");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
        fetchDataFromFirebase(kitID);
    }
}

function fetchDataFromFirebase(kitID){
    //alert(kitID);
    var bpRef = firebase.database().ref('iot/' + kitID+'/Blood Pressure');
    bpRef.on('value', (snapshot) => {
        const data = snapshot.val();
        document.getElementById("bp").innerHTML=data;
    });
    var tempRef = firebase.database().ref('iot/' + kitID+'/Temperature');
    tempRef.on('value', (snapshot) => {
        const data = snapshot.val();
        document.getElementById("temp").innerHTML=data;
    });
    var pulseRef = firebase.database().ref('iot/' + kitID+'/Pulse rate');
    pulseRef.on('value', (snapshot) => {
        const data = snapshot.val();
        document.getElementById("pulse").innerHTML=data;
    });
}
