FROM openjdk:11
EXPOSE 8085
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/app.jar
WORKDIR /opt/
ENTRYPOINT ["java","-jar","/opt/app.jar"]
