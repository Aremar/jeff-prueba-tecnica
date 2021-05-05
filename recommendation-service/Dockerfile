FROM adoptopenjdk/openjdk16:alpine
COPY /target/*bootified.jar app.jar
EXPOSE 8081
ARG ENVIRONMENT
ENV SPRING_ENV=$ENVIRONMENT
CMD ["sh", "-c", "java -jar app.jar --spring.profiles.active=$SPRING_ENV"]