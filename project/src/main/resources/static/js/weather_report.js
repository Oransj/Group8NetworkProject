window.onload = async function () {
  /* Set the weekday and date as undertitle */
  let date = parseClickedDate();
  setForecastDayUndertitle(date);

  const data = (await getDayRapport());
  const dayRapport = data.at(0).data;
  const dayRapportType = data.at(1).data;

  /* Add table rows */
  insertForecastTableRow("00:00", dayRapportType.at(0), dayRapport.at(0).at(0), dayRapport.at(0).at(1), dayRapport.at(0).at(2), dayRapport.at(0).at(3));
  insertForecastTableRow("01:00", dayRapportType.at(1), dayRapport.at(1).at(0), dayRapport.at(1).at(1), dayRapport.at(1).at(2), dayRapport.at(1).at(3));
  insertForecastTableRow("02:00", dayRapportType.at(2), dayRapport.at(2).at(0), dayRapport.at(2).at(1), dayRapport.at(2).at(2), dayRapport.at(2).at(3));
  insertForecastTableRow("03:00", dayRapportType.at(3), dayRapport.at(3).at(0), dayRapport.at(3).at(1), dayRapport.at(3).at(2), dayRapport.at(3).at(3));
  insertForecastTableRow("04:00", dayRapportType.at(4), dayRapport.at(4).at(0), dayRapport.at(4).at(1), dayRapport.at(4).at(2), dayRapport.at(4).at(3));
  insertForecastTableRow("05:00", dayRapportType.at(5), dayRapport.at(5).at(0), dayRapport.at(5).at(1), dayRapport.at(5).at(2), dayRapport.at(5).at(3));
  insertForecastTableRow("06:00", dayRapportType.at(6), dayRapport.at(6).at(0), dayRapport.at(6).at(1), dayRapport.at(6).at(2), dayRapport.at(6).at(3));
  insertForecastTableRow("07:00", dayRapportType.at(7), dayRapport.at(7).at(0), dayRapport.at(7).at(1), dayRapport.at(7).at(2), dayRapport.at(7).at(3));
  insertForecastTableRow("08:00", dayRapportType.at(8), dayRapport.at(8).at(0), dayRapport.at(8).at(1), dayRapport.at(8).at(2), dayRapport.at(8).at(3));
  insertForecastTableRow("09:00", dayRapportType.at(9), dayRapport.at(9).at(0), dayRapport.at(9).at(1), dayRapport.at(9).at(2), dayRapport.at(9).at(3));
  insertForecastTableRow("10:00", dayRapportType.at(10), dayRapport.at(10).at(0), dayRapport.at(10).at(1), dayRapport.at(10).at(2), dayRapport.at(10).at(3));
  insertForecastTableRow("11:00", dayRapportType.at(11), dayRapport.at(11).at(0), dayRapport.at(11).at(1), dayRapport.at(11).at(2), dayRapport.at(11).at(3));
  insertForecastTableRow("12:00", dayRapportType.at(12), dayRapport.at(12).at(0), dayRapport.at(12).at(1), dayRapport.at(12).at(2), dayRapport.at(12).at(3));
  insertForecastTableRow("13:00", dayRapportType.at(13), dayRapport.at(13).at(0), dayRapport.at(13).at(1), dayRapport.at(13).at(2), dayRapport.at(13).at(3));
  insertForecastTableRow("14:00", dayRapportType.at(14), dayRapport.at(14).at(0), dayRapport.at(14).at(1), dayRapport.at(14).at(2), dayRapport.at(14).at(3));
  insertForecastTableRow("15:00", dayRapportType.at(15), dayRapport.at(15).at(0), dayRapport.at(15).at(1), dayRapport.at(15).at(2), dayRapport.at(15).at(3));
  insertForecastTableRow("16:00", dayRapportType.at(16), dayRapport.at(16).at(0), dayRapport.at(16).at(1), dayRapport.at(16).at(2), dayRapport.at(16).at(3));
  insertForecastTableRow("17:00", dayRapportType.at(17), dayRapport.at(17).at(0), dayRapport.at(17).at(1), dayRapport.at(17).at(2), dayRapport.at(17).at(3));
  insertForecastTableRow("18:00", dayRapportType.at(18), dayRapport.at(18).at(0), dayRapport.at(18).at(1), dayRapport.at(18).at(2), dayRapport.at(18).at(3));
  insertForecastTableRow("19:00", dayRapportType.at(19), dayRapport.at(19).at(0), dayRapport.at(19).at(1), dayRapport.at(19).at(2), dayRapport.at(19).at(3));
  insertForecastTableRow("20:00", dayRapportType.at(20), dayRapport.at(20).at(0), dayRapport.at(20).at(1), dayRapport.at(20).at(2), dayRapport.at(20).at(3));
  insertForecastTableRow("21:00", dayRapportType.at(21), dayRapport.at(21).at(0), dayRapport.at(21).at(1), dayRapport.at(21).at(2), dayRapport.at(21).at(3));
  insertForecastTableRow("22:00", dayRapportType.at(22), dayRapport.at(22).at(0), dayRapport.at(22).at(1), dayRapport.at(22).at(2), dayRapport.at(22).at(3));
  insertForecastTableRow("23:00", dayRapportType.at(23), dayRapport.at(23).at(0), dayRapport.at(23).at(1), dayRapport.at(23).at(2), dayRapport.at(23).at(3));

  async function getDayRapport() {
    const year = +date.getFullYear();
    const month = +date.getMonth() + 1;

    const today = +date.getDate();

    const hour00 = new Date(`${month}/${today}/${year}, 00:00:00`).getTime();
    const hour01 = new Date(`${month}/${today}/${year}, 01:00:00`).getTime();
    const hour02 = new Date(`${month}/${today}/${year}, 02:00:00`).getTime();
    const hour03 = new Date(`${month}/${today}/${year}, 03:00:00`).getTime();
    const hour04 = new Date(`${month}/${today}/${year}, 04:00:00`).getTime();
    const hour05 = new Date(`${month}/${today}/${year}, 05:00:00`).getTime();
    const hour06 = new Date(`${month}/${today}/${year}, 06:00:00`).getTime();
    const hour07 = new Date(`${month}/${today}/${year}, 07:00:00`).getTime();
    const hour08 = new Date(`${month}/${today}/${year}, 08:00:00`).getTime();
    const hour09 = new Date(`${month}/${today}/${year}, 09:00:00`).getTime();
    const hour10 = new Date(`${month}/${today}/${year}, 10:00:00`).getTime();
    const hour11 = new Date(`${month}/${today}/${year}, 11:00:00`).getTime();
    const hour12 = new Date(`${month}/${today}/${year}, 12:00:00`).getTime();
    const hour13 = new Date(`${month}/${today}/${year}, 13:00:00`).getTime();
    const hour14 = new Date(`${month}/${today}/${year}, 14:00:00`).getTime();
    const hour15 = new Date(`${month}/${today}/${year}, 15:00:00`).getTime();
    const hour16 = new Date(`${month}/${today}/${year}, 16:00:00`).getTime();
    const hour17 = new Date(`${month}/${today}/${year}, 17:00:00`).getTime();
    const hour18 = new Date(`${month}/${today}/${year}, 18:00:00`).getTime();
    const hour19 = new Date(`${month}/${today}/${year}, 19:00:00`).getTime();
    const hour20 = new Date(`${month}/${today}/${year}, 20:00:00`).getTime();
    const hour21 = new Date(`${month}/${today}/${year}, 21:00:00`).getTime();
    const hour22 = new Date(`${month}/${today}/${year}, 22:00:00`).getTime();
    const hour23 = new Date(`${month}/${today}/${year}, 23:00:00`).getTime();
    const hour24 = new Date(`${month}/${today}/${year}, 24:00:00`).getTime();

    const dayRapport = await axios({
      method: 'POST',
      url: 'http://127.0.0.1:8080/api/getDayRapport',
      data: [
        hour00.toString(),
        hour01.toString(),
        hour02.toString(),
        hour03.toString(),
        hour04.toString(),
        hour05.toString(),
        hour06.toString(),
        hour07.toString(),
        hour08.toString(),
        hour09.toString(),
        hour10.toString(),
        hour11.toString(),
        hour12.toString(),
        hour13.toString(),
        hour14.toString(),
        hour15.toString(),
        hour16.toString(),
        hour17.toString(),
        hour18.toString(),
        hour19.toString(),
        hour20.toString(),
        hour21.toString(),
        hour22.toString(),
        hour23.toString(),
        hour24.toString(),
      ]
    });

    const dayRapportType = await axios({
      method: 'POST',
      url: 'http://127.0.0.1:8080/api/getWeatherTypeDayRapport',
      data: [
        hour00.toString(),
        hour01.toString(),
        hour02.toString(),
        hour03.toString(),
        hour04.toString(),
        hour05.toString(),
        hour06.toString(),
        hour07.toString(),
        hour08.toString(),
        hour09.toString(),
        hour10.toString(),
        hour11.toString(),
        hour12.toString(),
        hour13.toString(),
        hour14.toString(),
        hour15.toString(),
        hour16.toString(),
        hour17.toString(),
        hour18.toString(),
        hour19.toString(),
        hour20.toString(),
        hour21.toString(),
        hour22.toString(),
        hour23.toString(),
        hour24.toString(),
      ]
    });

    return [dayRapport, dayRapportType];
  }

  /* Render the graphs */
  let temp = [
    { x: "00:00", y: dayRapport.at(0).at(0) },
    { x: "01:00", y: dayRapport.at(1).at(0) },
    { x: "02:00", y: dayRapport.at(2).at(0) },
    { x: "03:00", y: dayRapport.at(3).at(0) },
    { x: "04:00", y: dayRapport.at(4).at(0) },
    { x: "05:00", y: dayRapport.at(5).at(0) },
    { x: "06:00", y: dayRapport.at(6).at(0) },
    { x: "07:00", y: dayRapport.at(7).at(0) },
    { x: "08:00", y: dayRapport.at(8).at(0) },
    { x: "09:00", y: dayRapport.at(9).at(0) },
    { x: "10:00", y: dayRapport.at(10).at(0) },
    { x: "11:00", y: dayRapport.at(11).at(0) },
    { x: "12:00", y: dayRapport.at(12).at(0) },
    { x: "13:00", y: dayRapport.at(13).at(0) },
    { x: "14:00", y: dayRapport.at(14).at(0) },
    { x: "15:00", y: dayRapport.at(15).at(0) },
    { x: "16:00", y: dayRapport.at(16).at(0) },
    { x: "17:00", y: dayRapport.at(17).at(0) },
    { x: "18:00", y: dayRapport.at(18).at(0) },
    { x: "19:00", y: dayRapport.at(19).at(0) },
    { x: "20:00", y: dayRapport.at(20).at(0) },
    { x: "21:00", y: dayRapport.at(21).at(0) },
    { x: "22:00", y: dayRapport.at(22).at(0) },
    { x: "23:00", y: dayRapport.at(23).at(0) }
  ];
  let tempSeries = arrayToSeries('Temperature', temp);
  renderTemperatureChart(tempSeries);

  let precip = [
    { x: "00:00", y: dayRapport.at(0).at(1) },
    { x: "01:00", y: dayRapport.at(1).at(1) },
    { x: "02:00", y: dayRapport.at(2).at(1) },
    { x: "03:00", y: dayRapport.at(3).at(1) },
    { x: "04:00", y: dayRapport.at(4).at(1) },
    { x: "05:00", y: dayRapport.at(5).at(1) },
    { x: "06:00", y: dayRapport.at(6).at(1) },
    { x: "07:00", y: dayRapport.at(7).at(1) },
    { x: "08:00", y: dayRapport.at(8).at(1) },
    { x: "09:00", y: dayRapport.at(9).at(1) },
    { x: "10:00", y: dayRapport.at(10).at(1) },
    { x: "11:00", y: dayRapport.at(11).at(1) },
    { x: "12:00", y: dayRapport.at(12).at(1) },
    { x: "13:00", y: dayRapport.at(13).at(1) },
    { x: "14:00", y: dayRapport.at(14).at(1) },
    { x: "15:00", y: dayRapport.at(15).at(1) },
    { x: "16:00", y: dayRapport.at(16).at(1) },
    { x: "17:00", y: dayRapport.at(17).at(1) },
    { x: "18:00", y: dayRapport.at(18).at(1) },
    { x: "19:00", y: dayRapport.at(19).at(1) },
    { x: "20:00", y: dayRapport.at(20).at(1) },
    { x: "21:00", y: dayRapport.at(21).at(1) },
    { x: "22:00", y: dayRapport.at(22).at(1) },
    { x: "23:00", y: dayRapport.at(23).at(1) }
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