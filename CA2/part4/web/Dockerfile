FROM tomcat:10.1-jdk17-temurin

RUN apt-get update \
&& apt-get install -y \
git \
nano \
nodejs \
npm \
&& rm -rf /var/lib/apt/lists/*

WORKDIR /usr/src/app

RUN git clone https://github.com/NunoNC/devops-24-25-1241919.git .

WORKDIR /usr/src/app/CA1/part3/react-and-spring-data-rest-basic

RUN chmod +x gradlew \
    && ./gradlew bootWar \
    && cp build/libs/*.war /usr/local/tomcat/webapps/basic.war


EXPOSE 8080

CMD ["catalina.sh", "run"]
