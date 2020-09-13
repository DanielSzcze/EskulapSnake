let address ='http://' + window.location.hostname + ':' + window.location.port + '/';
console.log(location.hash);
let patientId = window.location.hash.substring(1, window.location.hash.indexOf("%"));
let entryId = window.location.hash.substring(window.location.hash.indexOf("%") + 1);
console.log(patientId + " " + entryId);
let entryForm = document.querySelector("#entryForm");
let employeesSelectionList = document.querySelector("#employee");
let visitTypeSelectionList = document.querySelector("#visitType");
let patientsSelectionList = document.querySelector("#patient");
let entryDate = document.querySelector("#entryDate");
let exam = document.querySelector("#examination");
let recom = document.querySelector("#recommendations");

function fillEmployeesSelectionList(employees) {
    employeesSelectionList.innerHTML = "";
    employeesSelectionList.appendChild(document.createElement("option"));
    let role
    employees.forEach(employee => {
            // if(employee.roles.contains("PHYSICIAN")) {
                //TODO dodanie do listy tylko medyków
                let option = document.createElement("option")
                option.value = employee;
                option.innerHTML = employee.id + " " + employee.firstName + " " + employee.lastName;
                employeesSelectionList.appendChild(option);
                // console.log(employee);
            }
    );
}

function updateEmployeeList() {
    let url = address + "employees"
    fetch(url)
        .then(response => response.json())
        .then(employees => fillEmployeesSelectionList(employees));
}

function fillVisitTypeList(visitTypes) {
    visitTypeSelectionList.innerHTML = "";
    visitTypeSelectionList.appendChild(document.createElement("option"));
    visitTypes.forEach(vt => {
            let option = document.createElement("option")
            option.innerHTML = vt.name;
            visitTypeSelectionList.appendChild(option);
        }
    );
}

function updateVisitTypeList() {
    let url = address + "visits"
    fetch(url)
        .then(response => response.json())
        .then(fillVisitTypeList);
}

function fillPatientList(patients) {
    patientsSelectionList.innerHTML = "";
    patientsSelectionList.appendChild(document.createElement("option"));
    patients.forEach(p => {
            let option = document.createElement("option")
            // option.value = employee;

            option.innerHTML = p.id + ". " + p.firstName + " " + p.lastName;
            patientsSelectionList.appendChild(option);
        }
    );
}

function updatePatientList() {
    let url =  address + "patients"
    fetch(url)
        .then(response => response.json())
        .then(patients => fillPatientList(patients));
}

function setEntryDate() {
    if(entryDate.getAttribute("value") == null){
        let date = new Date(Date.now()).toISOString()
        entryDate.value = date.substring( 0, 16 );
    }
}

//TODO przycisk zapisujący wpis i dodający go do listy wpisów danego pacjenta
entryForm.addEventListener("submit", function (event) {
    event.preventDefault();
    // let entry = {
    //     patientId: ;
    //     examination: ,
    //     recommendations: ,
    // }

});
updateEmployeeList();
updateVisitTypeList();
updatePatientList()
// setEntryDate();

//TODO zaciąganie się danych

function entryUpdate(){
    if(entryId != null) {
        let url = address + "entries/" + entryId;
        fetch(url)
            .then(response => response.json())
            .then(entry => fillEntryForm(entry));
    }
}

function fillEntryForm(entry) {
    // console.log(entry.localDateTime);
    // let date = entry.localDateTime.substring(0,16)
    // console.log(entry);
    entryDate.setAttribute("value", entry.localDateTime.substring(0,16));
    exam.setAttribute("value", entry.examination);
    recom.setAttribute("value", entry.recommendation);
    // patientsSelectionList.setAttribute("placeholder", entry.patient.id + " " + entry.patient.firstName + " " + entry.patient.lastName);
    // employeesSelectionList.setAttribute("placeholder", entry.employee.id + " " + entry.employee.firstName + " " + entry.employee.lastName);
    // let e = entry.employee;
    let employeesOptions = employeesSelectionList.children;




    // let option = employeesOptions
    //     .filter(option => findEmpl(option));
    // console.log(option);
    // function findEmpl (option) {
    //     console.log(option)
    //     if( option.innerHTML.includes(e.id) && option.innerHTML.includes(e.lastName)){
    //         option.setAttribute("selected", true);
    //         console.log(option);
    //     }

}





entryUpdate();
