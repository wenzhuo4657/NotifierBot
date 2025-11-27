# 构建阶段 - 使用 Maven 镜像
FROM maven:3.9-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# 运行阶段 - 使用轻量级 JRE
# todo 这里或许应该考虑将mq和redis一起部署了？否则太过简单了
FROM btgoose/jdk17:latest
MAINTAINER jacka7732
COPY target/app-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS app.jar"]


