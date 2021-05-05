FROM adoptopenjdk/openjdk16:alpine
COPY /target/*bootified.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]