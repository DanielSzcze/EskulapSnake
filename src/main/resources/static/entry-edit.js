console.log(location.hash);
let patientId = location.hash.substring(1, location.hash.indexOf("%"));
let entryId = location.hash.substring(location.hash.indexOf("%") + 1);
let address = "http://localhost:8080";
console.log(patientId + " " + entryId);
let entryForm = document.querySelector("#entryForm");
let employeesSelectionList = document.querySelector("#employee");
let visitTypeSelectionList = document.querySelector("#visitType");
let patientsSelectionList = document.querySelector("#patient");
let entryDate = document.querySelector("#entryDate");
console.log(location.hash);


function fillEmployeesSelectionList(employees) {
    employeesSelectionList.innerHTML = "";
    employeesSelectionList.appendChild(document.createElement("option"));
    employees.forEach(employee => {
            //dodać warunek że role musza zaweirać pole medyk

            let option = document.createElement("option")
            option.value = employee;
            option.innerHTML = employee.id + " " + employee.firstName + " " + employee.lastName;
            employeesSelectionList.appendChild(option);
            // console.log(employee);
        }
    );
}

function updateEmployeeList() {
    let url = address + "/employees"
    fetch(url)
        .then(response => response.json())
        .then(employees => fillEmployeesSelectionList(employees));
}

function fillVisitTypeList(visitTypes) {
    visitTypeSelectionList.innerHTML = "";
    visitTypeSelectionList.appendChild(document.createElement("option"));
    visitTypes.forEach(vt => {
            let option = document.createElement("option")
            // option.value = employee;

            option.innerHTML = vt.name;
            visitTypeSelectionList.appendChild(option);
        }
    );
}

function updateVisitTypeList() {
    let url = address + "/visits"
    fetch(url)
        .then(response => response.json())
        .then(visitTypes => fillVisitTypeList(visitTypes));
}

function fillPatientList(patients) {
    patientsSelectionList.innerHTML = "";
    patientsSelectionList.appendChild(document.createElement("option"));
    patients.forEach(p => {
            let option = document.createElement("option")
            // option.value = employee;

            option.innerHTML = /*p.id + ". " + */p.firstName + " " + p.lastName;
            patientsSelectionList.appendChild(option);
        }
    );
}

function updatePatientList() {
    let url =  address + "/patients"
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
setEntryDate();
// console.log(entryDate.value);