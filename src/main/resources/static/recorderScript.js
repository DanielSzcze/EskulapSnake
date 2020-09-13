let address ='http://' + window.location.hostname + ':' + window.location.port + '/';
let findPatientForm = document.querySelector("#findPatientsForm");
let findText = document.querySelector("#findText");
let patientsList = document.querySelector("#patientsList");
let employeesSelect = document.querySelector("#employeesSelect");
let visitTypesSelect = document.querySelector("#visitTypesSelect");
let addEntryButton = document.querySelector("#postEntryButton");


let year = new Date().getFullYear();
let month = new Date().getMonth() + 1;
let yearField = document.querySelector("#yearField");
let monthField = document.querySelector("#monthField");


let minusMonthButton = document.querySelector("#minusMonth");
let plusMonthButton = document.querySelector("#plusMonth");
let minusYearButton = document.querySelector("#minusYear");
let plusYearButton = document.querySelector("#plusYear");
let tbody = document.querySelector("tbody");
setLists();
setCalender();

function setLists() {
    setEmployeesList();
    setVisTypeList();
}

findText.addEventListener("input", function (event) {
    refreshPatientList();
});
findPatientForm.addEventListener("submit", function (event) {
    event.preventDefault();
    refreshPatientList();
});

function setEmployeesList() {

    let url = address + "employees";
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

function setVisTypeList() {

    let url = address + "visits";
    fetch(url)
        .then(response => response.json())
        .then(visitTypes => fillVisitTypeList(visitTypes));
}

function fillVisitTypeList(visitTypes) {
    visitTypes.forEach(visit => {
        let option = document.createElement("option");
        option.innerHTML = visit.id + ". " + (visit.name);
        visitTypesSelect.appendChild(option);
    });
}


function refreshPatientList() {
    patientsList.innerHTML = "";
    let url = address + "patients/" + findText.value;
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

function getPatientId() {
    let patient = patientsList.value;
    let patientId = patient.substr(0, patient.indexOf('.'));
    return patientId;
}

function getVisitTypeId() {
    let visitType = visitTypesSelect.value;
    let visitTypeId = visitType.substr(0, visitType.indexOf('.'));
    return visitTypeId;
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
    Object.values(document.getElementsByName("EntryRadio"))
        .forEach(function (radio) {
            radio.remove();
        });
    Object.values(document.getElementsByName("cancelButton"))
        .forEach(function (button) {
            button.remove();

        });
    let employeeId = getEmployeeId();
    let date = String(month) + "." + String(year);
    let url = address + "workdays/" + employeeId + "/" + date;
    if (employeeId != "") fetch(url)
        .then(response => response.json())
        .then(workDays => addWorkDays(workDays));

}


function addWorkDays(workDays) {
    Object.values(workDays).forEach(workDay => addWorkDayToCalender(workDay));

}


function addWorkDayToCalender(workDay) {
    let fromWorkTime = workDay.fromWorkTime;
    let toWorkTime = workDay.toWorkTime;
    let fromWorkTimeDay = fromWorkTime.substr(8, 2);
    if (fromWorkTimeDay.startsWith("0")) fromWorkTimeDay.substr(1, 1);
    let toWorkTimeDay = toWorkTime.substr(8, 2);
    if (toWorkTimeDay.startsWith("0")) toWorkTimeDay.substr(1, 1);


    let fromWorkTimeHour = fromWorkTime.substr(11, 2);
    if (fromWorkTimeHour.startsWith("0")) fromWorkTimeHour.substr(1, 1);
    let toWorkTimeHour = toWorkTime.substr(11, 2);
    if (toWorkTimeHour.startsWith("0")) toWorkTimeHour.substr(1, 1);

    let allDivs = document.getElementsByTagName("div");
    Object.values(allDivs)
        .forEach(div => setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour, toWorkTimeHour));
}

function setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour, toWorkTimeHour) {

    div.style = "background-color:white;";
    let day = div.getAttribute("day");
    let hour = div.getAttribute("hour");
    let minutes = div.getAttribute("min");
    let isWorkDay = div.hasAttribute("day")
        && div.hasAttribute("hour")
        && (day >= Number(fromWorkTimeDay))
        && (day <= Number(toWorkTimeDay))
        && (hour >= Number(fromWorkTimeHour))
        && (hour < Number(toWorkTimeHour));
    if (isWorkDay) {
        let radio = document.createElement("input");
        radio.setAttribute("day", day);
        radio.setAttribute("hour", hour);
        radio.setAttribute("minutes", minutes);
        radio.setAttribute("type", "radio");
        radio.setAttribute("name", "EntryRadio")
        div.appendChild(radio);
        div.style = "background-color:yellow";
    }
}


//========================================ENTRIES=========================

function fillCalenderByEntries() {
    let employeeId = getEmployeeId();
    let date = String(month) + "." + String(year);
    let url = address + "entries/" + employeeId + "/" + date;
    if (employeeId != "") fetch(url)
        .then(response => response.json())
        .then(entries => addEntries(entries));
}

function addEntries(entries) {
    Object.values(entries).forEach(entry => addEntryToCalender(entry));

}

function addEntryToCalender(entry) {
    let entryLocalDateTime = String(entry.localDateTime);
    let entryDay = entryLocalDateTime.substr(8, 2);
    if (entryDay.startsWith("0")) entryDay.substr(1, 1);


    let entryHour = entryLocalDateTime.substr(11, 2);
    if (entryHour.startsWith("0")) enryHour.substr(1, 1);
    let entryMinutes = entryLocalDateTime.substr(14, 2);
    let entryId = entry.id

    let listOfDivs = document.querySelectorAll("div");
    Object.values(listOfDivs)
        .forEach(div => setIfEntry(div, entryDay, entryHour, entryMinutes, entryId));
}

function setIfEntry(div, entryDay, entryHour, entryMinutes, entryId) {

    let day = div.getAttribute("day");
    let hour = div.getAttribute("hour");
    let minutes = div.getAttribute("min");
    let isEntryTime = div.hasAttribute("day")
        && div.hasAttribute("hour")
        && (day == Number(entryDay))
        && (hour == Number(entryHour))
        && (minutes == Number(entryMinutes));

    if (isEntryTime) {
        let radio = div.querySelector("input");

        let cancelButton = document.createElement("button");
        cancelButton.innerText = "cancel";
        cancelButton.addEventListener("click", function (event) {
            confirm("Are you sure to delete it?");
            cancelEntry(event, entryId);

        });
        cancelButton.name = "cancelButton";
        div.appendChild(cancelButton);
        if (radio != null) radio.remove();
        div.style = "background-color:red;";
    }
}

function cancelEntry(event, entryId) {
    event.preventDefault();
    let url = address + "entries/" + entryId;
    fetch(url, {

        method: "delete",
    })
        .then(response => {
            if (response.status == 202) {
                setCalender();
            } else {
                alert("problem with delete entry")
            }
        });


}

addEntryButton.addEventListener("click", function () {
    postEntry();

});

function postEntry() {
    event.preventDefault();
    let employeeId = getEmployeeId();
    let patientId = getPatientId();
    let selectedRadio = document.querySelector("input[name=EntryRadio]:checked");
    let vistTypeId = getVisitTypeId();
    if (employeeId == "") alert("select employee");
    else if (patientId == "") alert("select patient");
    else if (selectedRadio == null) alert("select time");
    else if (vistTypeId == "") alert("select visit type");

    else {
        let day = selectedRadio.getAttribute("day");
        if (String(day).length == 1) day = "0" + day;
        let hour = selectedRadio.getAttribute("hour");
        if (String(hour).length == 1) hour = "0" + hour;
        let minutes = selectedRadio.getAttribute("minutes");
        if (String(minutes).length == 1) minutes = "0" + minutes;
        let localMonth = month;
        if (String(localMonth).length == 1) localMonth = "0" + localMonth;

        let dateTime = year + "-" + localMonth + "-" + day + "T" + hour + ":" + minutes + ":00"
        console.log(dateTime);
        let json = "{" +
            '"patientId": ' + patientId + ","
            + '"visitTypeId": ' + vistTypeId + ","
            + '"employeeId": ' + employeeId + ","
            + '"localDateTime": ' + '"' + dateTime + '"'
            + "}";
        console.log(json)
        let url = address + "entries"
        fetch(url, {

            method: "post",
            headers: {

                "Content-Type": "application/json"
            },
            body:
            json
        })
            .then(response => {
                if (response.ok) {
                    setCalender();
                } else {
                    alert("problem with creating entry")
                }
            });
    }
}
