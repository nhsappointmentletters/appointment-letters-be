FROM openjdk:8u151-jdk-alpine3.7

ENV APP_USER java

RUN adduser -S $APP_USER

RUN mkdir -p /usr/local/apps/appointment-letters-service

# Add the service itself
ARG JAR_FILE
ADD ${JAR_FILE} /usr/local/apps/appointment-letters-service/app.jar
RUN sh -c 'touch /usr/local/apps/appointment-letters-service/app.jar'

RUN mkdir -p /var/log && chown $APP_USER /var/log

ADD src/test/resources/mappings.json /usr/local/apps/appointment-letters-service/

USER $APP_USER
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/usr/local/apps/appointment-letters-service/app.jar"]
EXPOSE 8000
EXPOSE 3306