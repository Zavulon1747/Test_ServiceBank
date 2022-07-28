FROM openjdk:11-slim
LABEL maintainer="J.C. <@PrivetDimas>"
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]