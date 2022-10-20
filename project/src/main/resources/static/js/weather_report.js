window.onload = function () {
  insertForecastTableRow("00:00", "moon", "2", "0.4", "1.5", 90);
  insertForecastTableRow("01:00", "moon", "2", "0.4", "2.0", 85);
  insertForecastTableRow("02:00", "moon", "2", "0.3", "1.8", 70);
  insertForecastTableRow("03:00", "moon", "3", "0.3", "2.4", 60);
  insertForecastTableRow("03:00", "cloud-with-moon", "4", "0.2", "2.0", 60);
}

var forecastTableBody = document.querySelector(".forecast_table--body");

function insertForecastTableRow(time, weatherType, temp, rainAmount, windAmount, windDirection) {
  forecastTableBody.innerHTML += `
    <tr>
      <td>${time}</td>
      <td>
        <img src="../static/img/static/${weatherType}.svg">
      </td>
      <td>${temp}&#176C</td>
      <td>${rainAmount}mm</td>
      <td>
        <div>${windAmount}m/s<img style="transform: rotate(${windDirection}deg)" src="../static/img/static/arrow.svg"></div>
      </td>
    </tr>
  `;
}

var forecastGraphTemp = document.querySelector(".forecast_graph--temperature");

fetch('https://data.cdc.gov/resource/w9j2-ggv5.csv')
  .then(function (response) {
    return response.text();
  })
  .then(function (text) {
    let series = csvToSeries(text);
    renderChart(series);
  })
  .catch(function (error) {
    // Something went wrong
    console.log(error);
  });

function csvToSeries(text) {
  const lifeExp = 'average_life_expectancy'
  // Convert the data text (CSV) to Jason
  let dataAsJson = JSC.csv2Json(text);
  let male = [], female = [];
  dataAsJson.forEach(function (row) {
    if (row.race === 'All Races') {
      if (row.sex === 'Male') {
        // Add data to male array
        male.push({ x: row.year, y: row[lifeExp] });
      } else if (row.sex === 'Female') {
        // Add data to female array
        female.push({ x: row.year, y: row[lifeExp] });
      }
    }
  });
  return [
    { name: 'Male', points: male },
    { name: 'Female', points: female }
  ];
}

function renderChart(series) {
  JSC.Chart(forecastGraphTemp, {
    series: series
  });
}