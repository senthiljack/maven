FROM openjdk:11
EXPOSE 8085
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /var/www/html/app.jar
WORKDIR /var/www/html/
ENTRYPOINT ["java","-jar","/var/www/html/app.jar"]
