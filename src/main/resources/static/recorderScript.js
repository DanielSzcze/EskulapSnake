let adress = "http://localhost:8080/"
let findPatientForm = document.querySelector("#findPatientsForm");
let findText = document.querySelector("#findText");
let patientsList = document.querySelector("#patientsList");
let employeesDataList = document.querySelector("#employeesDataList");
setEmployeesList()
findText.addEventListener("input", function (event) {
    refreshPatientList();
});
findPatientForm.addEventListener("submit", function (event) {
    event.preventDefault();
    refreshPatientList();
});

function setEmployeesList() {

    let url = adress + "employees";
    fetch(url)
        .then(response => response.json())
        .then(employees => fillEmployeesList(employees));
}
function  fillEmployeesList(employees) {
    employees.forEach(employee =>{
        let option = document.createElement("option");
        option.innerHTML = employee.id+ ". "+ employee.firstName+ +" "+ employee.lastName;
        employeesDataList.appendChild(option);
    });

}


function refreshPatientList() {
    patientsList.innerHTML = "";
    let url = adress + "patients/" + findText.value;
    fetch(url)
        .then(response => response.json())
        .then(patients => fillPatientsList(patients));

}

function fillPatientsList(patients) {

    patients.forEach(patient => {
        let option = document.createElement("option");
        option.innerHTML = patient.id + ". " + patient.firstName + " " + patient.lastName + " " + patient.pesel;
        patientsList.appendChild(option);
    });

}


