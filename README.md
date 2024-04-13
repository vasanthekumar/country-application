# country-application
This project is part of our application. Implemented based on Spring boot microservices architecture.

This Micro Service expose below Rest APIs:
1. Fetch all available country. 
2. Get country details based on country name.

## Requirements:

 ~ Java 17 or higher
 ~ Maven 3.9.4
 ~ MySQL Server 5.7.43 

## Project setup and dependency:

1. Clone this repository to your local machine:
   https://github.com/vasanthekumar/country-application.git

    All microservice applications relies on:
   1) Config Server – config-server: for centralized configurations    
       a) Centralized configuration properties:  https://github.com/vasanthekumar/cloud-country-service-config-store.git

   2) Eureka server – discovery-service: Automated service registry and dynamic service discovery.
   
Configuration verification before stating the MS application:

1. Properties in cloud-country-service-config-store application configuration
    Path: cloud-country-service-config-store/country-service/demo-country.properties
           https://github.com/vasanthekumar/cloud-country-service-config-store/blob/main/country-service/demo-country.properties
       ~spring.datasource.url=jdbc:mysql://<host>:<port>/country_db?createDatabaseIfNotExist=true
       ~spring.datasource.password=<MySql password>
       ~spring.datasource.username=<MySql username>

2. bootstrap properties of the application country-application.
   Path: src/main/resources/bootstrap.properties
   Note: If you are not running the config server on port 8191 please configure below property in bootstrap properties file accordingly.
    ~spring.cloud.config.uri= http://localhost:<port> eg: http://localhost:8191

Make Sure before starting the microservice application (here country-service) Config server and Eureka server are up and running.

### Build and run.
1. Navigate to the project directory:
    cd country-application
2. Build the application using Maven
    mvn clean install
3. Run the application:
    java -jar country-0.0.1-SNAPSHOT.jar

http://localhost:8083/demo-country/v1/country/countries
http://localhost:8083/demo-country/v1/country/countries/Finland

