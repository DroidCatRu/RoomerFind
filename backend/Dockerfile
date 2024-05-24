FROM amazoncorretto:17
EXPOSE 8082:8082
RUN mkdir /app
COPY ./build/libs/backend-*.jar /app/backend.jar
ENTRYPOINT ["java","-jar","/app/backend.jar"]
