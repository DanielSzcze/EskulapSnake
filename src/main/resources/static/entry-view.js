let address ='http://' + window.location.hostname + ':' + window.location.port + '/';
console.log(location.hash);
let patientId = window.location.hash.substring(1, window.location.hash.indexOf("%"));
let entryId = window.location.hash.substring(window.location.hash.indexOf("%") + 1);
console.log(patientId + " " + entryId);
let outDateTime = document.querySelector("#entryDateEntry");
let outVisitType = document.querySelector("#visitTypeEntry");
let outEmployee = document.querySelector("#employeeEntry");
let outExam= document.querySelector("#examinationEntry");
let outRecom = document.querySelector("#recommendationsEntry");
let entryEdit = document.querySelector("#entryEdit");
let outPatient = document.querySelector("#patientEntry");

function updateOutputs (entry){
    let localDateTime = entry.localDateTime;
    let date = localDateTime.substring(0,10);
    let time = localDateTime.substring(11,19)
    let linkEdit = address + "entry-edit#" + patientId + "%" + entryId;
    outDateTime.innerHTML = date + " " + "<br>" + time;
    outVisitType.innerHTML = entry.visitType.name;
    outEmployee.innerHTML = entry.employee.firstName + " " + entry.employee.lastName;
    outExam.innerHTML = entry.examination;
    outRecom.innerHTML = entry.recommendations;
    entryEdit.setAttribute("href", linkEdit);
}

function getEntry() {
    let url = address + "entries/" + entryId;
    fetch( url )
        .then(response => response.json())
        .then(entry => updateOutputs(entry));
}

function getPatient() {
    let url = address + "patients/" + patientId;
    fetch( url )
        .then(response => response.json())
        .then(patient => outPatient.innerHTML = patient.firstName + " " + patient.lastName);
}

getEntry();
getPatient();