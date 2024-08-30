FROM openjdk:17
VOLUME /tmp
ADD ./target/appointmentservice-0.0.1-SNAPSHOT.jar appointment-service.jar
ENTRYPOINT ["java", "-jar", "/appointment-service.jar"]
