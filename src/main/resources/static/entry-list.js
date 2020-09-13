let address = 'http://' + window.location.hostname + ':' + window.location.port + '/';
let patientId = window.location.hash.substring(1);
console.log(patientId);
// let entriesList = document.querySelector("#entriesList");
let tbody = document.querySelector("#tbodyEntries");

function updateEntryTable() {
    let url = address + "entries/patient/" + patientId;
    fetch(url)
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
        let date = e.localDateTime.substring(0, 10) + " " + e.localDateTime.substring(11, 16);
        tr.innerHTML = "<td>" + date + "</td>"
            + "<td>" + e.employee.firstName + " " + e.employee.lastName + "</td>"
            + "<td>" + e.visitType.name + "</td>"
            + "<a href=" + linkOpen + ">Open</a>" + "<br>"
            + "<a href=" + linkEdit + ">Edit</a>";
        tbody.appendChild(tr);
    })
}

updateEntryTable();
