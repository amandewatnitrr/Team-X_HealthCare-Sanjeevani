var roomIDS = [];
var doctorName = [];
var dates = [];
var times = [];
var c=0;
var curr=0;

function findApps(){
    firebase.auth().onAuthStateChanged((user)=>{
        if(user){
            var userUID = user.uid;
            loadApps(userUID);
            
        }
    })
}

function loadApps(userUID){
    //alert(userUID);
    var appRef = firebase.database().ref('patient/'+userUID+'/callRooms');
    
    //alert("Hit");
    
    appRef.once("value", snap=> {
        snap.forEach(b=>{
            roomIDS.push(b.key);
        });
        roomIDS.forEach(loadCallDetails);
    });
}

function loadCallDetails(id){
    var docRef = firebase.database().ref('callRooms/'+id+'/nameDoc');
    docRef.on('value', (snapshot) => {
        const data = snapshot.val();
        doctorName.push(data);
    });
    var timeRef = firebase.database().ref('callRooms/'+id+'/time');
    timeRef.on('value', (snapshot) => {
        times.push(snapshot.val());
    });
    var dateRef = firebase.database().ref('callRooms/'+id+'/date');
    dateRef.on('value', (snapshot) => {
        dates.push(snapshot.val());
        c++;
        if(c==roomIDS.length){
            doctorName.forEach(updateUI);
        }
    });
}

function updateUI(item){
    var uiUpdateNode = document.createElement("LI");
    var data = "With "+doctorName[curr]+" on "+dates[curr]+" at "+times[curr];
    var upNode = document.createTextNode(data);
    uiUpdateNode.appendChild(upNode);
    document.getElementById("info").appendChild(uiUpdateNode);
    curr++;
}