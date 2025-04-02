FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/pokeapi-0.0.1-SNAPSHOT.jar /app/pokeapi-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true","-jar", "pokeapi-0.0.1-SNAPSHOT.jar"]
