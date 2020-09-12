let patientsTable = document.querySelector("#patients");
let tbodyPatients = document.querySelector("#tbodyPatients");

function updatePatientTable() {
    fetch("http://localhost:8080/patients")
        .then(response => response.json())
        .then(patients => filterPatients(patients))
        .then(patients => fillPatientsTable(patients))
    // let filter = document.querySelector("#filter").value;
    // console.log(filter);

}

function fillPatientsTable(patients) {
    tbodyPatients.innerHTML = "";
    patients.forEach(patient => {
        let tr = document.createElement("tr");
        let linkList = "entry-list.html#" + patient.id;
        let linkAdd = "entry-edit.html#" + patient.id;
        tr.innerHTML = "<td>" + patient.firstName + "</td>"
            + "<td>" + patient.lastName + ' ' + "</td>"
            + "<td>" + patient.pesel + "</td>"
            + "<a href=" + linkList + "><button>Lista wpisów</button></a>"
            + "<a href=" + linkAdd + "><button>Dodaj wpis</button></a>"
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

//TODO obsługa przycisky "lista wpisów pacjenta"

//dla pacjenta wyszukiwanie wpisów dla pacjenta skąd wziąść id
//dla lekarza lista pacjentów i następnie lista wpisów dla pacjenta
//przycisk nowy wpis
//czy lekarze na liście czy domyślny lekarz
