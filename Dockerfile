FROM openjdk:11-jre-slim
#LABEL maintainer="ensu6788@gmail.com"
ARG JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 6000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./uradom","-jar","/app.jar"]
#ENTRYPOINT ["java",\
##"-javaagent:/home/ubuntu/test1/backend-auth-service/elastic-apm-agent-1.26.0.jar", \
#"-javaagent:/home/ubuntu/backend-auth-service/elastic-apm-agent-1.26.0.jar", \
#"-Delastic.apm.service_name=order-service", \
#"-Delastic.apm.server_url=http://3.34.239.165:8200", \
#"-Delastic.apm.application_packages=com.bithumb.auth",\
#"-Djava.security.egd=file:/dev/./uradom",\
#"-jar",\
#"/app.jar"]