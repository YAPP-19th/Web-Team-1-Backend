FROM openjdk:11-jre-slim
ARG JAR_FILE=usr/local/bin/giljob-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} giljob.jar
ENTRYPOINT ["java", "-jar","/giljob.jar","--spring.config.location=file:/conf/application.yml"]