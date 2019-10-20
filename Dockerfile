FROM daocloud.io/liushaoping/maven:latest as builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

FROM openjdk:8u222-jre
EXPOSE 80
CMD exec java -Dloader.path="/home/libs/" -jar /home/app.jar
COPY --from=builder /build/target/*.jar /home/app.jar
COPY --from=builder /build/target/libs /home/libs/