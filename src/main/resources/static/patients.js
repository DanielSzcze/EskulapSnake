let address ='http://' + window.location.hostname + ':' + window.location.port + '/';
// let patientsTable = document.querySelector("#patients");
let tbodyPatients = document.querySelector("#tbodyPatients");

function updatePatientTable() {
    let url = address + "patients";
       fetch(url)
        .then(response => response.json())
        .then(patients => filterPatients(patients))
        .then(patients => fillPatientsTable(patients))
}

function fillPatientsTable(patients) {
    tbodyPatients.innerHTML = "";
    patients.forEach(patient => {
        let tr = document.createElement("tr");
        let linkList = address + "entry-list#" + patient.id;
        let linkAdd = address + "entry-edit#" + patient.id;
        tr.innerHTML = "<td>" + patient.firstName + "</td>"
            + "<td>" + patient.lastName + ' ' + "</td>"
            + "<td>" + patient.pesel + "</td>"
            + "<a class=\"button\" href=" + linkList + ">Entries list</a>";
            // + "<br>" +
            // + "<a class=\"button\" href=" + linkAdd + ">Dodaj wpis</a>"
            tbodyPatients.appendChild(tr);
    })
}

function filterPatients(patients) {
    let filter = document.querySelector("#filter").value;
    return patients.filter(patient => (patient.firstName + " " + patient.lastName).includes(filter) || (patient.lastName + " " + patient.firstName).includes(filter) || patient.pesel.includes(filter));
}

updatePatientTable();

let filterForm = document.querySelector("#filterForm");

filterForm.addEventListener("submit", function (event) {
    event.preventDefault();
    updatePatientTable();
});


//TODO obsługa przycisku "dodaj wpis"
