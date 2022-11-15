window.onload = async function() {
    const data = (await getDataFromAPI());
    const daysData = data.at(0).data;
    const daysType = data.at(1).data;

    const currentDate = new Date(Date.now()).toLocaleString('en-US').split('/');
    const year = +currentDate.at(2).split(',').at(0);
    const month = +currentDate.at(0);
    const today = +currentDate.at(1);
    const day2 = +currentDate.at(1) + 1;
    const day3 = +currentDate.at(1) + 2;
    const day4 = +currentDate.at(1) + 3;
    const todayMs = new Date(`${month}/${today}/${year}, 00:00:00`).getTime();
    const day2Ms = new Date(`${month}/${day2}/${year}, 00:00:00`).getTime();
    const day3Ms = new Date(`${month}/${day3}/${year}, 00:00:00`).getTime();
    const day4Ms = new Date(`${month}/${day4}/${year}, 00:00:00`).getTime();

    const todayDate = new Date(todayMs);
    const day2Date = new Date(day2Ms);
    const day3Date = new Date(day3Ms);
    const day4Date = new Date(day4Ms);

    insertWeatherCard(todayDate.toLocaleDateString('sv'),
        todayDate.toLocaleString('en-us', {  weekday: 'long' }),
        // "sun",
        daysType.at(0),
        daysData.at(0).at(0),
        daysData.at(0).at(1),
        daysData.at(0).at(2).toFixed(2),
        daysData.at(0).at(3).toFixed(2));
    insertWeatherCard(day2Date.toLocaleDateString('sv'),
        day2Date.toLocaleString('en-us', {  weekday: 'long' }),
        // "rain-with-sun",
        daysType.at(1),
        daysData.at(1).at(0),
        daysData.at(1).at(1),
        daysData.at(1).at(2).toFixed(2),
        daysData.at(1).at(3).toFixed(2));
    insertWeatherCard(day3Date.toLocaleDateString('sv'),
        day3Date.toLocaleString('en-us', {  weekday: 'long' }),
        // "cloud-with-sun",
        daysType.at(2),
        daysData.at(2).at(0),
        daysData.at(2).at(1),
        daysData.at(2).at(2).toFixed(2),
        daysData.at(2).at(3).toFixed(2));
    insertWeatherCard(day4Date.toLocaleDateString('sv'),
        day4Date.toLocaleString('en-us', {  weekday: 'long' }),
        // "thunder",
        daysType.at(3),
        daysData.at(3).at(0),
        daysData.at(3).at(1),
        daysData.at(3).at(2).toFixed(2),
        daysData.at(3).at(3).toFixed(2));

    addEventListeners();
}

async function getDataFromAPI() {
    const currentDate = new Date(Date.now()).toLocaleString('en-US').split('/');

    const year = +currentDate.at(2).split(',').at(0);
    const month = +currentDate.at(0);

    const today = +currentDate.at(1);
    const day2 = +currentDate.at(1) + 1;
    const day3 = +currentDate.at(1) + 2;
    const day4 = +currentDate.at(1) + 3;
    const day5 = +currentDate.at(1) + 4;

    const todayMs = new Date(`${month}/${today}/${year}, 00:00:00`).getTime();
    const day2Ms = new Date(`${month}/${day2}/${year}, 00:00:00`).getTime();
    const day3Ms = new Date(`${month}/${day3}/${year}, 00:00:00`).getTime();
    const day4Ms = new Date(`${month}/${day4}/${year}, 00:00:00`).getTime();
    const day5Ms = new Date(`${month}/${day5}/${year}, 00:00:00`).getTime();

    const frontPageData = await axios({
        method: 'POST',
        url: 'http://127.0.0.1:8080/api/getData',
        data: [
            todayMs.toString(),
            day2Ms.toString(),
            day3Ms.toString(),
            day4Ms.toString(),
            day5Ms.toString()
        ]
    });

    const frontPageType = await axios({
        method: 'POST',
        url: 'http://127.0.0.1:8080/api/getWeatherType',
        data: [
            todayMs.toString(),
            day2Ms.toString(),
            day3Ms.toString(),
            day4Ms.toString(),
            day5Ms.toString()
        ]
    });

    return [frontPageData, frontPageType];
}

function insertWeatherCard(date, weekday, weatherType, minTemp, maxTemp, rainAmount, windAmount) {
    let weatherCardContainer = document.querySelector(".weather-card-container");
    
    /* The 'data-date' value here is in format YYYY-MM-DD */
    weatherCardContainer.innerHTML += `
        <li class="weather-card" data-date="${date}" tabindex="0">
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
