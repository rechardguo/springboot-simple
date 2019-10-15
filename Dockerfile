FROM openjdk:8u222-jre
LABEL maitainer=rechard
LABEL description="simple springboot"
WORKDIR app
RUN mkdir /logs
ADD $PWD/target/simplemvc-1.0.0.jar app.jar
EXPOSE 9898
ENTRYPOINT java -jar app:jar >> /logs/out.log 2>&1 &