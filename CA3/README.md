#  CA3: CI/CD Pipelines with Jenkins
**Author:** Nuno Cruz

**Date:** 11/06/2025  

**Course:** DevOps  

**Program:** SWitCH DEV  

**Institution:** ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents

- [Introduction](#introduction)
- [Setup & Prerequisites](#setup--prerequisites)
- [Part 1: CI/CD Pipeline for the Gradle Basic Demo](#part-1-cicd-pipeline-for-the-gradle-basic-demo)
    - [Jenkinsfile (excerpt)](#jenkinsfile-excerpt)
    - [What I Achieved](#what-i-achieved)
    - [How to Run It](#how-to-run-it)
- [Part 2: CI/CD Pipeline for React & Spring Data REST](#part-2-cicd-pipeline-for-react--spring-data-rest)
    - [Jenkinsfile](#jenkinsfile)
    - [Pipeline Stages](#pipeline-stages)
    - [Custom Jenkins Master Image](#custom-jenkins-master-image)
    - [Usage](#usage)
- [Conclusion](#conclusion)


# Introduction

In this hands-on activity, we created two fully functional CI/CD pipelines using Jenkins, streamlining each stage from code retrieval to final deployment.

1. **Gradle Basic Demo**  
   A lightweight Java project managed with Gradle. We set up automation for the following:
    - **Source Checkout** from GitHub
    - **Build & Assemble** via `./gradlew clean assemble`
    - **Unit Testing** with JUnit 5 (publishing XML results for Jenkins)
    - **Artifact Archiving** of the generated JAR

2. **React + Spring Data REST**  
   A complete full-stack application integrating a React frontend with a Spring Boot backend. Our CI/CD pipeline encompassed the following stages:    
   - **Source Checkout** from GitHub
       - **Dockerfile Generation** for the Spring Boot service
       - **Build & Assemble**:
           - Frontend bundling (Webpack)
           - Backend `bootJar` via Gradle
       - **Unit Testing** (JUnit 5)
       - **Javadoc Generation** (HTML reports published in Jenkins)
       - **Artifact Archiving** of the JAR
       - **Docker Image Build & Push** to Docker Hub

To facilitate Docker tasks within Jenkins, we deployed Jenkins using a custom Docker image that included the Docker CLI, and we securely set up Docker Hub credentials. This README outlines each stage of the pipeline along with the configuration steps needed for a fully automated workflow covering build, test, and deployment.

## Setup & Prerequisites

Before diving into the pipeline configuration, make sure you have the following components in place:

- **Git**
    - Necessary to clone your repositories.
    - Verify with:
      ```bash
      git --version
      ```

- **Docker**
    - Essential for building and running container images, including the Jenkins server itself.
    - Confirm by running:
      ```bash
      docker --version
      ```

- **Jenkins**
    - Jenkins was executed within a Docker container (using the `jenkins/jenkins:lts image`) that came with the Docker CLI already installed.
    - Once Jenkins is running at `http://localhost:8080`, proceed with the initial setup by unlocking it and installing the recommended plugins.

- **JDK 17 & Gradle Wrapper**
    - Both the Gradle example and the Spring Boot application require JDK 17 to run.
    - Every repository contains a Gradle wrapper, eliminating the need for a global Gradle installation.
    - To check locally, use:
      ```bash
      ./gradlew --version
      ```

- **Jenkins Plugins**
    - **Pipeline: Declarative** — for Jenkinsfile support
    - **Git** — to check out code
    - **HTML Publisher** — to publish Javadoc reports
    - **JUnit** — to display test results
    - **Docker Pipeline** — to run `docker.build()` and `docker.withRegistry()` inside your pipeline

- **Docker Hub Credentials**
    - In Jenkins, set up a “Username with password” credential using the ID: `dockerhub-creds-id`.
    - This credential is utilized by the pipeline to upload your Spring Boot Docker image.

After fulfilling these requirements, you can clone the repository and execute the Jenkins pipelines that automate checkout, building, testing, documentation, artifact storage, and Docker image publishing.

## Part 1: CI/CD Pipeline for the Gradle Basic Demo

In the initial exercise, we developed a Declarative Pipeline in Jenkins to build, test, and archive the **Gradle Basic Demo** (found at `CA1/part2/gradle_basic_demo`). Every step is controlled by a `Jenkinsfile` stored within the same repository.
### Jenkinsfile (excerpt)

```groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from repository'
                git branch: 'master', url: 'https://github.com/NunoNC/devops-24-25-1241919.git'
            }
        }
        stage('Assemble') {
            steps {
                dir('CA1/part2/gradle_basic_demo') {
                    echo 'Assembling...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }
        stage('Test') {
            steps {
                dir('CA1/part2/gradle_basic_demo') {
                    echo 'Running Tests...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {
                dir('CA1/part2/gradle_basic_demo') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
                }
            }
        }
    }
}
````

### What I Achieved

1. **Checkout**
    * Retrieved the `master` branch from GitHub into the Jenkins workspace.

2. **Assemble**
    * Set the Gradle wrapper to be executable.
    * Executed `./gradlew clean assemble` to compile the code, handle resources, and create the JAR package.

3. **Test**
    * Ran `./gradlew test` to compile and execute the JUnit tests.
    * Gathered XML test reports to allow Jenkins to determine the build status and display detailed test results.

4. **Archive**
    * Saved the produced `*.jar` file in Jenkins under **Build Artifacts**.
    * Activated fingerprinting to monitor future usage of this artifact.

### How to Run It

1. **Create a Pipeline Job**
    * In Jenkins: **New Item → Pipeline**
    * Under **Pipeline → Definition**, select **Pipeline script from SCM**.
    * Choose **Git**, branch `master`.
    * Set **Script Path** to `CA1/part2/gradle_basic_demo/Jenkinsfile`.

2. **Build**
    * Click **Build Now** while monitoring the console output for every stage.
    * After a successful run, the test results appeared in the **Tests** tab, and the JAR was available under **Last Successful Artifacts**.

[![Captura-de-ecr-2025-05-28-140604.png](https://i.postimg.cc/8CKKWKzK/Captura-de-ecr-2025-05-28-140604.png)](https://postimg.cc/gwhHbKR8)

This automated pipeline ensures that each commit to `master` is promptly validated, tested, and packaged—delivering quick feedback and dependable artifacts.

## Part 2: CI/CD Pipeline for React & Spring Data REST

In the second exercise, I expanded our CI/CD configuration to include the **React and Spring Data REST** application (found at `CA1/part3/react-and-spring-data-rest-basic`).
Additionally, I built a custom Jenkins Docker image to enable my pipeline to build and push Docker images.

### Jenkinsfile

```groovy
pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-credentials'
        DOCKER_IMAGE          = 'nunonelascruz/jenkins-image'
        DOCKER_REGISTRY       = 'https://index.docker.io/v1/'
        REPO_URL              = 'https://github.com/NunoNC/devops-24-25-1241919.git'
        DOCKER_HOST           = 'tcp://host.docker.internal:2375'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: env.REPO_URL
            }
        }

        stage('Create Dockerfile') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    writeFile file: 'Dockerfile', text: '''\
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/*.war app.war
EXPOSE 8080
ENTRYPOINT ["java","-war","app.war"]
'''
                }
            }
        }

        stage('Assemble') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    sh './gradlew test'
                    junit '**/build/test-results/**/*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    sh './gradlew javadoc'
                    publishHTML(target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'build/docs/javadoc',
                            reportFiles: 'index.html',
                            reportName: 'Javadoc'
                    ])
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    archiveArtifacts artifacts: 'build/libs/*.war', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('CA1/part3/react-and-spring-data-rest-basic') {
                    script {
                        def app = docker.build("${env.DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                        docker.withRegistry(env.DOCKER_REGISTRY, env.DOCKER_CREDENTIALS_ID) {
                            app.push()
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
````

### Pipeline Stages

1. **Checkout**
   Retrieved the master branch from GitHub into the Jenkins workspace.
2. **Create Dockerfile**
   Automatically create a Dockerfile that wraps the Spring Boot JAR into a container image.
3. **Assemble**
   Compile both the React frontend and Spring backend using `./gradlew clean assemble`.
4. **Test**
   Run unit tests and upload the JUnit XML reports to Jenkins.
5. **Javadoc**
   Produce HTML documentation and store it using the HTML Publisher plugin.
6. **Archive**
   Store the built JAR as a fingerprinted artifact.
7. **Build Docker Image**
    * Create the Docker image with the previously generated Dockerfile.
    * Upload the image to Docker Hub utilizing the saved credentials.

### Custom Jenkins Master Image

To enable Jenkins to execute Docker commands within the pipeline, we created a custom Jenkins container:
```dockerfile
FROM jenkins/jenkins:lts
USER root

RUN apt-get update \
 && apt-get install -y docker.io \
 && rm -rf /var/lib/apt/lists/*

USER jenkins
```

By mounting the host’s Docker socket into the container (`-v /var/run/docker.sock:/var/run/docker.sock`), the pipeline can build and push images directly.

### Usage

1. **Build & Run Jenkins**

   ```bash
   docker build -t jenkins-image .
   docker run -d --name jenkins \
     -p 8080:8080 -p 50000:50000 \
     -v jenkins_home:/var/jenkins_home \
     -v /var/run/docker.sock:/var/run/docker.sock \
     my-jenkins-with-docker
   ```
2. **Add Docker Hub Credentials**
    * Jenkins → Manage Jenkins → Manage Credentials → Global → Add → Username with password
    * ID: `docker-credentials`
   
3. **Create Pipeline Job**
    * New Item → Pipeline
    * Definition: Pipeline script sourced from SCM → Git → repository URL with branch set to `main`.
    * Script Path: `CA1/part3/react-and-spring-data-rest-basic/Jenkinsfile`

4. **Run & Verify**
    * Click **Build Now**, monitor every stage through the console output.
    * Verify that the Docker image is visible in your Docker Hub repository.


[![Captura-de-ecr-2025-05-28-173822.png](https://i.postimg.cc/8cWt2TB9/Captura-de-ecr-2025-05-28-173822.png)](https://postimg.cc/tnq3PQcN)
[![Captura-de-ecr-2025-05-28-174352.png](https://i.postimg.cc/SNFZWzmh/Captura-de-ecr-2025-05-28-174352.png)](https://postimg.cc/MX5bwHcP)

This configuration completely automates the build, testing, documentation, and containerization processes for our React + Spring application.

## Conclusion

This project strengthened the complete CI/CD workflow by creating two Jenkins pipelines—ranging from a basic Gradle build to a comprehensive React+Spring Data REST application with containerization. Important lessons learned are:

- **Pipeline as code**  
  Specifying each phase (checkout, build, test, documentation, archiving, Docker image) within a `Jenkinsfile` guarantees that every update follows a consistent automated process.

- **Automated quality gates**  
  Executing unit tests and uploading JUnit results helps detect regressions promptly, while publishing Javadoc ensures the documentation remains current.

- **Artifact management**  
  Archiving JAR files with fingerprints offers traceability, and generating Dockerfiles dynamically ensures container images remain consistent.

- **Docker integration**  
  Creating a custom Jenkins master equipped with Docker support allowed us to build and push images straight from the pipeline, simplifying the deployment process.

By formalizing these procedures, we removed manual steps, minimized human errors, and sped up our delivery process. This practical experience enhanced my understanding of Jenkins declarative pipelines and Docker workflows, setting the stage for more sophisticated deployments in cloud or Kubernetes setups.