var doctorUIDs = [];
var spcl = [];
var names = [];
var c=0;
var curr=0;

function findDocs(){
    var docRef = firebase.database().ref('doctor');
    docRef.once("value", snap=> {
        snap.forEach(b=>{
            doctorUIDs.push(b.key);
        });
        doctorUIDs.forEach(loadSpcl);
    });
}

function loadSpcl(doctorID){
    var nameRef = firebase.database().ref('doctor/'+doctorID+'/info/name');
    nameRef.on('value', (snapshot) => {
        names.push(snapshot.val());
    });
    var spclRef = firebase.database().ref('doctor/'+doctorID+'/info/specialisation');
    spclRef.on('value', (snapshot) => {
        spcl.push(snapshot.val());
        c++;
        if(c==doctorUIDs.length){
            doctorUIDs.forEach(updateUI);
        }
    });
}

function updateUI(item){
    var uiUpdateNode = document.createElement("LI");
    var data = "Dr. "+names[curr]+" as a "+spcl[curr];
    var upNode = document.createTextNode(data);
    uiUpdateNode.appendChild(upNode);
    document.getElementById("info").appendChild(uiUpdateNode);
    curr++;
}