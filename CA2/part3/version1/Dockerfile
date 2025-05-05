FROM gradle:jdk17 AS builder
WORKDIR /app

# Install git
RUN apt-get update && apt-get install -y git

# Clone the repository from Bitbucket
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo/src/master/ .

# Ensure gradlew has the correct permissions
RUN chmod +x gradlew && ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/basic_demo-0.1.0.jar ./basic_demo-0.1.0.jar

EXPOSE 59001
ENTRYPOINT ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]