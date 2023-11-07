FROM khipu/openjdk17-alpine
WORKDIR /app
COPY target/geoadapter-0.0.1-SNAPSHOT.jar /app
EXPOSE 8082
ENTRYPOINT ["java","-jar","geoadapter-0.0.1-SNAPSHOT.jar"]