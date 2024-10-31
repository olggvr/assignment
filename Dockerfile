
FROM openjdk:17-jdk-alpine

COPY --chown=gradle:gradle .. /home/gradle/src

WORKDIR /home/gradle/src

COPY gradlew /home/gradle/src

RUN chmod +x ./gradlew

RUN ./gradlew build --no-daemon

RUN cp /build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
