window.onload = function () {
  loadData();
  console.log(data);

  insertForecastTableRow("00:00", "moon", "2", "0.4", "1.5", 90);
  insertForecastTableRow("01:00", "moon", "2", "0.4", "2.0", 85);
  insertForecastTableRow("02:00", "moon", "2", "0.3", "1.8", 70);
  insertForecastTableRow("03:00", "moon", "3", "0.3", "2.4", 60);
  insertForecastTableRow("03:00", "cloud-with-moon", "4", "0.2", "2.0", 60);
}

var data = 0;

function loadData() {
  data = localStorage.getItem("clicked_date");
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