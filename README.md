# CsvDataProcessor

#Complie <br /> 
1. First Pull the repository   <br />                                                                                                                                2.Open any IDE like STS,Intelijje <br />
3.Import Each Project in IDE as the existing maven Project <br />
4.It will install the dependency jar into m2 folder <br />
5 Run the project as  Spring Boot App <br />
6 Services Will run on the following port <br />

    >>1 Dicover Server :-8761 <br />
    >>2. Api Gateway :- 8765 <br />
    3 Cloud Config Server :- 8888 <br />
    4 Iot Tracking Service :- 8080 <br />



#Test <br>
1 Post :- localhost:8765/IOT-TRACKING-SERVICE/iot/event/v1 (By API Gateway) or  localhost:8080/iot/event/v1 (Direct Access)  <br>
2 Get  :- localhost:8765/IOT-TRACKING-SERVICE/iot/event/v1?ProductId=WG11155638 (By API Gateway) localhost:8080/iot/event/v1?ProductId=WG11155638 (Direct Access) <br>
3 Get  :- localhost:8765/IOT-TRACKING-SERVICE/iot/event/v2?ProductId=WG11155638 (By API Gateway) localhost:8080/iot/event/v2?ProductId=WG11155638 (Direct Access) (For CycleTracker Changes for Requirement 3)  <br>


Run Time Configuration Change  For  IOWA TRACKING SERVICE Can be done by this URL <br>
https://github.com/harshgarg0906/voiseconfiguration        <br>
iot-tracking-service.properties - For Dev Enviroment  <br>
iot-tracking-service-uat.properties For Uat Envirmoment <br>

spring.cloud.config.profile=uat Use this Property to Change the Enviroment in application.properties file

#Swagger Documentation <br>

1 http://localhost:8765/IOT-TRACKING-SERVICE/swagger-ui/index.html :- From Api Gateway <br>
2 http://localhost:8080/swagger-ui/index.html  :- Direct Access ti iot tracking service 

<img src="https://bkit.co/w_6322b4e60ed42.gif" />
