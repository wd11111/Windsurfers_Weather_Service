# Windsurfers_Weather_Service

Application uses the Weatherbit Forecast Api and exposes a REST API, where the argument is a day (yyyy-mm-dd date format) and return value is one of the following location:
Jastarnia (Poland), 
Bridgetown (Barbados), 
Fortaleza (Brazil), 
Wailea (Hawaii), 
Pissouri (Cyprus)
depending on which place offers better windsurfing conditions on that day in the 16 forecast day range. Apart from retruning the name of the location, the response should also include weather conditions (at least average temperature - Celcius, wind speed - m/s) for the location on that day.
The best location selection criteria are:
If the wind speed is not within <5; 18> (m/s) and the temperature is not in the range <3; 35> (°C), the location is not suitable for windserfing. However, if they are in these ranges, then the best location is determited by the highest value calculated from the following formula: (v * 3 + temp) where v is the wind speed in m/s on a given day, and temp is an average forecasted temperature for a given day in Celsius.

# How to run
- Open a terminal in the root directory of the project.
- Run the command mvn package to build the project and generate the executable jar file.
- Finally, run the command "java -jar .\target\weatherservice-0.0.1-SNAPSHOT.jar --http.client.config.key={your own api key from www.weatherbit.io}" to start the application.
