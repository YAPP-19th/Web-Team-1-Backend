FROM openjdk:11-jre-slim as production
EXPOSE 8080

#COPY --from=build /app/target/giljob-*.jar /giljob.jar
WORKDIR /usr/src/app
COPY . .

RUN echo $(ls)
RUN echo $(pwd)

CMD ["java", "-jar", "/giljob.jar"]
