# Forecast Website
## Introduction
Our project concept is an application that collects data such as temperature, precipitation, pressure, light and wind (strength and direction), and uses this data to showcase what type of weather is in the area onto a website. It also uses statistics to predict the future weather. 

Weather forecasts are important so people can plan their day around the weather. For example, if it is raining or snowing, it would be a good idea to wear rain-proof clothing. If it’s sunny and warm, then that would be a good day to spend outdoors gardening or hiking. By using a website, almost everyone can access it, and it can be updated frequently. 

## Protocols
Our web server works with the HTTP in order to establish a connection with the users, and the TCP is used to send packets reliably. 

We also use the Internet Protocol, which is used for relaying datagrams across network boundaries. Our website is hosted on a web server, and it uses IP addresses. The IPv4 address is 129.241.152.42, and the IPv6 address is 2001:700:300:600e:f816:3eff:fe87:883e. In addition, the application connects to the MQTT server with IP address 129.241.152.12, and TCP port number 1883.

The project uses the MQTT protocol to transmit the weather data from the sensor node to the visualization node. The sensor node is in this case the MQTT publisher and uses the Python version of the Paho-MQTT library. All sensor nodes that are, or will be, connected and published on the MQTT broker will need a unique ID and will publish its messages at the topic: ntnu/ankeret/c220/gruppe8/multisensor/<ID>. 

The MQTT listener in the visualization node listens to all topics below ntnu/ankeret/c220/gruppe8/ with the MQTT wildcard symbol #. Meaning the path the MQTT listener listens to is ntnu/ankeret/c220/gruppe8/#. The MQTT listener runs the Java version of the Paho-MQTT library for the simplicity of both sides using the same configurations and similar libraries. Since the MQTT listener subscribes to all topics under the “gruppe8” topic it will receive the data from all devices publishing under that topic.

At the current version of the sensor software, the ID of each sensor node needs to be changed in the python code itself, the default name of “0601holmes” is used. It is recommended by the developers to use a 4 digit number and a few letters after to create this unique format in the same format as in the earlier example.

As the MQTT protocol requires a lossless bidirectional connection, a TCP/IP connection is established between the broker and its publishers and clients.

An MQTT connection in itself is not especially secure, as it sends everything in plain text. This makes it easy for a middle man to see the messages sent. MQTT has support for TLS if the broker supports it, but in our case the broker does not support TLS. Since we can’t use TLS without needing to host our own MQTT broker, we decided to use RSA for security with padding,

As the sensor nodes need to connect to the MQTT broker, they will also need to connect to the internet to be able to send their messages to the broker. Most likely the sensor node is connected to a DHCP/NAT home router through either Wi-Fi as in our case with our sensor node or directly with an ethernet cable like CAT6. The home router assigns the sensor node a DHCP IP to use in the LAN. To establish a TCP/IP connection with the MQTT broker the sensor node can’t use its local IP address. NAT address camouflages the packet header, enabling the MQTT broker and sensor node to communicate.

As people from all over the world can connect to our website with an IP address, the packets will need to be able to be routed across the internet. As the internet is built up by multiple AS, BGP is used to route the packet across the AS between each end-point. This may be used between the sensor nodes and MQTT broker, and from the broker to the visualization nodes. As we don’t know the intra-AS protocols used in the AS traversal, we can only guess they use protocols like RIP and OSPF.

All devices on a network have a MAC address. As our sensor node (Raspberry Pi 4 Model B 4GB) is connected to a network it will also have a MAC address. As multiple other devices are also connected to the network, the sensor node is added to the devices’ ARP table.

## Approach, architecture and solution
![Project Structure Plan Diagram](https://github.com/Oransj/Group8NetworkProject/blob/main/documentation/1.%20sprint/Planning/Structure%20Plan.png)
The application has two nodes, sensor and visualization nodes. Sensor node is responsible for simulating weather data and the visualization node is responsible for storing, manipulating and presenting the simulated data. 

### Sensor Node
The sensor node is separated from the visualization node. The data simulation is implemented using Python and is running on a Raspberry Pi. As mentioned above the responsibility of the sensor node is to simulate weather data and send it to the visualization node. The data is simulated based on models which can be compared to the average weather in Ålesund. The simulation models were created by looking at the weather data from multiple different sources like yr.no and storm.no.

It sends the data through the MQTT server which is provided by NTNU to any subscriber of the topic. The data is simulated based on different metrics and it takes into account regular weather data and spike data which can occur occasionally. How often the sensor node publishes its data to the topic is depending on weights set in the configuration of the sensor node. But the base setting is to send every 15 minutes, which the visualization node expects it to be.

### Visualization Node
![Visualiaztion Node Explanation](https://mobidev.biz/wp-content/uploads/2021/07/spa-web-app-architecture-diagram.jpg) 

The picture above describes the architecture of the visualization node quite well. The visualization node of the weather application is a web-based application consisting of presentation, business and data layers.

The presentation layer is created with the basic web technologies like HyperText Markup Language (HTML), Cascading Style Sheets (CSS), JavaScript (JS) and Scalable Vector Graphics (SVG). HTML is used to create templates with empty containers. These containers are then styled with CSS. The layering of our application is implemented using flexbox and CSS Grid which makes the application quite responsive. The animated weather images are created with SVG. JS is used to fill the empty HTML containers with data from the database by sending HTTP requests to the backend of the application which sends back the requested information. In addition, JS is used to create graphical representation of the temperature and precipitation fluctuations. All the files of the presentation layer are located on src/main/resources/public.

In a nutshell, the back-end is a Spring Boot Application, which acts as an Application Programming Interface (API). It runs on Apache Tomcat Server and connects the presentation layer with the business and data layers. The application is hosted on the Ubuntu server provided by NTNU. We used Gradle, an automation build tool, to structure the files of the application, dependencies, compilation and packaging. All the source code of the back-end are located in src/main/java directory and are divided into different packages. 

The back-end consists of two layers Business Layer and Data Layer. The Business Layer consists of all the packages in the logic package. The most important package is the webserver. It takes care of starting a Tomcat Server, mapping the different endpoints to the right methods, querying the requested data from the database, manipulating the data and sending it back to the front-end. In addition it is responsible for receiving data from the MQTT client, saving it to the right table in the database, and predicting the future weather data based on it. The weather package is responsible for providing data deriving functionalities, which are used by the webserver package to derive different data based on the data in the database like calculating the average temperature in the last week, etc. The MQTT package is responsible for receiving data from the sensor node and forwarding it to the webserver package. Simply put, the webserver package connects all the back-end together and connects the back-end with the front-end. 

The Data Layer consists of all the packages in the data package. The databasehandler package in the data package is responsible for creating a connection with the database and providing the needed query methods. In this simple project we chose to use SQLite to store and manage the weather data. The Data Layer is dependent on the database file which is located on src/main/resources/database.

## Excellent features
When it comes to SQL and database we have a Java class that is responsible for all queries and writing to the database. 

In the database we store time in milliseconds, temperature in Celsius, precipitation in millimeters, air pressure in hectoPascals, wind speed in meters per second and wind direction in degrees. 

We have two database tables: “Weather” and “Spikes”. Weather stores all realistic readings from the sensor node while Spikes stores all the readings that have one or more spike(s) in their reading. JavaScript gets the readings from the sensor node and decides which table it should be stored in. We have two query methods, one that gets all the readings stored in a table and another query that gets all the readings between two points in time. We can select which table we want to read from when we use these methods.

To display the information we use a website. The first page contains the next few days, with icons and text summarizing the days. When clicking on one of the days, it links to the next web page which shows hour-by-hour data in a table. There are also graphs for temperature and precipitation. At the bottom of both pages is the information about how long ago since the page was updated, so the user knows if the data is old. 

To create a secure, encrypted connection, messages are encrypted using public-key cryptography with RSA. The public and private keypairs are 4096-bit, and generated at first use of the software. The keypairs are stored in the same folder as the program running, either the visualization node or the sensor node. The private key will be stored as a PKCS8 formatted PEM file encoding while the public key will use OpenSSH both for its formatting and file encoding. OAEP padding is used with SHA-256 hashing and MGF1 padding. Because of how RSA with OAEP works, the max size of a file we can send with 4096-bit RSA keys are 470 bytes (4096/8 - 42). We can increase this limit if we increase the size of the encryption keys.

## Result

  ![The frontpage on the website](https://github.com/Oransj/Group8NetworkProject/blob/main/documentation/3.%20sprint/Screenshot%202022-11-24%20144743.png?raw=true)
  
  ![A picture of multiple tables with weather data and a graph showing the temperature and another graph showing the precipitation](https://github.com/Oransj/Group8NetworkProject/blob/main/documentation/3.%20sprint/Website%20page%202.png?raw=true)
  
We have built a functional, responsive full-stack web application which is accessible to anyone in the world on the following address: http://129.241.152.42/. 

Our application starts by simulating data in the sensor node, which it generates every 15 minutes. The back-end then collects and stores the data in a database. We query the data from the database, and it’s sorted through algorithms using statistics to filter out spikes. We also use the data to predict future weather data, using Time Series and Autoregressive integrated moving average model. The program looks at the data and maps it to a corresponding weather type. It’s sent to the web server, which displays the data on the website. 

## Reflection & Improvements
We mostly were able to keep to the original plan, with minimal changes in which functions we needed. However, we could have made an even more detailed plan, to optimize the working process more. 

Certain group members could have made more issues on GitHub. Generally, we communicated well when we needed someone to make a new functionality to their part of the application. 

When it comes to improvements we could have added a field for location in the database and functionality for more sensors nodes to predict and give forecasts for new locations but also to improve the accuracy of our predictions as well as give a safety net by having multiple sensors in one area in case one sensor goes down or has spikes in its readings. Currently there is no support to add more sensors in a meaningful way. 

A potential improvement regarding security would be to use our own MQTT broker with TLS support, or using password protection on the private and public keys. While the public and private keys on the visualization node cannot normally be loaded without using Java, if someone uses Java and loads them they are able to read them like plain text.

We can improve weather forecasting techniques. Instead of using Time Series with ARIMA model we can use Window Sliding Technique and Minimum Euclidean Distance for more accuracy.

We can improve performance when loading the day’s weather. Now we are querying data from the DB for each hour of the day. We could query a list of hours instead of querying each hour individually.

If we knew more about how the data correlates to weather, we could better predict the future weather and more accurately map the current weather. For example, currently we define “thunder” quite loosely, and it could be improved by using more variables, and weighing them differently. 

On the website, it would be better if we didn’t just show when the page was last refreshed, but also when the data was last updated in the database. If the data is not updated in a long time, the “last updated” portion could become very misleading. 
  
  ## References
  Mobidev.biz (2021) *Spa web app architecture diagram* [Website]. Found at https://mobidev.biz/wp-content/uploads/2021/07/spa-web-app-architecture-diagram.jpg (Downloaded 24.11.2022)
