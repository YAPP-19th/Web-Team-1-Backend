FROM openjdk:11-jre-slim as production
EXPOSE 8080

COPY --from=build /app/target/giljob-*.jar /giljob.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/giljob.jar"]
