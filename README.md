# Appointment letters
This project is to view patient appointment letters

## Description
This Project shows the list of Users which are stored in the MySql Database. Using the following endpoints, different operations can be achieved:
- `http://localhost:8000/user` - This creates a new user, require body in the request
- `http://localhost:8000/user/11` - This updates a user, require body in the request
- `http://localhost:8000/forgotPassword?username=saverineni` - This sends an email with password rest link
- `http://localhost:8000/user/{id}` - This returns user details

## Libraries used
- Spring Boot
- Spring MVC (Spring Web)
- Spring Data JPA with Hibernate
- MySql

## Tools used
- Git 2.10.0
- IntelliJ IDEA 2017.1
- MySql running locally

## Compilation Command
- `mvn clean install`

###Running
- `mvn spring-boot:run`

nohup java -jar appointment-letters-service-latest.jar &

#todo
1) Html Email
