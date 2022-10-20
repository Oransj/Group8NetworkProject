window.onload = function() {
    insertWeatherCard("4", "Today", "sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("4", "Tomorrow", "rain-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("4", "Sunday", "cloud-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("4", "Monday", "thunder", "6", "8", "4.0", "2.0");

    addEventListeners();
}

/* Setup weather cards with correct data */
var weatherData = [];

function getWeatherData() {
    throw "Not implemented yet";
}

function insertWeatherCards() {
    throw "Not implemented yet";
}

function insertWeatherCard(date, weekday, weatherType, minTemp, maxTemp, rainAmount, windAmount) {
    let weatherCardContainer = document.querySelector(".weather-card-container");
    weatherCardContainer.innerHTML += `
        <li class="weather-card" data-value="${date}" tabindex="0">
            <h4 class="weather-card--date">${weekday}</h4>
            <img class="weather-card--icon" src="../static/img/animated/${weatherType}.svg" alt="Weather icon">
            <div class="weather-card--min-temp">${minTemp}&#176C</div>
            <div class="weather-card--max-temp">${maxTemp}&#176C</div>
            <div class="weather-card--rain">
                <img class="weather-card--rain__icon" src="../static/img/water drop.png" alt="Rain icon">
                <div class="weather-card--rain--text">${rainAmount}mm</div>
            </div>
            <div class="weather-card--wind">
                <img class="weather-card--wind__icon" src="../static/img/wind.png" alt="Wind icon">
                <div class="weather-card--wind--text">${windAmount}m/s</div>
            </div>
        </li>
    `;
}

function addEventListeners() {
    /* Add event listeners to all weather cards:
    Save date and switch to the weather report page */

    let weatherCards = document.querySelectorAll(".weather-card");
    weatherCards.forEach(card => card.addEventListener("click", function() {
        switchToForecastPage(card)
    }));
}

function switchToForecastPage(element) {
    saveDate(element);
    setPage("weather_report.html");
}

function saveDate(element) {
    let data = element.getAttribute("data-value");
    localStorage.setItem("clicked_date", data);
}

function setPage(url) {
    window.location.href = url;
}