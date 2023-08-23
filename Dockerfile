FROM gradle:7.4.1 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 5000
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/vaale-bankpayment-ms-0.0.1-SNAPSHOT.jar /app/main.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/main.jar"]
