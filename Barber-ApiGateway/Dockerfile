FROM openjdk:17
VOLUME /tmp
EXPOSE 8090
ADD ./target/ApiGatewayService-1.0.0.jar apigateway-service.jar
ENTRYPOINT ["java", "-jar", "/apigateway-service.jar"]
