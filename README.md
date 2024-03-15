# Book Store - Backend
Multimodule backend app build with gradle and mongoDB as a database.

### Dependencies
- gradle
- spring-boot-starter-data-mongodb
- spring-boot-starter-web
- spring-boot-starter-test
- spring-boot-starter-validation

## Prerequisites
To run the app locally you should have the following:
- Docker Desktop installed
- BookStoreFE project set up on your machine

## Local run (IntelliJ IDEA)
1. Navigate to docker-compose.yaml file. IntelliJ should recognize the file and display a play button next to "services" in the file.
   Clicking on it should set up a container with mongo instance to be used by the app.

   ![image](https://github.com/Kamillkaze/BookStore/assets/89124162/47bcf1d5-9c98-4911-9c6f-3cfc4a50e274)

2. Go to BookStoreApplication class and start it. It should now work correctly.
3. Start BookStoreFE project and go to http://localhost:4200/ to see the application running.
