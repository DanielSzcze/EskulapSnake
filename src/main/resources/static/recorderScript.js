let adress = "http://localhost:8080/"
let findPatientForm = document.querySelector("#findPatientsForm");
let findText = document.querySelector("#findText");
let patientsList = document.querySelector("#patientsList");
let employeesDataList = document.querySelector("#employeesDataList");


let year = new Date().getFullYear();
let month = new Date().getMonth() + 1;
let yearField = document.querySelector("#yearField");
let monthField = document.querySelector("#monthField");


let minusMonthButton = document.querySelector("#minusMonth");
let plusMonthButton = document.querySelector("#plusMonth");
let minusYearButton = document.querySelector("#minusYear");
let plusYearButton = document.querySelector("#plusYear");
let tbody = document.querySelector("tbody");

setMonth();
setYear();
fillCalender();
{
    setEmployeesList();
}
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

function fillEmployeesList(employees) {
    employees.forEach(emp => {
        let option = document.createElement("option");
        option.innerHTML = emp.id + ". " + (emp.firstName) + " " + emp.lastName;
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
        option.innerHTML = patient.id + ". " + patient.firstName + "\ " + patient.lastName + " " + patient.pesel;
        patientsList.appendChild(option);
    });

}

//Calendar
function setMonth() {
    monthField.value = month;
    console.log(month)
}

function setYear() {
    yearField.value = year;
    console.log(year)
}

minusMonthButton.addEventListener("click", function (event) {
    event.preventDefault();
    if (month == 1) {
        month = 12;
        year--;
        setYear();
        setMonth();
    } else {
        month--;

        setMonth();
    }
});
plusMonthButton.addEventListener("click", function (event) {
    event.preventDefault();
    if (month == 12) {
        month = 1;
        year++;
        setMonth();
        setYear();
    } else {
        month = month + 1;
        setMonth();
    }
});

minusYearButton.addEventListener("click", function (event) {
    year--;
    setYear();
});

plusYearButton.addEventListener("click", function (event) {
    year++;
    setYear();
});

function fillCalender() {
    let monthInJS = month - 1;
    let row = document.createElement("tr");
    let currentDay = 1;
    let maxDayIMonth = new Date(year, monthInJS + 1, 0).getDate();
    let firstDay = new Date(year, monthInJS, currentDay);
    let dayOfFirstInMonth = firstDay.getDay();
    let currentDayOfweek = dayOfFirstInMonth;

    for (let i = 1; i < dayOfFirstInMonth; i++) {
        let emptyHeader = document.createElement("th");
        row.appendChild(emptyHeader);
    }
    while (currentDayOfweek <= 7) {
        let dayHeader = document.createElement("th");
        let dayDiv = document.createElement("div");
        dayDiv.innerHTML = "<h2>" + currentDay + "</h2>";
        dayHeader.appendChild(dayDiv);
        row.appendChild(dayHeader);
        currentDayOfweek++;
        currentDay++
    }
    currentDayOfweek = 1;
    tbody.appendChild(row);

    while (currentDay <= maxDayIMonth) {
        let row = document.createElement("tr");
        while (currentDayOfweek <= 7) {
            let dayHeader = document.createElement("th");
            let dayDiv = document.createElement("div");
            dayDiv.innerHTML = "<h2>" + currentDay + "</h2>";
            dayHeader.appendChild(dayDiv);
            row.appendChild(dayHeader);
            currentDayOfweek++;
            currentDay++;
            if (currentDay > maxDayIMonth) break;

        }
        currentDayOfweek=1;
    }
}




