FROM eclipse-temurin:17
WORKDIR /opt/app
COPY target/pdc-1.0-SNAPSHOT.jar pdc.jar
CMD ["java", "-jar", "locations.jar"]