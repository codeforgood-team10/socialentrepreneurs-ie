## J.P. Morgan Chase Code for good challenge London 2013 ##

This application is developed as part of the challenge for helping **Social Entrepreneurs of Ireland (SEI)** with a web application catering to its present needs.

###Web application developed###

This application is developed using AngularJS UI and Node JS for the backend. However, we also have provided the JAVA, Spring and Hibernate JPA with MySQL over the RESTful framework for use in productions as well.

### Deployment using Node JS backend ###
This is for faster turnaround in development time for realisation of the concept.

We just need the seicfg-web application to run a developer version. 

#####Use the following commands#####

npm install .
node app.js

### Deployment using RESTful APIs ###

This has two projects. seicfg-orm is a Spring MVC-Maven based project using hibernate JPA and MySQL. seicfg-web is the Spring MVC Maven based web application.

a. Install Maven3 and MySQL.

b. Import the complete project from https://github.com/codeforgood-team10/socialentrepreneurs-ie.git into workspace/directory of your choice (may or may not use IDE).

c. Search for ..seicfg-orm\src\main\resources\SEICFG_DUMP.sql and import the database into MySQL

d. Search for ..seicfg-orm\src\main\resources\seicfg-orm-context.xml file and modify the datasource password using the database created.

e. Open a command promp and navigate to the root folder of the imported project.

f. Execute "mvn clean install -DskipTests=true"

g. After the Build is successful, navigate to seicfg-web project and execute "mvn t7:run"

h. This will run a tomcat 7 server deployed with the SEI production application

i. Open a browser and access http://localhost:8080/seicfg-web/login.html and enter the application using client@abc.com and client

### Future Enhancements ###

As this project is developed keeping the very limited requirements in mind and projected as a proof of concept, there are enormous opportunites for this project to be scaled to a higher level.

a. Make proper use of all the RESTful APIs to build more robust functionalities.

b. Can make the reporting more meaningful.

c. Improve the security features.

d. Many more.