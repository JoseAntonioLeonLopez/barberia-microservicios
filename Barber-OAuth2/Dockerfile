FROM openjdk:17
VOLUME /tmp
EXPOSE 9100
ADD ./target/OAuthService-1.0.0.jar oauth-service.jar
ENTRYPOINT ["java", "-jar", "/oauth-service.jar"]
