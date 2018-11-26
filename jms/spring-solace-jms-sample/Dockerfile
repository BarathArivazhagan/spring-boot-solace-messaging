FROM openjdk:8-jdk-alpine as final
ADD target/spring-solace-jms-sample-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
