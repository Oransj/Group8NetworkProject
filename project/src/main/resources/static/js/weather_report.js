window.onload = function () {
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
  // Convert the data text (CSV) to JSON
  /*
  let tempAsString = '{"time": ["00:00", "01:00", "02:00", "03:00"],"temp": [7.0, 8.0, 7.6, 8.0]}';
  let tempAsJson = JSON.parse(tempAsString);
  let dataAsJson = JSC.csv2Json(text);
  */
  let temp = [
    {x: "00:00", y: 7.0},
    {x: "01:00", y: 8.0},
    {x: "02:00", y: 7.6},
    {x: "03:00", y: 8.2},
    {x: "04:00", y: 8.3},
    {x: "05:00", y: 8.6},
    {x: "06:00", y: 8.7},
    {x: "07:00", y: 10.1},
    {x: "08:00", y: 12.3},
    {x: "09:00", y: 12.6},
    {x: "10:00", y: 12.9},
    {x: "11:00", y: 13.0},
    {x: "12:00", y: 13.6},
    {x: "13:00", y: 13.8},
    {x: "14:00", y: 13.7},
    {x: "15:00", y: 13.6},
    {x: "16:00", y: 13.7},
    {x: "17:00", y: 13.5}
  ];

  /* tempAsJson.forEach(function (row) {
    temp.push({x: row.time, y: row.temp})
  }); */

  return [
    { name: 'Temperature', points: temp },
  ];
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

function precipToSeries(text) {
  let precip = [{
    "time": "00:00",
    "value": 2.0
  }, {
    "time": "01:00",
    "value": 1.8
  }];

  let array = [];
  precip.forEach(function (obj) {
    array.push({ x: obj.time, y: obj.value })
  });

  return [{
    name: 'Precipitation', points: array
  }];
}

function precipToSeries2(text) {
  let precip = {
    "time": ["00:00", "01:00"],
    "value": [2.0, 1.8]
  }

  let array = [];
  for (var i = 0; i < precip.time.length; i++) {
    array.push({x: precip.time[i], y: precip.value[i]});
  };

  return [{
    name: 'Precipitation', points: array
  }];
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
  })
}