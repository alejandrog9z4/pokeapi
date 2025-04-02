FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/pokeapi-0.0.1-SNAPSHOT.jar /app/pokeapi-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "pokeapi-0.0.1-SNAPSHOT.jar"]
