var weatherCards = document.querySelectorAll(".weather-card");

weatherCards.forEach(card => card.addEventListener("click", function() {
    setBackgroundToRed(card)
}));

function setBackgroundToRed(element) {
    if (element.style.backgroundColor == "purple") {
        element.style.backgroundColor = "rgba(255, 255, 255, 0.4)"
    } else {
        element.style.backgroundColor = "purple";
    }
}