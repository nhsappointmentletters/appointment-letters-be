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



#todo
1) Html Email

suresh.averineni@sureshs-MBP:~/Documents/ec2-details/ec2-ubuntu-aws-pem-latest (master) $ssh -i "ec2-ubuntu-sep-23.pem" ubuntu@ec2-52-15-93-251.us-east-2.compute.amazonaws.com

cp -i ~/Documents/ec2-details/ec2-ubuntu-aws-pem-latest/ec2-ubuntu-sep-23.pem  target/appointment-letters-be-latest.jar ubuntu@ec2-52-15-93-251.us-east-2.compute.amazonaws.com:~/webdriver/

nohup java -jar appointment-letters-service-latest.jar &
