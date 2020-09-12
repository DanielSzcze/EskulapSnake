let patientId = location.hash.substring(1);
console.log(patientId);
let address = "http://localhost:8080";
// let entriesList = document.querySelector("#entriesList");
let tbody= document.querySelector("#tbodyEntries");

function updateEntryTable() {
    let url = address + "/entries/patient/" + patientId;
    fetch( url )
        .then(response => response.json())
        // .then(entries => console.log(entries));
        .then(entries => fillEntriesTable(entries));

}

function fillEntriesTable(entries) {
    tbody.innerHTML = "";
    entries.forEach(e => {
        let tr = document.createElement("tr");
        let linkOpen = "entry-view.html#" + patientId + "%" + e.id;
        let linkEdit = "entry-edit.html#" + patientId + "%" + e.id;
        tr.innerHTML = "<td>" + e.localDateTime + "</td>"
        + "<td>" + e.visitType.innerHTML + "</td>"
        +"<td>" + e.employee.firstName + " " + e.employee.lastName + "</td>"
        + "<a href=" + linkOpen + "><button>Otwórz</button></a>"
        + "<a href=" + linkEdit + "><button>Edytuj</button></a>";
        tbody.appendChild(tr);
    })
}

updateEntryTable();


//
// function filterPatients(patients) {
//     let filter = document.querySelector("#filter").value;
//     return patients.filter(patient => (patient.firstName + " " + patient.lastName).includes(filter) || (patient.lastName + " " + patient.firstName).includes(filter) || patient.pesel.includes(filter));
// }
//
// updatePatientTable();
//
// let filterForm = document.querySelector("#filterForm");
//
// filterForm.addEventListener("submit", function (event) {
//     event.preventDefault();
//     updatePatientTable();
// });
//
