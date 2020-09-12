let address ='http://' + window.location.hostname + ':' + window.location.port + '/';
let patientId = window.location.hash.substring(1);
console.log(patientId);
// let entriesList = document.querySelector("#entriesList");
let tbody= document.querySelector("#tbodyEntries");

function updateEntryTable() {
    let url = address + "entries/patient/" + patientId;
    fetch( url )
        .then(response => response.json())
        // .then(entries => console.log(entries));
        .then(entries => fillEntriesTable(entries));

}

function fillEntriesTable(entries) {
    tbody.innerHTML = "";
    entries.forEach(e => {
        let tr = document.createElement("tr");
        let linkOpen = address + "entry-view#" + patientId + "%" + e.id;
        let linkEdit = address + "entry-edit#" + patientId + "%" + e.id;
        tr.innerHTML = "<td>" + e.localDateTime + "</td>"
        + "<td>" + e.visitType.innerHTML + "</td>"
        +"<td>" + e.employee.firstName + " " + e.employee.lastName + "</td>"
        + "<a href=" + linkOpen + "><button>Otwórz</button></a>"
        + "<a href=" + linkEdit + "><button>Edytuj</button></a>";
        tbody.appendChild(tr);
    })
}

updateEntryTable();
