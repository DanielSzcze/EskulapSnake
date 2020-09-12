console.log(location.hash);
let patientId = location.hash.substring(1, location.hash.indexOf("%"));
let entryId = location.hash.substring(location.hash.indexOf("%") + 1);
let address = "http://localhost:8080";
console.log(patientId + " " + entryId);
let outDateTime = document.querySelector("#entryDateEntry");
let outVisitType = document.querySelector("#visitTypeEntry");
let outEmployee = document.querySelector("#employeeEntry");
let outExam= document.querySelector("#examinationEntry");
let outRecom = document.querySelector("#recommendationsEntry");
let entryEdit = document.querySelector("#entryEdit");
// let outPatient = document.querySelector("#patientEntry");

function updateOutputs (entry){
    let localDateTime = entry.localDateTime;
    let date = localDateTime.substring(0,10);
    let time = localDateTime.substring(11,19)
    let linkEdit = "entry-edit.html#" + patientId + "%" + entryId;
    outDateTime.innerHTML = date + " " + "<br>" + time;
    outVisitType.innerHTML = entry.visitType.name;
    outEmployee.innerHTML = entry.employee.firstName + " " + entry.employee.lastName;
    outExam.innerHTML = entry.examination;
    outRecom.innerHTML = entry.recommendations;

    entryEdit.setAttribute("href", linkEdit);
    // entryEdit.href = linkEdit;
    // entryEdit.getAttribute("href") = linkEdit;
    // entryEdit.setAttribute("target", "_self");

}

function getEntry() {
    let url = address + "/entries/" + entryId;
    fetch( url )
        .then(response => response.json())
        .then(entry => updateOutputs(entry));
}

getEntry();