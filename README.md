ANTHILL

/\/\
  \_\  _..._
  (" )(_..._)
   ^^  // \\


**Intro:**
Anthill is simple unopinionated REST based ticketing system in Java. It is designed to be small enough to be locally deployable however, thanks to use of REST API it is also LAN and WAN accessible if needed. Flexibility in its useage is also kept by avoiding oppinionated features.


**Technical details**
Anthill uses Spring boot with embedded Tomcat application server and H2 database for data storage configured out of the box for persistance through locally stored file. It also includes very simplistic initial VUE.js frontend that is not yet finished. Application is secured using base auth. Rest API endpoits are documented by build in Swagger UI if configured as such.


**Currently it is not advised to use this system in serious production scenario and for sensitive data as it is not production ready.**


**System requirements**
Project requires Java and Maven to be installed and currently no official Docker image exists.


**Project cconfiguration**
Project configuration is done through: 
_src/main/resources/application.properties_

Configuration includes details like innitial Admin user password, enabling embedded Swagger or location of DB file(H2 file storage is used as form of DB to allow easy local deployment) just to name a few.


**Running project**
To run project execute _mvn clean install_ to generate executable jar file in target location. 
