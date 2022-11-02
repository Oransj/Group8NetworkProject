window.onload = function() {
    const data = getDataFromAPI2();
    insertWeatherCard(data.date, data.weekday, data.weatherType, data.minTemp, data.maxTemp, data.rainAmount, data.windAmount);
    insertWeatherCard("2022-10-24", "Tomorrow", "rain-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("2022-10-25", "Sunday", "cloud-with-sun", "6", "8", "4.0", "2.0");
    insertWeatherCard("2022-10-26", "Monday", "thunder", "6", "8", "4.0", "2.0");

    addEventListeners();
}

async function getDataFromAPI() {
    const currentDate = new Date(Date.now()).toLocaleDateString('en-US').split('/');

    const year = +currentDate.at(2);
    const month = +currentDate.at(0);

    const today = +currentDate.at(1);
    const day2 = +currentDate.at(1) + 1;
    const day3 = +currentDate.at(1) + 2;
    const day4 = +currentDate.at(1) + 3;

    const todayMs = new Date(`${month}/${today}/${year}, 00:00:00`).getTime();
    const day2Ms = new Date(`${month}/${day2}/${year}, 00:00:00`).getTime();
    const day3Ms = new Date(`${month}/${day3}/${year}, 00:00:00`).getTime();
    const day4Ms = new Date(`${month}/${day4}/${year}, 00:00:00`).getTime();

    const frontPageData = await axios({
        method: 'POST',
        url: 'http://127.0.0.1:8080/api/front',
        data: {
            todayMs,
            day2Ms,
            day3Ms,
            day4Ms,
        }
    });

    return frontPageData;
}


function getDataFromAPI2() {
    const data = {
        date: "2022-10-23",
        weekday: "Today",
        weatherType: "sun",
        minTemp: "6",
        maxTemp:"8",
        rainAmount: "4.0",
        windAmount: "2,0",
    }

    return data;
}

function insertWeatherCard(date, weekday, weatherType, minTemp, maxTemp, rainAmount, windAmount) {
    let weatherCardContainer = document.querySelector(".weather-card-container");
    
    /* The 'data-date' value here is in format YYYY-MM-DD */
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

const test = async () => {
    const res = await axios({
        method: 'GET',
        url: 'http://127.0.0.1:8080/api/front',
    });

    console.log(res.data);
    // console.log(res.data[0].Time.ms.num);

}

test();
