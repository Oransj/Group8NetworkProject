window.onload = function() {
    insertWeatherCard("2022-10-23", "Today", "sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("2022-10-24", "Tomorrow", "rain-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("2022-10-25", "Sunday", "cloud-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("2022-10-26", "Monday", "thunder", "6", "8", "4.0", "2.0");

    addEventListeners();
}

function insertWeatherCard(date, weekday, weatherType, minTemp, maxTemp, rainAmount, windAmount) {
    let weatherCardContainer = document.querySelector(".weather-card-container");
    
    /* The data-date value here is in format YYYY-MM-DD */
    weatherCardContainer.innerHTML += `
        <li class="weather-card" data-date="${date}" tabindex="0">
            <h4 class="weather-card--date">${weekday}</h4>
            <img class="weather-card--icon" src="../img/animated/${weatherType}.svg" alt="Weather icon">
            <div class="weather-card--min-temp">${minTemp}&#176C</div>
            <div class="weather-card--max-temp">${maxTemp}&#176C</div>
            <div class="weather-card--rain">
                <img class="weather-card--rain__icon" src="../img/water drop.png" alt="Rain icon">
                <div class="weather-card--rain--text">${rainAmount}mm</div>
            </div>
            <div class="weather-card--wind">
                <img class="weather-card--wind__icon" src="../img/wind.png" alt="Wind icon">
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

function saveDate(element) {
    let data = element.getAttribute("data-date");
    localStorage.setItem("clicked_date", data);
}

function setPage(url) {
    window.location.href = url;
}

function switchToForecastPage(element) {
    saveDate(element);
    setPage("weather_report.html");
}