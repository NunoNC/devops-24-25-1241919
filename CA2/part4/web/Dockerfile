FROM tomcat:10.1-jdk17-temurin

# Install necessary tools and dependencies
RUN apt-get update -y && \
    apt-get install -y sudo nano git nodejs npm && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create build directory
RUN mkdir -p /tmp/build

# Set working directory
WORKDIR /tmp/build/

# Clone the repository
RUN git clone https://github.com/NunoNC/devops-24-25-1241919.git

# Move to the directory containing the Gradle wrapper
WORKDIR /tmp/build/devops-24-25-1241919/CA1/part3/react-and-spring-data-rest-basic

# Ensure gradlew is executable and build the application
RUN chmod +x gradlew && \
    ./gradlew clean build \
    && cp build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT-plain.war /usr/local/tomcat/webapps/

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
