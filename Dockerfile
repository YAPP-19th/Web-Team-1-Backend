FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/giljob-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]