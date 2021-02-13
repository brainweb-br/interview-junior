FROM openjdk11
VOLUME /tmp
COPY target/spring-boot-docker-*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]