FROM openjdk:17
VOLUME /tmp
EXPOSE 8888
ADD ./target/Barber-ConfigServer-1.0.0.jar config-server.jar
ENTRYPOINT ["java", "-jar", "/config-server.jar"]
