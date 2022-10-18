function updateLastUpdated() {
    setLastUpdated();
    refreshLastUpdatedText();
}

function getDateAndTime() {
    var currentDate = new Date();
    var dateTime = currentDate.getDate() + "."
        + (currentDate.getMonth()+1)  + "." 
        + currentDate.getFullYear() + " "  
        + currentDate.getHours() + ":"  
        + currentDate.getMinutes();
    return dateTime;
}

function setLastUpdated() {
    localStorage.setItem("last_updated", getDateAndTime());
}

var header = document.querySelector("header");
header.onclick = function() {
    updateLastUpdated();
}

var lastUpdatedElem = document.querySelector(".time-updated");

function refreshLastUpdatedText() {
    lastUpdatedElem.innerHTML = `
    Last updated ${localStorage.getItem("last_updated")}
    `;
}