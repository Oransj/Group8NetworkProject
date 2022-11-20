from calendar import monthrange
from datetime import datetime
import datetime
import time as t
from random import randrange, uniform
import paho.mqtt.client as mqtt
import json
from cryptography.hazmat.primitives import serialization as crypto_serialization
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.backends import default_backend as crypto_default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.x509 import load_pem_x509_certificate
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.primitives import hashes
import os

from numpy import pi, sin

    
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
        self.wind_speed = windpower
        self.winddir = winddir

    def __str__(self) -> str:
        return f"Temperature: {self.temperature}°C, Precipitation: {self.precipitation}mm, Lux: {self.lux}lx, Pascal: {self.pascal}Pa, Windpower: {self.wind_speed}m/s, Winddir: {self.winddir}"

class day:
    def __init__(self) -> None:
        """The constructor of the day class. The day class hold the weather data for each time slot of the day.
        """                
        self.weather_list = {}
        time = datetime.datetime(2020, 1, 1, 0, 0, 0)
        for _ in range(int(24*60/weights().minutes_update)):
            self.weather_list[time.time()] = None
            time = time + datetime.timedelta(minutes=weights().minutes_update)

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
        self.temp_weights = [0.7, -5.0, 5.0]
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
        #How often the sensor node should update the weather data in minutes.
        self.minutes_update = 15
        #Sleep time for each weather update check in seconds.
        self.sleep_time = 30
        
        self.avg_temp = 9.55
        self.temperature_months = [[12.0, -3.0], [11.5, -2.0], [16.5, -2.5], [17.0, -3.0],
                                   [19.0, 2.0], [24.0, 5.0], [26.5, 8.0], [21.0, 4.0],
                                   [23.5, 6.0], [19.5, 1.5], [15.1, -5.7], [12.5, -6.5]]
        
        self.wind_change = [-7, 7]
        self.gust_power = 10.0
        self.storm_power = 20.0
        self.chance_of_direction_change = 0.15
        #North, East, South, West
        self.avg_wind_speed_direction = [4, 4, 4, 4]
        
class percipitation_simulation:
    
    def __init__(self) -> None:
        self.rainy_days_in_month = []
        self.precipitation_today = []
    
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
            month, precipitation_bucket = self.fill_zeroes(precipitation_bucket, month)
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
        self.rainy_days_in_month = month
        return month
    
    def fill_zeroes(self, bucket : float, month : list[float]) -> list[float] | float:
        """Fills the days with zero precipitation with some precipitation and empties the bucket.

        Args:
            bucket (float): The bucket with precipitation.
            month (list[float]): The month to find zeroes and fill.

        Returns:
            list[float] | float: The month with filled zeroes and the bucket with precipitation.
        """        
        instances = self.find_indices_of_condition(month, lambda x: x == 0.0)
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
        """Creates a list of precipitation values for the days based upon the average precipitation per month and the wettest day of the month.

        Args:
            precipitation_bucket (float): The average precipitation per month.
            wettestday (float): The wettest day of the month.
            days (int): The number of days to create precipitation values for.

        Returns:
            list[float]: A list of precipitation values for the days.
        """        
        precipitation_list = []
        drain = wettestday
        i = 0
        while(precipitation_bucket > 0 and i < days and drain > 0.1):
            drain = uniform((2*drain)/7, wettestday-0.65*i)
            precipitation_bucket = precipitation_bucket - drain
            precipitation_list.append(round(drain, 2))
            i += 1
        return precipitation_list
    
    def generate_percipitation_today(self, bucket2) -> list[float]:
        #TODO: Make this actually realistic and not just plain random
        times = []
        phase = pi/12
        #bucket = self.rainy_days_in_month[datetime.datetime.now().day-1]
        bucket = bucket2
        chance = 0.3
        for _ in range(24):
            times.append(0)
        i = 0
        while(bucket > 0):
            if(times[i] < 0):
                chance = 0.75
            else:
                chance = 0.3
            if(randrange(100) < chance*100):
                times[i] = times[i] + 0.1
                bucket -= 0.1
            if(i+1 > 23):
                i = 0
            else:
                i += 1
        self.precipitation_today = times
        return times
        
class temperature_simulation:
    
    def __init__(self):
        print("Created")
    
    def simulate_temperature_today(self, add_on : float) -> float:
        """Simulates the temperature today at the current time. The add on temperature is chosen by the invoker of the function to simulate hotter or colder days.

        Args:
            add_on (float): The add on temperature.

        Returns:
            float: The temperature today at the current time.
        """        
        percentage_of_month = datetime.datetime.now().day/monthrange(datetime.datetime.now().year, datetime.datetime.now().month)[1]
        avg_today = self.sinus_temp_year(datetime.datetime.now().month-1 + percentage_of_month)
        avg_today = round(avg_today, 2)
        print(avg_today)
        time_now_conv = datetime.datetime.now().hour + datetime.datetime.now().minute/60
        sin_now = self.sinus_day(time_now_conv)
        temp_now = sin_now*avg_today+add_on
        temp_now = round(temp_now, 2)
        print(temp_now)
        return temp_now
        
        
    def sinus_day(self, x : float) -> float:
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

    def sinus_temp_year(self, x : float) -> float:
        """Generates a sinus curve with a period of 12 months.
        The curve simulates the change in temperature over the year in Ålesund.

        Args:
            x (float): The x value of the sinus curve.

        Returns:
            float: The y value of the sinus curve.
        """       
        A = 5.8
        w = (2*pi)/12
        phi = pi - 9.5*2*pi/12
        up = 8
        return A * sin(w * x + phi)+up

class mqtt_client:
    def __init__(self):
        self.client = mqtt.Client()
        self.topic = "ntnu/ankeret/c220/gruppe8/multisensor/"
        self.sensorID = "0601holmes"
        self.client.on_connect = self.on_connect
        self.client.connect("129.241.152.12", 1883, 60)
    
    def publish(self, payload : str):
        """Publishes a message to the MQTT broker.

        Args:
            topic (str): The topic to publish to.
            payload (str): The payload to publish.
        """   
        print("Publishing payload")     
        self.client.publish(self.topic + self.sensorID, self.sensorID + "::" + payload)
    
    def on_connect(self, client, userdata, flags, rc):
        """The callback for when the client receives a CONNACK response from the server.

        Args:
            client (_type_): The client instance for this callback.
            userdata (_type_): The private user data as set in Client() or userdata_set().
            flags (_type_): Response flags sent by the broker.
            rc (_type_): The connection result.
        """        
        print("Connected with result code "+str(rc))
        self.client.subscribe(self.topic + self.sensorID)
    
    def format_to_json(self, weather : weather) -> str:
        """Formats the data to a json string.

        Args:
            temperature (float): The temperature.
            precipitation (float): The precipitation.
            humidity (float): The humidity.

        Returns:
            str: The json string.
        """        
        t_ms = int(t.time()*1000)
        json_string = json.dumps({"Reading1": {"Time": {"ms" : t_ms}, "Temperature": {"celsius" : round(weather.temperature, 2)}, "Precipitation": {"mm" : round(weather.precipitation, 2)}, "Air_pressure": {"hPa" : weather.pascal}, "Light": {"lux" : round(weather.lux, 5)}, "Wind": { "W_speed": round(weather.wind_speed, 2), "W_direction": round(weather.winddir, 2)}}})
        print(json_string)
        return json_string

class pressure_simulation():
    
    def __init__(self):
        self.pressure = weights().pressure_extreme_points[1]
        
    def simulate_pressure(self, temperature_now : float, previous_temperature : float) -> int:
        """Simulates the pressure today at the current time.

        Args:
            temperature_now (float): The temperature at the current time.
            previous_temperature (float): The temperature at the last simulation.

        Returns:
            int: The pressure today at the current time.
        """        
        if(temperature_now > previous_temperature):
            self.pressure += uniform(1, 4.5)
        elif(temperature_now < previous_temperature):
            self.pressure -= uniform(1, 4.5)
        else:
            self.pressure += uniform(-2.65, 2.65)
        self.pressure = round(self.pressure, 2)
        
        return self.pressure

class wind_simulation():
    def __init__(self) -> None:
        self.wind_direction = 0
        self.change_in_wind_direction = 1
        self.average_wind_speed_direction = self.generate_avg_wind_speed_direction()
    
    def generate_avg_wind_speed_direction(self) -> list[float]:
        """Generates a list of average wind speeds and directions for each hour of the day.

        Returns:
            list[float]: A list of average wind speeds and directions for each hour of the day.
        """        
        avg = []
        for i in weights().avg_wind_speed_direction:
            max = i + uniform(-i + 5, i + 5)
            where = uniform(0, 2)
            avg_wind = i*where
            if(avg_wind > max):
                avg_wind = max * uniform(0.7, 1)
            avg_wind = round(avg_wind, 2)
            avg.append(avg_wind)
        return avg
          
    def simulate_wind(self, storm : bool) -> int | float:
        """Simulates the wind today at the current time.

        Args:
            storm (bool): If there is a storm or not.

        Returns:
            int | float: The wind today at the current time.
        """        
        self.calculate_wind_direction()
        wind_speed = self.calculate_wind_speed(storm)
        return self.wind_direction, wind_speed
        
    def calculate_wind_speed(self, storm : bool) -> float:
        """Calculates the wind speed today at the current time.

        Args:
            storm (bool): If there is a storm or not.

        Returns:
            float: The wind speed today at the current time.
        """        
        #TODO: Vindhastighet e avhengig av vind retning. Nordavind e mye sterkere enn andre vind retninger.
        if(not storm):
            if(self.wind_direction in range(0, 45) or self.wind_direction in range(315, 360)):
                direction = 0
            elif(self.wind_direction in range(45, 135)):
                direction = 1
            elif(self.wind_direction in range(135, 225)):
                direction = 2
            else:
                direction = 3
            wind_speed = self.average_wind_speed_direction[direction] + uniform(-2, 2)
            if(wind_speed < 0):
                wind_speed = 0
        else:
            weight = weights()
            max_storm = weight.storm_power + uniform(-5, 5)
            wind_speed = uniform(weight.gust_power, max_storm)
        
        return round(wind_speed, 2)
            
    def calculate_wind_direction(self) -> int:
        """Calculates the wind direction today at the current time.

        Returns:
            int: The wind direction today at the current time.
        """        
        weight = weights()
        if(self.change_in_wind_direction == 1):
            self.wind_direction -= randrange(1, weight.wind_change[1])
        elif(self.change_in_wind_direction == 0):
            self.wind_direction += randrange(weight.wind_change[0], weight.wind_change[1])
        elif(self.change_in_wind_direction == -1):
            self.wind_direction += randrange(weight.wind_change[0], -1)
        if(randrange(100) < weight.chance_of_direction_change*100):
            self.change_in_wind_direction = randrange(-1, 1)
        return self.wind_direction

class lux_simulation():
    
    def simulate_lux(self, precipitation : float) -> float:
        """Simulates the lux at the current time.

        Args:
            precipitation (float): The precipitation at the current time.

        Returns:
            float: The lux at the current time.
        """        
        time_now = datetime.datetime.now().time()
        min, max = self.min_max_light(time_now)
        print(min, max)
        if(precipitation > 0):
            current_time = time_now.hour + time_now.minute/60
            max = min
            max = max/(precipitation**(1/2))/2 + 1
            min = 160 * sin(current_time/12 * pi - 6 * pi/12) + 160.0001
        else:
            min = min + ((max-min)/uniform(0.9, 3))
        print(f"new min: {min}, new max: {max}")
        random_light = uniform(min, max)
        return round(random_light, 2)
        
    def min_max_light(self, time_now : t.time) -> float|float:
        """Calculates the min and max lux at the given time.

        Args:
            time_now (time): The time.

        Returns:
            float|float: The min and max lux.
        """        
        current_time = time_now.hour + time_now.minute/60
        max = 10000 * sin(current_time/12 * pi - 6 * pi/12) + 10001
        min = 500 * sin(current_time/12 * pi - 6 * pi/12) + 500.0001
        return min, max

def createKeys() -> rsa.RSAPrivateKey | rsa.RSAPublicKey:
    if(os.path.isfile('PRK.ppk') and os.path.isfile('PUK.pem')):
        print("Keys exists! Load em up!")
        with open('PUK.pem', 'rb') as public_read:
            public_key = serialization.load_ssh_public_key(public_read.read())
        with open('PRK.ppk', 'rb') as private_read:
            private_key = serialization.load_pem_private_key(private_read.read(), None)
    else:
        print("Keys dont exists! Create new keys!")
        key = rsa.generate_private_key(
            backend=crypto_default_backend(),
            public_exponent=65537,
            key_size=4096
        )

        private_key = key.private_bytes(
            crypto_serialization.Encoding.PEM,
            crypto_serialization.PrivateFormat.PKCS8,
            crypto_serialization.NoEncryption()
        )

        public_key = key.public_key().public_bytes(
            crypto_serialization.Encoding.OpenSSH,
            crypto_serialization.PublicFormat.OpenSSH
        )
        
        print("Keys generated")
        
        with open("PRK.ppk", 'wb') as pem_out:
            pem_out.write(private_key)
            
        with open("PUK.pem", 'wb') as pem_out:
            pem_out.write(public_key)
            
        print("Keys stored")
        
        private_key = key
        public_key = key.public_key()
    
    return private_key, public_key

server_public_key = None

class key_exchange():
    def __init__(self, public_key : bytes) -> None:
        self.topic = "ntnu/ankeret/c220/multisensor/gruppe8/visualiseringsnode/"
        self.sensorID = "0601holmes"
        self.public_key = public_key
        self.client = mqtt.Client(self.sensorID)
        self.connect_mqtt()
        self.client.connect("129.241.152.12", 1883, 60)
        self.subscribe()
        mqtt_client().publish(public_key.decode("utf-8"))
        print("Loop")
        self.client.loop_forever()
        
    def connect_mqtt(self) -> mqtt:
        def on_connect(client, userdata, flags, rc):
            if(rc == 0):
                print("Connected to broker")
            else:
                print("Connection failed")
        self.client.on_connect = on_connect
    
    def subscribe(self) -> None:
        print("Subscribed")
        def on_message(client, userdata, msg : mqtt.MQTTMessage):
            global server_public_key
            cert_bytes = msg.payload
            server_public_key = serialization.load_pem_public_key(cert_bytes)
            print("Server public key : " + server_public_key.public_bytes(crypto_serialization.Encoding.PEM, crypto_serialization.PublicFormat.SubjectPublicKeyInfo).decode("utf-8"))
            self.client.disconnect()
            print("Loop stop")
        self.client.subscribe(self.topic)
        self.client.on_message = on_message

def main():
    
    print("Starting key exchange")
    private_key, public_key = createKeys()
    public_bytes = public_key.public_bytes(
            crypto_serialization.Encoding.OpenSSH,
            crypto_serialization.PublicFormat.OpenSSH
        )
    key_exchange(public_bytes)
    global server_public_key
    public_key : rsa.RSAPublicKey = server_public_key
    print("Key exchange done")
    
    now = datetime.datetime.now()
    month = now.month
    day = now.day
    time = now.time()
    percipitation_sim = percipitation_simulation()
    month_percipitation = percipitation_sim.generate_percipitation_month()
    percipitation_today = month_percipitation[now.day-1]
    if(percipitation_today > 20):
        storm = True
    else:
        storm = False
    percipitation_today = percipitation_sim.generate_percipitation_today(percipitation_today)
    percipitation_now = percipitation_today[now.hour] * (weights().minutes_update/60)
    lux_sim = lux_simulation()
    lux_now = lux_sim.simulate_lux(percipitation_now)
    temp_sim = temperature_simulation()
    temp_now = temp_sim.simulate_temperature_today(calculate_temp_add_on(lux_now))
    previous_temp = temp_now -1
    pressure_sim = pressure_simulation()
    pressure_now = pressure_sim.simulate_pressure(temp_now, previous_temp)
    wind_sim = wind_simulation()
    wind_speed_now = wind_sim.calculate_wind_speed(storm)
    wind_direction_now = wind_sim.calculate_wind_direction()
    weather__now = weather(temp_now, percipitation_now, lux_now, pressure_now, wind_speed_now, wind_direction_now)
    mqtt_cli = mqtt_client()
    next_time = now + datetime.timedelta(minutes=weights().minutes_update)
    messageBytes = mqtt_cli.format_to_json(weather__now).encode("utf-8")
    
    messageBytes = public_key.encrypt(messageBytes,
                       padding.OAEP(mgf=padding.MGF1(algorithm=hashes.SHA256()),
                        algorithm=hashes.SHA256(),
                        label=None))
    
    mqtt_cli.publish(messageBytes.decode("utf-8", errors="ignore"))
    
    while(True):
        if(month != now.month):
            month = now.month
            percipitation_sim = percipitation_simulation()
            month_percipitation = percipitation_sim.generate_percipitation_month()
        if(day != now.day):
            day = now.day
            percipitation_today = month_percipitation[now.day-1]
            if(percipitation_today > 20):
                storm = True
            else:
                storm = False
            percipitation_today = percipitation_sim.generate_percipitation_today(percipitation_today)
        if(next_time <= now):
            print("Creating new data")
            time = now.time()
            percipitation_now = percipitation_today[now.hour]  * (weights().minutes_update/60)
            lux_sim = lux_simulation()
            lux_now = lux_sim.simulate_lux(percipitation_now)
            temp_sim = temperature_simulation()
            previous_temp = temp_now
            temp_now = temp_sim.simulate_temperature_today(calculate_temp_add_on(lux_now))
            pressure_now = pressure_sim.simulate_pressure(temp_now, previous_temp)
            wind_speed_now = wind_sim.calculate_wind_speed(storm)
            wind_direction_now = wind_sim.calculate_wind_direction()
            weather__now = weather(temp_now, percipitation_now, lux_now, pressure_now, wind_speed_now, wind_direction_now)
            weather__now = create_spikes(weather__now)
            print("Data ready to be published")
            mqtt_cli.publish(public_key.encrypt(mqtt_cli.format_to_json(weather__now)))
            print("Data published")
            next_time = datetime.datetime.now() + datetime.timedelta(minutes=weights().minutes_update)
            print(f"Next update at {next_time}")
            
        print("sleeping " + str(now))
        t.sleep(weights().sleep_time)
        now = datetime.datetime.now()
        
def create_spikes(check_weather : weather) -> weather:
    values = [check_weather.temperature, check_weather.precipitation, check_weather.lux, check_weather.pascal, check_weather.wind_speed, check_weather.winddir]
    if(randrange(100) <= 10):
        random_val = randrange(6)
        print(f"spike in {random_val}")
        values[random_val] = values[random_val]*randrange(10,20)
        print(values[random_val])
    return weather(values[0], values[1], values[2], values[3], values[4], values[5])
    
def calculate_temp_add_on(lux : float) -> float:
    if(lux < 1000):
        return uniform(-0.5, 0.5)
    if(lux >= 1000 and lux < 5000):
        return uniform(0.5, 2)
    if(lux >= 5000 and lux < 10000):
        return uniform(2, 4)
    if(lux >= 10000):
        return uniform(4, 6)

if __name__ == "__main__":
    main()