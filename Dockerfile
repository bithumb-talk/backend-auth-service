FROM openjdk:11-jre-slim
LABEL maintainer="ensu6788@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./build/libs/*.jar
ADD ${JAR_FILE} app.jar
EXPOSE 6000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./uradom","-jar","/app.jar"]