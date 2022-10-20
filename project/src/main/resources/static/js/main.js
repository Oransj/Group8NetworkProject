function setLastUpdatedTime() {
    localStorage.setItem("last_updated", new Date());
}

var lastUpdatedElem = document.querySelector(".time-updated");

function getLastUpdatedTime() {
    return localStorage.getItem("last_updated")
}

var date = new Date();
var finaltime = timeSince(date);

setLastUpdated.onclick = function() {
    setInterval();
}

setInterval(function() {
  lastUpdatedElem.innerHTML = "This page was updated " + timeSince(date) + " ago";
}, 30000);
lastUpdatedElem.innerHTML = "This page was updated " + timeSince(date) + " ago";

function timeSince(date) {
  var seconds = Math.floor((new Date() - date) / 1000);
  var interval = Math.floor(seconds / 31536000);

  if (interval > 1) {
    return interval + " years";
  }
  interval = Math.floor(seconds / 2592000);
  if (interval > 1) {
    return interval + " months";
  }
  interval = Math.floor(seconds / 86400);
  if (interval > 1) {
    return interval + " days";
  }
  interval = Math.floor(seconds / 3600);
  if (interval > 1) {
    return interval + " hours";
  }
  interval = Math.floor(seconds / 60);
  if (interval >= 1) {
    return interval + " minutes";
  }
  return Math.floor(seconds) + " seconds";
}