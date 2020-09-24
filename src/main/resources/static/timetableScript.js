let address = 'http://' + window.location.hostname + ':' + window.location.port + '/';

let employeesSelect = document.querySelector("#employeesSelect");

let addWorkDayButton = document.querySelector("#postWorkDayButton");
let employeeId;
setEmployeeId();

let year = new Date().getFullYear();
let month = new Date().getMonth() + 1;
let yearField = document.querySelector("#yearField");
let monthField = document.querySelector("#monthField");
let daySelect = document.querySelector("#daySelect");
let hourFromSelect = document.querySelector("#hourFromSelect");
let hourToSelect = document.querySelector("#hourToSelect");


let minusMonthButton = document.querySelector("#minusMonth");
let plusMonthButton = document.querySelector("#plusMonth");
let minusYearButton = document.querySelector("#minusYear");
let plusYearButton = document.querySelector("#plusYear");
let tbody = document.querySelector("tbody");
setEmployeesList();
setCalender();
setLists();

function setLists() {
    setHourLists()
    setDayList()
}

function setHourLists() {
    for (let i = 7; i <= 19; i++) {
        let optionF = document.createElement("option");
        let optionT = document.createElement("option");
        optionF.innerText = i + ":00";
        optionT.innerText = i + ":00";
        hourFromSelect.appendChild(optionF)
        hourToSelect.appendChild(optionT)
    }
}

function setDayList() {
    let maxDayIMonth = new Date(year, month, 0).getDate()
    for (let i = 1; i <= maxDayIMonth; i++) {
        let option = document.createElement("option");
        option.innerText = String(i);
        daySelect.appendChild(option)
    }
}

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
        if (employeesSelect != null) employeesSelect.appendChild(option);
    });
}


function setEmployeeId() {

    let employee = employeesSelect.value;
    employeeId = employee.substr(0, employee.indexOf('.'));

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
    setDayList()
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
        let text = String(h);
        text += ":00"
        let hourDiv = document.createElement("div");

        hourDiv.innerHTML = "<h4 style='margin: 0'>" + text + "</h4>"
        hourDiv.setAttribute("day", dayDiv.getAttribute("day"));
        hourDiv.setAttribute("hour", h);

        dayDiv.appendChild(hourDiv);
    }
}

employeesSelect.addEventListener("change", function () {
    fillCalenderByWorkDays();
});

function fillCalenderByWorkDays() {


    Object.values(document.getElementsByName("cancelButton"))
        .forEach(function (button) {
            button.remove();

        });
    setEmployeeId();
    let date = String(month) + "." + String(year);
    let url
    url = address + "workdays/employeeId/" + employeeId + "/" + date;
    fetch(url)
        .then(response => response.json())
        .then(workDays => addWorkDays(workDays));

}


function addWorkDays(workDays) {
    Object.values(workDays).forEach(workDay => addWorkDayToCalender(workDay));

}


function addWorkDayToCalender(workDay) {
    let isFirst = true
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
        .forEach(div => {
            isFirst = setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour,
                toWorkTimeHour, isFirst, workDay.id)

        });
}

function setIfWorkDay(div, fromWorkTimeDay, toWorkTimeDay, fromWorkTimeHour,
                      toWorkTimeHour, isFirst, workDayId) {

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
        if (isFirst) {
            let cancelButton = document.createElement("button")
            cancelButton.setAttribute("name", "cancelButton")

            cancelButton.addEventListener("click", function () {
                confirm("Are you sure to delete this workay?");
                cancelWorkDay(workDayId)
            })
            cancelButton.innerText = "cancel"
            div.insertBefore(cancelButton, div.childNodes[0])
            isFirst = false;
        }

        div.style = "background-color:yellow; size:flexible";
    }
    return isFirst
}


function cancelWorkDay(workDayId) {
    let url = address + "workdays/" + workDayId;
    fetch(url, {

        method: "delete",
    })
        .then(response => {
            if (response.status == 202) {
                setCalender();
            } else {
                alert("problem with delete work day")
            }
        });


}

addWorkDayButton.addEventListener("click", function () {
    postWorkDay();

});

function postWorkDay() {
    event.preventDefault();
    setEmployeeId();
    let day = daySelect.value
    let fromHour = hourFromSelect.value
    let toHour = hourToSelect.value
    let tH = toHour.substr(0, toHour.indexOf(":"))
    let fH = fromHour.substr(0, fromHour.indexOf(":"))
    if (employeeId == "") alert("select employee");
    else if (Number(tH) <= Number(fH))
        alert("select correct hours time");

    else {
        if (String(day).length == 1) day = "0" + day;
        if (String(toHour).length == 4) toHour = "0" + toHour;
        if (String(fromHour).length == 4) fromHour = "0" + fromHour;
        let localMonth = month;
        if (String(localMonth).length == 1) localMonth = "0" + localMonth;

        let fromWorkTime = year + "-" + localMonth + "-" + day + "T" + fromHour;
        let toWorkTime = year + "-" + localMonth + "-" + day + "T" + toHour;
        let json = "{"
            +' "fromWorkTime": ' + '"' + fromWorkTime + '"' + ","
            + ' "toWorkTime": ' + '"' + toWorkTime + '"' + ","
            + ' "employeeId": ' +  employeeId

            + "}"
        console.log(json)
        let url = address + "workdays"
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
                }
            });
    }
}


