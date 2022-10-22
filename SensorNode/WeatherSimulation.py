from calendar import monthrange
import datetime
from random import randrange, uniform

from numpy import pi, round_, sin

    
class weather:

    def __init__(self, temperature : int, precipitation : float, lux : float, pascal : int, windpower : int, winddir : int) -> None:
        """The constructor of the weather class. The weather class hold the weather data for the simulation and one unit of time.

        Args:
            temperature (int): The temperature in degrees celsius.
            precipitation (float): The precipitation in millimeters.
            lux (float): The lux value.
            pascal (int): The pascal value.
            windpower (int): The windpower in meters per second.
            winddir (chr): The wind direction.
        """        
        self.temperature = temperature
        self.precipitation = precipitation
        self.lux = lux
        self.pascal = pascal
        self.windpower = windpower
        self.winddir = winddir

    def __str__(self) -> str:
        return f"Temperature: {self.temperature}°C, Precipitation: {self.precipitation}mm, Lux: {self.lux}lx, Pascal: {self.pascal}Pa, Windpower: {self.windpower}m/s, Winddir: {self.winddir}"

class day:
    def __init__(self) -> None:
        """The constructor of the day class. The day class hold the weather data for each time slot of the day.
        """                
        self.weather_list = {}
        minutes_to_add = 15
        time = datetime.datetime(2020, 1, 1, 0, 0, 0)
        for i in range(int(24*60/minutes_to_add)):
            self.weather_list[time.time()] = None
            time = time + datetime.timedelta(minutes=minutes_to_add)

    def add_weather(self, time : datetime.time, weather : weather):
        """Adds a weather object to the assigned time slot.

        Args:
            time (datetime.time): The time slot.
            weather (weather): The weather object to assign to the time slot.
        """        
        self.weather_list[time] = weather
        print(time)
        print(f"{self.weather_list[time]} TIME: {time}")
        
def sinus(x : float):
    A = 1
    w = 2*pi
    phi = 0
    return A * sin(w * x + phi)

print(sinus(0))

newDay = day()
newDay.add_weather(datetime.time(0,0,0), weather(10, 0.0, 0.0, 0, 0, 'N'))
