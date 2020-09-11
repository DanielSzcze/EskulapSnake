let adress = "http://localhost:8080/"
let findPatientForm = document.querySelector("#findPatientsForm");
let findText = document.querySelector("#findText");
let patientsList = document.querySelector("#patientsList");
let employeesSelect = document.querySelector("#employeesSelect");


let year = new Date().getFullYear();
let month = new Date().getMonth() + 1;
let yearField = document.querySelector("#yearField");
let monthField = document.querySelector("#monthField");


let minusMonthButton = document.querySelector("#minusMonth");
let plusMonthButton = document.querySelector("#plusMonth");
let minusYearButton = document.querySelector("#minusYear");
let plusYearButton = document.querySelector("#plusYear");
let tbody = document.querySelector("tbody");

setCalender();
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
        employeesSelect.appendChild(option);
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
function getEmployeeId() {
    let employee = employeesSelect.value;
    let employeeId = employee.substr(0, employee.indexOf('.'));
    return employeeId;
}
//Calendar
function setMonth() {
    monthField.value = month;
}

function setYear() {
    yearField.value = year;
}


function setCalender() {
    setYear();
    setMonth();
    fillCalender();
    fillCalenderByWorkDays();
    fillCalenderByEntries();
}


minusMonthButton.addEventListener("click", function (event) {
    event.preventDefault();
    if (month == 1) {
        month = 12;
        year--;
        setCalender();
    } else {
        month--;

        setCalender();
    }
});
plusMonthButton.addEventListener("click", function (event) {
    event.preventDefault();
    if (month == 12) {
        month = 1;
        year++;
        setCalender();
    } else {
        month = month + 1;
        setCalender();
    }
});

minusYearButton.addEventListener("click", function (event) {
    year--;
    setCalender();
});

plusYearButton.addEventListener("click", function (event) {
    year++;
    setCalender();
});

function fillCalender() {
    tbody.innerHTML = "";
    let monthInJS = month - 1;
    let weekRow = document.createElement("tr");
    let currentDay = 1;
    let maxDayIMonth = new Date(year, monthInJS + 1, 0).getDate();
    let firstDay = new Date(year, monthInJS, currentDay);
    let dayOfFirstInMonth = firstDay.getDay();
    let currentDayOfweek = dayOfFirstInMonth;
    if (currentDayOfweek == 0) currentDayOfweek++;

    for (let i = 1; i < dayOfFirstInMonth; i++) {
        let emptyHeader = document.createElement("th");
        weekRow.appendChild(emptyHeader);
    }
    while (currentDayOfweek <= 7) {
        let dayHeader = document.createElement("th");
        let dayDiv = document.createElement("div");
        dayDiv.innerHTML = "<h2>" + currentDay + "</h2>";
        dayDiv.setAttribute("day", currentDay);
        fillDayDivByHours(dayDiv);
        dayHeader.appendChild(dayDiv);
        weekRow.appendChild(dayHeader);
        currentDayOfweek++;
        currentDay++
    }
    tbody.appendChild(weekRow);

    for (let i = 0; i < 4; i++) {
        let weekRow = document.createElement("tr");
        for (let j = 1; j < 8; j++) {
            if (currentDay <= maxDayIMonth) {
                let dayHead = document.createElement("th");
                let dayDiv = document.createElement("div");
                dayDiv.innerHTML = "<h2 style='margin: 0'>" + currentDay + "</h2>";
                dayDiv.setAttribute("day", currentDay);
                fillDayDivByHours(dayDiv);
                dayHead.appendChild(dayDiv);
                currentDay++;
                weekRow.appendChild(dayHead);
            }
        }
        tbody.appendChild(weekRow);
        if (currentDay > maxDayIMonth) break;


    }


}


function fillDayDivByHours(dayDiv) {
    for (let h = 7; h < 19; h++) {
        hourRow = document.createElement("tr");
        for (let min = 0; min < 60; min += 15) {
            hourHead = document.createElement("th");
            let text = String(h);
            text += ":";
            text += min;
            if (min == 0) text += "0";
            let hourDiv = document.createElement("div");

            hourDiv.innerHTML = "<h4 style='margin: 0'>" + text + "</h4>"
            hourDiv.setAttribute("day", dayDiv.getAttribute("day"));
            hourDiv.setAttribute("hour", h);
            hourDiv.setAttribute("min", min);
            hourHead.appendChild(hourDiv);
            hourRow.appendChild(hourHead);

        }
        dayDiv.appendChild(hourRow);


    }


}

employeesSelect.addEventListener("change", function () {
    fillCalenderByWorkDays();
    fillCalenderByEntries();
});

function fillCalenderByWorkDays() {
    let employeeId = getEmployeeId();
    let date = String(month) + "." + String(year);
    let url = adress + "workdays/" + employeeId + "/" + date;
    fetch(url)
        .then(response => response.json())
        .then(workDays => addWorkDays(workDays));
}



function addWorkDays(workDays) {
    workDays.forEach(workDay => addWorkDayToCalender(workDay));

}

function addWorkDayToCalender(workDay) {

    let fromWorkTimeDay = workDay.fromWorkTime.substr(8, 2);
    if (fromWorkTimeDay.startsWith("0")) fromWorkTimeDay.substr(1, 1);
    let toWorkTimeDay = workDay.toWorkTime.substr(8, 2);
    if (toWorkTimeDay.startsWith("0")) toWorkTimeDay.substr(1, 1);


    let fromWorkTimeHour = workDay.fromWorkTime.substr(11, 2);
    if (fromWorkTimeHour.startsWith("0")) fromWorkTimeHour.substr(1, 1);
    let toWorkTimeHour = workDay.toWorkTime.substr(11, 2);
    if (toWorkTimeHour.startsWith("0")) toWorkTimeHour.substr(1, 1);

    let listOfDivs = document.querySelectorAll("div");
    Object.values(listOfDivs)
        .forEach(div => setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour, toWorkTimeHour));
}

function setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour, toWorkTimeHour) {
    div.style = "background-color:white;";
    let day = div.getAttribute("day");
    let hour = div.getAttribute("hour");
    let isWorkDay = div.hasAttribute("day")
        && div.hasAttribute("hour")
        && (day >= Number(fromWorkTimeDay))
        && (day <= Number(toWorkTimeDay))
        && (hour >= Number(fromWorkTimeHour))
        && (hour < Number(toWorkTimeHour));

    if (isWorkDay) {
        div.style = "background-color:yellow;";

        div.addEventListener("onclick", function(){
            postEntry();
        });
    }


}

function postEntry() {
    console.log("post")
}

//========================================ENTRIES=========================

function fillCalenderByEntries() {
    let employeeId = getEmployeeId();
    let date = String(month) + "." + String(year);
    let url = adress + "entries/" + employeeId + "/" + date;
    fetch(url)
        .then(response => response.json())
        .then(entries => addEntries(entries));
}

function addEntries(entries) {
    entries.forEach(entry => addEntryToCalender(entry));

}

function addEntryToCalender(entry) {
    let entryDay = entry.localDateTime.substr(8, 2);
    if (entryDay.startsWith("0")) entryDay.substr(1, 1);


    let entryHour = entry.localDateTime.substr(11, 2);
    if (entryHour.startsWith("0")) enryHour.substr(1, 1);
    let entryMinutes = entry.localDateTime.substr(14, 2)// szczeelamy!!!

    let listOfDivs = document.querySelectorAll("div");
    Object.values(listOfDivs)
        .forEach(div => setIfEntry(div, entryDay, entryHour, entryMinutes));
}

function setIfEntry(div, entryDay, entryHour, entryMinutes) {

    let day = div.getAttribute("day");
    let hour = div.getAttribute("hour");
    let minutes = div.getAttribute("min");
    let isEntryTime = div.hasAttribute("day")
        && div.hasAttribute("hour")
        && (day == Number(entryDay))
        && (hour == Number(entryHour))
        && (minutes == Number(entryMinutes));

    if (isEntryTime) {
        div.style = "background-color:red;";

        div.addEventListener("onclick", none())
    }
}

function none() {
}

