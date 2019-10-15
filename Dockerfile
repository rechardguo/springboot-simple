FROM openjdk:8u222-jre
LABEL maitainer=rechard
LABEL description="simple springboot"
WORKDIR app
ADD $PWD/target/simplemvc-2.0.0.RELEASE.jar .
EXPOSE 9898
CMD nohup java -jar simplemvc-2.0.0.RELEASE.jar >> logs/out.log 2>&1 &