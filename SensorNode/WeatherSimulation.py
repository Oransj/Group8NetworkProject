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
        
class weights:
    def __init__(self) -> None:
        """The constructor of the weights class. The weights class hold the weights for each weather parameter.
        """        
        #Weights are in the following format: [Change rate, Mininum change, Maximum change]
        self.temp_weights = [0.7, 0.0, 5.0]
        self.air_pressure_weights = [0.75, 0.1, 1.33]
        self.light_weights = [1.0, 10.0, 100.0]
        self.wind_weights = [0.3, 0.0, 10.0]
        self.precipiation_weights = [0.3, 0.01, 1.0]
        
        #The air pressure min, avg, max values
        self.pressure_extreme_points = [970.00, 1013.25, 1040.00]
        #The average precipitation per month and the wettest day of the month.
        # If the wettest day is none that means we dont have data for that.
        self.precipitation_months = [[149.00, None], [130.00, None], [128.00, None], [78.00, 9.70],
                                    [75.00, 17.00], [85.00, 10.00], [84.00, 10.00], [126.00, 17.00],
                                    [161.00, 29.2], [169.00, None], [149.00, None], [176.00, None]]
        self.chance_of_continued_rain = 0.85
        
class percipitation_simulation:
    
    def generate_percipitation_month(self) -> list[float]:
        """Generates a list of precipitation values for the month based upon the average precipitation per month and the wettest day of the month, while considering the weights.

        Returns:
            list[float]: A list of precipitation values for the month.
        """        
        month = []
        days = monthrange(datetime.datetime.now().year, datetime.datetime.now().month)[1]
        precipitation_bucket, wettestday = weights().precipitation_months[datetime.datetime.now().month - 1]
        precipitation_bucket = precipitation_bucket + randrange(-20, 20)
        if(wettestday == None or wettestday == 0):
            wettestday = precipitation_bucket / 8
        elif(wettestday > 10):
            wettestday = wettestday + uniform(-10, 10)
        print(precipitation_bucket)
        print(wettestday)
        for _ in range(days):
            month.append(0)
        print(month)
        current_day = randrange(days-1)
        month[current_day] = wettestday
        rainy_days = 0
        chance_of_end = precipitation_bucket*weights().chance_of_continued_rain
        print(f"Chance of end: {chance_of_end}")
        while(randrange(100) < chance_of_end*0.95**rainy_days and rainy_days+current_day < days-1):
            rainy_days += 1
        print(f"Days1 {rainy_days}")
        precipitation_left = self.create_precipitation_pattern(precipitation_bucket, wettestday, rainy_days)
        print(f"PrecipL {precipitation_left}")
        i = 1
        for precipitation in precipitation_left:
            month[current_day+i] = precipitation
            i += 1
        
        rainy_days = 0
        while(randrange(100) < chance_of_end*0.95**rainy_days and rainy_days+current_day < days-1):
            rainy_days += 1
        print(f"Days2 {rainy_days}")
        precipitation_bucket = precipitation_bucket - sum(month)
        print(f"PrecipB {precipitation_bucket}")
        precipitation_right = self.create_precipitation_pattern(precipitation_bucket, wettestday, rainy_days)
        precipitation_right.reverse()
        print(f"PrecipR {precipitation_right}")
        i = 1
        for precipitation in precipitation_right:
            month[current_day-i] = precipitation
            i += 1
        precipitation_bucket = precipitation_bucket - sum(precipitation_right)
        print(f"PrecipB {precipitation_bucket}")
        print(month)
        
        if(precipitation_bucket > 0):
            month, precipitation_bucket = self.fill_zeroes(month, precipitation_bucket)
        else:
            instances = self.find_indices_of_condition(month, lambda x: x > 5)
            print(f"instances {instances}")
            i = 0
            while(precipitation_bucket < 0):
                month[instances[i]] = month[instances[i]] - 1
                precipitation_bucket += 1
                if(i+1 > len(instances)-1):
                    i = 0
                else:
                    i += 1
            print(month)
            print(precipitation_bucket)
            month = self.fill_zeroes(wettestday, month)[0]
        return month
    
    def fill_zeroes(self, bucket : float, month : list[float]) -> list[float] | float:
        """Fills the days with zero precipitation with some precipitation and empties the bucket.

        Args:
            bucket (float): The bucket with precipitation.
            month (list[float]): The month to find zeroes and fill.

        Returns:
            list[float] | float: The month with filled zeroes and the bucket with precipitation.
        """        
        instances = self.find_indices_of_condition(month, lambda x: x == 0)
        print(f"instances {instances}")
        i = 0
        while(bucket > 0):
            if(randrange(100) < weights().chance_of_continued_rain*50+month[instances[i]]*100):
                drain = 1 + uniform(0.0, 2.0)
                drain = round(drain, 2)
                month[instances[i]] = drain
                bucket -= drain
            if(i+1 > len(instances)-1):
                i = 0
            else:
                i += 1
        print(month)
        print(bucket)
        return month, bucket
                        
    def find_indices_of_condition(self, lst : list, condition) -> list[int]:
        """Finds the indices of a list that meet a condition.

        Args:
            lst (list): The list to find the indices of.
            condition (_type_): The condition to find the indices of.

        Returns:
            list[int]: The indices of the list that meet the condition.
        """        
        return [i for i, elem in enumerate(lst) if condition(elem)]
    
    def create_precipitation_pattern(self, precipitation_bucket : float, wettestday : float, days : int) -> list[float]:
        precipitation_list = []
        drain = wettestday
        i = 0
        while(precipitation_bucket > 0 and i < days and drain > 0.1):
            drain = uniform((2*drain)/7, wettestday-0.65*i)
            precipitation_bucket = precipitation_bucket - drain
            precipitation_list.append(round(drain, 2))
            i += 1
        return precipitation_list
    
    
def sinus_day(x : float) -> float:
    """Generates a sinus curve with a period of 24 hours.

    Args:
        x (float): The x value of the sinus curve.

    Returns:
        float: The y value of the sinus curve.
    """    
    A = 1
    w = (2*pi)/24
    phi = pi + 3*2*pi/24
    return A * sin(w * x + phi)

def sinus_temp_year(x : float) -> float:
    """Generates a sinus curve with a period of 12 months.
    The curve simulates the change in temperature over the year in Ålesund.

    Args:
        x (float): The x value of the sinus curve.

    Returns:
        float: The y value of the sinus curve.
    """       
    A = 12
    w = (2*pi)/12
    phi = pi - 4*2*pi/12
    return A * sin(w * x + phi)
        
test = percipitation_simulation()
print(test.generate_percipitation_month())
