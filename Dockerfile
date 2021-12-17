FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/giljob-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} giljob.jar
ENTRYPOINT ["java","-jar","/giljob.jar"]