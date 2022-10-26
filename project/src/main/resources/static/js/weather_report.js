window.onload = function () {
  /* Set the weekday and date as undertitle */
  let date = parseClickedDate();
  setForecastDayUndertitle(date);

  /* Add table rows */
  insertForecastTableRow("00:00", "moon", "2", "0.4", "1.5", 90);
  insertForecastTableRow("01:00", "moon", "2", "0.4", "2.0", 85);
  insertForecastTableRow("02:00", "moon", "2", "0.3", "1.8", 70);
  insertForecastTableRow("03:00", "moon", "3", "0.3", "2.4", 60);
  insertForecastTableRow("04:00", "cloud-with-moon", "4", "0.2", "2.0", 60);
  insertForecastTableRow("05:00", "cloud", "4", "0.2", "2.0", 60);
  insertForecastTableRow("06:00", "cloud", "4", "0.2", "2.0", 60);
  insertForecastTableRow("07:00", "cloud", "4", "0.2", "2.0", 60);
  insertForecastTableRow("08:00", "cloud-with-sun", "5", "0.2", "2.0", 60);
  insertForecastTableRow("09:00", "cloud-with-sun", "5", "0.2", "2.0", 60);
  insertForecastTableRow("10:00", "cloud-with-sun", "6", "0.2", "2.0", 60);
  insertForecastTableRow("11:00", "cloud-with-sun", "7", "0.2", "2.0", 60);
  insertForecastTableRow("12:00", "cloud-with-sun", "8", "0.2", "2.0", 60);
  insertForecastTableRow("13:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("14:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("15:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("16:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("17:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("18:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("19:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("20:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("21:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("22:00", "sun", "9", "0.2", "2.0", 60);
  insertForecastTableRow("23:00", "sun", "9", "0.2", "2.0", 60);

  /* Render the graphs */
  let temp = [
    { x: "00:00", y: 2.0 },
    { x: "01:00", y: 2.0 },
    { x: "02:00", y: 2.0 },
    { x: "03:00", y: 3.0 },
    { x: "04:00", y: 4.0 },
    { x: "05:00", y: 4.0 },
    { x: "06:00", y: 4.0 },
    { x: "07:00", y: 4.0 },
    { x: "08:00", y: 5.0 },
    { x: "09:00", y: 5.0 },
    { x: "10:00", y: 6.0 },
    { x: "11:00", y: 7.0 },
    { x: "12:00", y: 8.0 },
    { x: "13:00", y: 9.0 },
    { x: "14:00", y: 9.0 },
    { x: "15:00", y: 9.0 },
    { x: "16:00", y: 9.0 },
    { x: "17:00", y: 9.0 },
    { x: "18:00", y: 9.0 },
    { x: "19:00", y: 9.0 },
    { x: "20:00", y: 9.0 },
    { x: "21:00", y: 9.0 },
    { x: "22:00", y: 9.0 },
    { x: "23:00", y: 9.0 }
  ];
  let tempSeries = arrayToSeries('Temperature', temp);
  renderTemperatureChart(tempSeries);

  let precip = [
    { x: "00:00", y: 0.4 },
    { x: "01:00", y: 0.4 },
    { x: "02:00", y: 0.3 },
    { x: "03:00", y: 0.3 },
    { x: "04:00", y: 0.2 },
    { x: "05:00", y: 0.2 },
    { x: "06:00", y: 0.2 },
    { x: "07:00", y: 0.2 },
    { x: "08:00", y: 0.2 },
    { x: "09:00", y: 0.2 },
    { x: "10:00", y: 0.2 },
    { x: "11:00", y: 0.2 },
    { x: "12:00", y: 0.2 },
    { x: "13:00", y: 0.2 },
    { x: "14:00", y: 0.2 },
    { x: "15:00", y: 0.2 },
    { x: "16:00", y: 0.2 },
    { x: "17:00", y: 0.2 },
    { x: "18:00", y: 0.2 },
    { x: "19:00", y: 0.2 },
    { x: "20:00", y: 0.2 },
    { x: "21:00", y: 0.2 },
    { x: "22:00", y: 0.2 },
    { x: "23:00", y: 0.2 }
  ];
  let precipSeries = arrayToSeries('Precipation', precip);
  renderPrecipitationChart(precipSeries);
}

/* Parse from String (in the format 'YYYY-MM-DD') to Date */
function parseClickedDate() {
  let clickedDate = localStorage.getItem("clicked_date");
  let parts = clickedDate.split('-');
  // Beware: JS counts months from 0: January - 0, February - 1, etc.
  let date = new Date(parts[0], parts[1] - 1, parts[2]);
  return date;
}

/* Set the weekday and date as the undertitle */
function setForecastDayUndertitle(date) {
  let forecastDay = document.querySelector('.forecast--day');

  var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

  let newDate = new Date(date);
  forecastDay.innerHTML = `
    ${days[newDate.getDay()]}, ${newDate.getDate()}. ${months[newDate.getMonth()]}
    `;
}

/* Insert a row into the forecast table */
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

/* Render graphs */
function arrayToSeries(type, array) {
  return [{
    name: type, points: array
  }];
}

const forecastGraphTemp = document.querySelector(".forecast_graph--temperature");

function renderTemperatureChart(series) {
  return JSC.Chart(forecastGraphTemp, {
    type: 'Line',
    elements_point_radius: 0,
    title_label_text: 'Temperature',
    xAxis_label_text: 'Time',
    yAxis_label_text: 'Celsius',
    legend_visible: false,
    defaultPoint_tooltip: '%xValue &nbsp;&nbsp;<b>%yValue</b> degrees',
    defaultPoint_marker_visible: false,
    series: series
  });
}

const forecastGraphPrecip = document.querySelector(".forecast_graph--precipitation");

function renderPrecipitationChart(series) {
  return JSC.Chart(forecastGraphPrecip, {
    type: 'Line',
    elements_point_radius: 0,
    title_label_text: 'Precipitation',
    xAxis_label_text: 'Time',
    yAxis_label_text: 'Millimeter',
    legend_visible: false,
    defaultPoint_tooltip: '%xValue &nbsp;&nbsp;<b>%yValue</b> mm',
    defaultPoint_marker_visible: false,
    series: series
  });
}