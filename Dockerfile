FROM openjdk:11
CMD mkdir -p /usr/src/myapp
WORKDIR /usr/src/myapp
COPY ./target/*.jar /usr/src/myapp
CMD java -jar *.jar

