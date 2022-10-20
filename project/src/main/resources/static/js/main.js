function setLastUpdatedTime() {
    localStorage.setItem("last_updated", new Date());
}

var lastUpdatedElem = document.querySelector(".time-updated");

function getLastUpdatedTime() {
    return localStorage.getItem("last_updated")
}

var date = new Date();
var finaltime = timeSince(date);
lastUpdatedElem.innerHTML = "This page was updated " + timeSince(date) + " ago";

/* Set an interval for the text to be refreshed on the page */
setInterval(function() {
  lastUpdatedElem.innerHTML = "This page was updated " + timeSince(date) + " ago";
}, 30000);

/* Get the amount of time since the page was updated */
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