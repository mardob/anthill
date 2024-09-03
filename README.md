<h1>ANTHILL</h1>


<h2>Intro</h2>

Anthill is simple unopinionated REST based ticketing system in Java. It is designed to be small enough to be locally deployable however, thanks to use of REST API it is also LAN and WAN accessible if needed. Flexibility in its useage is also kept by avoiding oppinionated features.
<br><br>

<h2>Technical details</h2>

Anthill uses Spring boot with embedded Tomcat application server and H2 database for data storage configured out of the box for persistance through locally stored file. It also includes very simplistic initial VUE.js frontend that is not yet finished. Application is secured using base auth. Rest API endpoits are documented by build in Swagger UI if configured as such.

<br>
<b>Currently it is not advised to use this system in serious production scenario and for sensitive data as it is not production ready.</b>



<br><br>
<h2>System requirements</h2>

Project requires Java and Maven to be installed and currently no official Docker image exists.



<br><br>
<h2>Project cconfiguration</h2>

Project configuration is done through: <br>
<i>src/main/resources/application.properties</i>

Configuration includes details like innitial Admin user password, enabling embedded Swagger or location of DB file(H2 file storage is used as form of DB to allow easy local deployment) just to name a few.



<br><br>
<h2>Running project</h2>

To run project execute _mvn clean install_ to generate executable jar file in target location. 
