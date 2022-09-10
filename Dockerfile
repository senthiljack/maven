FROM openjdk:11
CMD mkdir -p /usr/src/myapp
COPY ./target/*.jar /usr/src/myapp
WORKDIR /usr/src/myapp
RUN java -jar *.jar

