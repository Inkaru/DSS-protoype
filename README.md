# __DSS-protoype__

## **API**

### Users / Companies

#### Attributes :

- Name
- Location (adress + country)
- Size
- Field of activity
- Social network links
- Description
- Image ?

authentification
- email
- login
- password


#### Methods :

- login
- CRUDS (Create, Read, Update, Delete, Search)

### Projects

#### Attributes :

- Name
- Associated companies / users
- Description

#### Methods :

- CRUDS

### Communication

#### Methods :

- GET communication between two users

### Suggestions

#### Methods :

- GET projects suggestions for user
- GET partners suggestions for user

## **Project Setup**
1. Import project using the pom.xml in parent directory. In IntelliJ: File -> New -> Project from Existing Sources -> choose pom.xml
2. Go to Project Settings (Ctrl+Shift+Alt+S) and make sure that "Project SDK" is a Java 14 SDK (in IntelliJ you can autodownload OpenJDK 14) and "Project Langauge level" is set to 14, too
3. Create a Maven run configuration (Run -> Edit Configurations -> Click on the plus and choose Maven). Then set the Working directory to the DSS-prototype main directory and put the following into "command line": <code>spring-boot:run</code> (if you want debug info <code>spring-boot:run -X</code>)
4. Run the configuration, it will build angular, copy it in the resources folder of the backend and run the Spring Boot backend, which sets up a Tomcat server accessible with <code>localhost:8080/</code> by default
###Additional Info:
- If you only want to change things in the backend you don't have to let it rebuild Angular everytime (takes a while), so instead, you can use the Spring Boot run configuration that should be created automatically on project import (at least in IntelliJ)
- If there's any problem with dependencies that are not available, or the Maven run configuration fails for some reason, I recommend adding another Maven run configuration and running it: Put the project root folder as "Working directory" and in the "Command line"-field, put: <code>clean install -X</code>
