#  CA2: Virtualization with Vagrant: Technical Report
**Author:** Nuno Cruz

**Date:** 01/04/2025

**Course:** DevOps

**Program:** SWitCH DEV

**Institution:** ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents

- [Part 1: Virtualization with Vagrant: Technical Report](#part-1-virtualization-with-vagrant-technical-report)
  - [Introduction](#introduction)
  - [Create a VM](#create-a-vm)
  - [Configure Network and Services](#configure-network-and-services)
  - [Clone the Repository](#clone-the-repository)
  - [Set Up Development Environment](#set-up-development-environment)
  - [Execute the spring boot tutorial basic project](#execute-the-spring-boot-tutorial-basic-project)
  - [Execute the gradle_basic_demo project - Part 1](#execute-the-gradle_basic_demo-project---part-1)
  - [Execute the gradle_basic_demo project - Part 2](#execute-the-gradle_basic_demo-project---part-2)
  - [Conclusion](#conclusion)
- [Part2: Virtualization with Vagrant: Technical Report](#part-2-virtualization-with-vagrant-technical-reportt)
  - [Introduction](#introduction)
  - [Environment Setup](#environment-setup)
  - [Setup Base Project](#setup-base-project)
  - [Vagrantfile](#vagrantfile)
  - [Connecting Spring Boot to H2 Database](#connecting-spring-boot-to-h2-database)
  - [Running the Project](#running-the-project)
  - [Vagrant commands](#vagrant-commands)
  - [Alternative Solution](#alternative-solution)
  - [Conclusion](#conclusion)
- [Part3: Containers with Docker: Technical Report](#part-3-containers-with-docker-technical-reportt)
  - [Introduction](#introduction)
  - [Environment Setup](#environment-setup)
  - [Dockerfile - version 1](#dockerfile---version-1)
  - [Dockerfile - version 2](#dockerfile---version-2)
  - [Conclusion](#conclusion)
- [Part4: Containers with Docker: Technical Report](#part-4-containers-with-docker-technical-reportt)
  - [Introduction](#introduction)
  - [DB Dockerfile](#db-dockerfile)
  - [Web Dockerfile](#web-dockerfile)
  - [Docker Compose](#docker-compose)
  - [Tag and Push Images](#tag-and-push-images)
  - [Working with volumes](#working-with-volumes)
  - [Alternative solution](#alternative-solution)
  - [Conclusion](#conclusion)

## Introduction

This technical report outlines the procedures and results of **Class Assignment 2**, which focused on exploring virtualization techniques using VirtualBox as part of the DevOps course. The primary goal was to develop practical skills in creating and managing virtual environments, which play a key role in contemporary software development and IT operations.

In this report, I describe the process of creating and configuring a virtual machine, setting up the development environment, and carrying out several projects within that setup.

## Create a VM

- Initially I downloaded VirtualBox from https://www.virtualbox.org/wiki/Downloads and proceeded to install it.
- I opened VirtualBox, clicked `New`, named the VM, and selected the OS type and version I needed.
- I assigned sufficient memory and set up a virtual hard disk to support the VM’s needs.
- In the storage settings, I mounted the OS ISO file to the virtual CD/DVD drive, then started the VM and installed the OS by following the prompts.
- Once the OS was installed, I added the VirtualBox Guest Additions for better performance and integration.
- I set up the VM for Ubuntu 18.04 by linking it to the minimal CD ISO. I allocated 2048 MB of RAM for performance. For networking, I used NAT on Adapter 1 for internet access and set Adapter 2 to Host-only (vboxnet0) for isolated communication with the host.

## Configure Network and Services

After setting up the VM, I configured the network and key services to improve its functionality and accessibility.

- I accessed the Host Network Manager by going to `File` -> `Host Network Manager` in the VirtualBox main menu.
- I clicked the `Create` button to add a new Host-only network, which allowed me to assign a name to the network in the VM’s network settings.
- After setting up the Host-only Adapter (vboxnet0), I checked the IP range, which was `192.168.56.1/24`. I assigned `192.168.56.5` to the VM's second adapter, ensuring it was within the subnet.
- After booting the VM, I updated the package repositories with the command `sudo apt update`.
- I installed the network tools package using `sudo apt install net-tools` to assist with network configuration.
- To assign the selected IP address, I edited the network configuration file by running `sudo nano /etc/netplan/01-netcfg.yaml` and updated the file with the appropriate network settings.

```yaml
network:
  version: 2
  renderer: networkd
  ethernets:
    enp0s3:
      dhcp4: yes
    enp0s8:
      addresses:
        - 192.168.56.5/24
```

- After making the edits, I applied the changes using `sudo netplan apply`.
- To enable remote management of the VM, I installed the OpenSSH server using `sudo apt install openssh-server`. I then edited `/etc/ssh/sshd_config` to uncomment PasswordAuthentication yes and restarted the SSH service with `sudo service ssh restart`.
- I set up an FTP server for file transfers by installing vsftpd with `sudo apt install vsftpd`. I enabled write access by editing `/etc/vsftpd.conf` to uncomment `write_enable=YES` and then restarted the service with `sudo service vsftpd restart`.

## Clone the Repository

To clone my repository into the VM, I first set up secure SSH access between the VM and my GitHub repository. Here’s how I did it:

- I generated a new SSH key pair in the VM to enable secure communication with GitHub. I ran the following command in the terminal:

```bash
ssh-keygen -t ed25519 -C "email@example.com"
```

- To add the newly created SSH key to my GitHub account, I displayed the key content in the terminal with the following command:

```bash
cat ~/.ssh/id_ed25519.pub
```

- I then logged into my GitHub account, went to `Settings` -> `SSH and GPG keys`, and clicked on "New SSH key." I pasted the key into the provided field and saved it, enabling secure authentication between my VM and GitHub.
- With SSH set up, I cloned my repository into the desired directory within the VM using the following command:3

```bash
git clone git@github.com:userName/repositoryName.git
```

## Set Up Development Environment

After setting up the virtual machine and confirming its network configuration, I proceeded to install the necessary tools for the projects.

- I started by updating and upgrading the installed packages to ensure all software on the VM was up-to-date. I used the following commands:

```bash
sudo apt update
sudo apt upgrade
```

- Next, I installed Git for version control and source code management using the following command:

```bash
sudo apt install git
```

- I then installed both the JDK (Java Development Kit) and JRE (Java Runtime Environment) for Java-based projects using the following command:

```bash
sudo apt install openjdk-17-jdk openjdk-17-jre
```

- Next, I installed Maven for project dependency management and building Java projects using the following command:

```bash
sudo apt install maven
```

- Installing Gradle required a few additional steps due to its packaging, which I followed to complete the installation.

```bash
wget https://services.gradle.org/distributions/gradle-8.12-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.12-bin.zip
```

- To ensure Gradle could be executed from any location in the terminal, I added its bin directory to the system PATH by modifying the `.bashrc` file.

```bash
echo "export GRADLE_HOME=/opt/gradle/gradle-8.12" >> ~/.bashrc
echo "export PATH=\$GRADLE_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc
```

I also had to change my build.gradle, as it was showing an error while running it.
These installations provided the virtual machine with the essential tools to build and manage Java applications effectively, enabling me to move forward with executing and testing the projects.
To verify that all tools were installed correctly and functioning as expected, I ran the following commands to check their versions:

```bash
git --version
java --version
mvn --version
gradle --version
```

## Execute the spring boot tutorial basic project

In this part, I executed the Spring Boot tutorial basic project, which was part of the previous assignments. The objective was to build and run the project within the virtual machine environment I had set up earlier.

1. I moved to the `basic` directory, where the project files are located. This directory contains the setup for the Spring Boot application.
2. To start the Spring Boot application, I ran the following command in the terminal within the project directory:

```bash
./mvnw spring-boot:run
```

3. To ensure the application was accessible from external devices, such as the host machine or other devices on the same network, I used the VM's IP address. I determined the IP address by running the `ifconfig` command. Here is the URL I used to access the application:

```
http://192.168.56.5:8080/
```

The application loaded as expected, showing the correct content, which confirmed that the backend was working properly and the Spring Boot framework was effectively serving the content. I captured a screenshot of the application's landing page in the browser to document the successful setup and execution.

[![Captura-de-ecr-2025-03-20-143000.png](https://i.postimg.cc/Kvdg7Nbv/Captura-de-ecr-2025-03-20-143000.png)](https://postimg.cc/tYNTpF7L)

## Execute the gradle_basic_demo project - Part 1

Here, I outline the process of building and running the **gradle_basic_demo project**. This project needed to be executed in both the virtual machine and the host machine environments.

1. I went to the `gradle_basic_demo` directory in the virtual machine. To build the project, I entered the following command:

```bash
./gradlew build
```

2. Since the virtual machine was configured with Ubuntu Server, which doesn't have a desktop environment, running GUI applications like the project’s chat client on the VM wasn’t possible. Before starting the Server/Client I had to run `gradle wrapper`. Then to resolve this, I opened a terminal on my host machine, navigated to the `gradle_basic_demo` directory (which I had cloned there as well), and ran the client component using the command below. This allowed the client on the host machine to connect to the server running on the VM by specifying the VM's IP address and port number:

```bash
./gradlew runClient --args="192.168.56.5 59001"
```

I was able to open two chat windows on the host machine, showcasing the client-server communication. The chat application operated correctly, with messages being transmitted and received as intended.
I captured a screenshot of this interaction, highlighting the active connection and data exchange enabled by the network configuration.

[![Captura-de-ecr-2025-04-08-143913.png](https://i.postimg.cc/vmVq2y6c/Captura-de-ecr-2025-04-08-143913.png)](https://postimg.cc/MczmXh5S)

## Execute the gradle_basic_demo project - Part 2

In this section of the assignment, I concentrated on building and running another component of the **gradle_basic_demo** project within the virtual machine.

1. I went to the `basic` folder inside the `gradle_basic_demo` directory.
2. I ran the following commands to build the application and start the Spring Boot server, making the application accessible through the web.

```bash
./gradlew build
./gradlew bootRun
```

3. Once the server was up and running, I accessed the application by entering the following URL into a web browser. This URL took me to the landing page of the Spring Boot application hosted on the virtual machine, confirming that the server was operational and capable of handling client requests over the network.

```bash
http://192.168.56.5:8080/
```

## Conclusion
This technical report details the setup and execution of a virtual environment using VirtualBox for **Class Assignment 2**. The tasks involved creating a virtual machine, configuring its network and services, and installing the development tools required to run software projects.

The virtualization work provided valuable insights into the configuration and management of virtual machines in a DevOps context. Successfully running the Spring Boot tutorial and gradle_basic_demo projects within this environment showcased the ability to effectively simulate real-world software deployment and operational scenarios.

Key outcomes from this assignment include a deeper understanding of network configuration in virtualized environments and the complexities of setting up software on virtual platforms. Challenges such as configuring network interfaces and ensuring seamless communication between the host and guest machines were addressed, leading to a more thorough understanding of virtualization technologies.

Overall, the experiences gained from this assignment are crucial for building the skills needed to manage complex environments. These insights will be valuable in my continued education and professional growth in the field of DevOps.

## Introduction

This report outlines the steps and results of **Class Assignment 2 - Part 2**, which focused on using Vagrant for virtualization. The task involved setting up a virtual machine to run a Spring Boot application connected to an H2 database. I describe how the Vagrant environment was configured, how the application was linked to the database, and how the system was run successfully. I also explored an alternative setup using VMware with Vagrant, comparing it to VirtualBox and noting the main differences between the two providers.

## Environment Setup

To create the virtual environment with Vagrant, I carried out the following main steps:

**Download Vagrant**:
I accessed the [official Vagrant website](https://www.vagrantup.com/downloads) to download the version compatible with my operating system.

**Install Vagrant**:
After downloading the installer from the Vagrant website, I executed it and completed the setup by following the on-screen instructions, which were simple and easy to follow.

**Verify Installation**:
To verify that Vagrant was installed properly, I opened a terminal (or command prompt) and ran the command:

 ```bash
 vagrant --version
 ```

Seeing the Vagrant version number confirmed that the installation had completed successfully.

**Update .gitignore**:
To maintain a clean repository, I added the following lines to the `.gitignore` file to prevent tracking the `Vagrant` directory and any `.war` files:

```bash
.vagrant/
*.war
```

## Setup Base Project

**Clone the Base Project**:
I began by cloning the base Vagrant project to obtain the required configuration files. I ran the following command:

```bash
git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/
```

This step cloned the repository, which includes the initial setup and configurations required for our project.

**Copy the Vagrantfile**:
After cloning the base project, I copied the `Vagrantfile` from the cloned repository into my project's specific directory. 
Here's how I did it:


```bash
cp -r vagrant-multi-spring-tut-demo/Vagrantfile C/Users/Utilizador/Desktop/DevOps/devops-24-25-1241919/CA2/part2
```

By doing this, I made sure that the necessary `Vagrantfile` configuration was in place within my project directory, enabling the next steps in the setup process.

## Vagrantfile

The `Vagrantfile` serves as the configuration file that outlines the virtual machine (VM) settings and provisions. Once the initial `Vagrantfile` was set up, I made the following essential changes to adapt it to our project needs:

1. **Changed the Repository URL**: I modified the repository URL in the `Vagrantfile` to direct it to my specific project.
2. **Changed the Path**: I updated the path in the `Vagrantfile` to reference the correct directory.
3. **Added bootRun Command**: I included the `./gradlew bootRun` command to launch the Spring Boot application.
4. **Updated the Java Version**: I updated the Java version in the setup to OpenJDK 17.

Here's the updated Vagrantfile:

```ruby
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
# for information about machine names on private network
Vagrant.configure("2") do |config|
  config.ssh.forward_agent = true
  config.vm.box = "ubuntu/bionic64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.33.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setiing H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

    # Configurations specific to the webserver VM
      config.vm.define "web" do |web|
        web.vm.hostname = "web"
        web.vm.network "private_network", ip: "192.168.56.10"
        web.vm.network "forwarded_port", guest: 8080, host: 8080
        # Sync the host's SSH key folder into the VM (read-only)
        web.vm.synced_folder "~/.ssh", "/home/vagrant/.ssh_host", type: "virtualbox"

     # We set more ram memmory for this VM
        web.vm.provider "virtualbox" do |v|
          v.memory = 1024
        end

        # Provisioning script for the web VM
        web.vm.provision "shell", privileged: false, inline: <<-SHELL
          # Copy the SSH private key and set correct permissions
          cp /home/vagrant/.ssh_host/id_ed25519 ~/.ssh/id_ed25519
          chmod 600 ~/.ssh/id_ed25519

          # Trust GitHub host to avoid SSH warnings
          ssh-keyscan github.com >> ~/.ssh/known_hosts

          # Clone the repository only if it doesn't exist
          if [ ! -d "devops-24-25-1241911" ]; then
            git clone git@github.com:NunoNC/devops-24-25-1241919.git
          fi

          cd devops-24-25-1241919/CA1/part3/react-and-spring-data-rest-basic
          chmod u+x gradlew
          ./gradlew clean build
          ./gradlew bootRun

          # Deploy the WAR to Tomcat
          sudo cp ./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar /var/lib/tomcat9/webapps
        SHELL
 end
end
```

## Connecting Spring Boot to H2 Database

To link the Spring Boot application with the H2 database, I implemented the following modifications in the `react-and-spring-data-rest-basic` project:

**Modify application.properties**:
I included the required properties in `src/main/resources/application.properties` to establish a connection to the H2 database:

```properties
spring.data.rest.base-path=/api
server.servlet.context-path=/basic-0.0.1-SNAPSHOT
spring.datasource.url=jdbc:h2:tcp://192.168.56.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
spring.jpa.hibernate.ddl-auto=create
```

**Update React App.js**:
The `src/App.js` file required updates to align with the new backend path:

```javascript
// client({method: 'GET', path: '/basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {
```

## Running the Project

Before running the project, I verified that VirtualBox was initialized and that the repository to be cloned was public. Afterward, I navigated to the project directory and ran the following command:

```bash
vagrant up
```

This command initiated the VMs and provisioned them based on the configurations defined in the Vagrantfile.

Once the VMs were running, I visited http://localhost:8080/basic-0.0.1-SNAPSHOT/ in my web browser to verify that the Spring Boot application was functioning properly. Below is a screenshot of the outcome:

[![Captura-de-ecr-2025-05-19-111818.png](https://i.postimg.cc/YSnK6f6v/Captura-de-ecr-2025-05-19-111818.png)](https://postimg.cc/NyHzYXPt)

I also accessed the H2 console by navigating to http://localhost:8082/ and connected to the H2 database using the JDBC URL `jdbc:h2:tcp://192.168.56.11:9092/./jpadb`. Below is a screenshot of the H2 Login page, where I entered the connection details:

[![Captura-de-ecr-2025-05-19-112217.png](https://i.postimg.cc/3x8ct0Jm/Captura-de-ecr-2025-05-19-112217.png)](https://postimg.cc/tYckTT64)

After connecting to the H2 database, I was able to view the tables and data stored within it. This configuration enabled smooth interaction between the Spring Boot application and the H2 database. Below is a screenshot of the H2 console displaying the `EMPLOYEE` database table:

[![Captura-de-ecr-2025-05-19-112122.png](https://i.postimg.cc/J4WgBdfT/Captura-de-ecr-2025-05-19-112122.png)](https://postimg.cc/LhNvcv6f)

These steps verified that the Spring Boot application was operating correctly and could interact with the H2 database.

## Vagrant commands

Below are the key Vagrant commands I used throughout the setup and troubleshooting process:

| Command             | Description                                                                             |
|---------------------|-----------------------------------------------------------------------------------------|
| `vagrant init`      | Initializes a new Vagrant environment by creating a Vagrantfile.                        |
| `vagrant up`        | Starts and provisions the Vagrant environment as defined in the Vagrantfile.            |
| `vagrant halt`      | Stops the Vagrant machine, effectively powering it down.                                |
| `vagrant reload`    | Restarts the Vagrant machine, reloading the Vagrantfile if it has changed.              |
| `vagrant destroy`   | Stops and destroys all resources that were created during the machine creation process. |
| `vagrant ssh`       | Connects to the machine via SSH.                                                        |
| `vagrant status`    | Shows the current status of the Vagrant machine.                                        |
| `vagrant suspend`   | Suspends the machine, saving its current running state.                                 |
| `vagrant resume`    | Resumes a suspended Vagrant machine.                                                    |
| `vagrant provision` | Provisions the Vagrant machine based on the configuration specified in the Vagrantfile. |

These commands were essential for effectively managing and navigating the virtualized environments.

## Alternative Solution

In this section, I examine VMware as an alternative virtualization tool to VirtualBox. Below, you'll find a detailed comparison between VMware and VirtualBox, along with instructions on using VMware with Vagrant to meet the objectives of this assignment.

**Comparison of VMware and VirtualBox**

- **VirtualBox:**
  - **Overview**: A free, open-source hypervisor developed by Oracle, known for its user-friendly interface and compatibility with multiple operating systems.
  - **Pros**:
    - Open-source and available at no cost.
    - Intuitive graphical user interface (GUI).
    - Compatible with a wide range of guest operating systems.
  - **Cons**:
    - Lacks some advanced features.
    - Performance may degrade with 3D graphics and larger virtual machines.

- **VMware (Workstation and Fusion):**
  - **Overview**: A high-performance, feature-rich solution from VMware, designed for professional use.
  - **Pros**:
    - Excellent performance and reliability.
    - Powerful features such as snapshots, cloning, and shared virtual machines.
    - Seamlessly integrates with other VMware enterprise solutions.
  - **Cons**:
    - Costly, with a license required after the trial period.
    - A higher learning curve for utilizing enterprise-level features.

**Using VMware with Vagrant**

Integrating VMware with Vagrant requires the following steps:

1. **Install the Vagrant VMware Utility**. This step is essential for Vagrant to control VMware virtual machines.

```bash
# Example for installing on Linux
wget https://releases.hashicorp.com/vagrant-VMware-utility/1.0.14/vagrant-VMware-utility_1.0.14_x86_64.deb
sudo dpkg -i vagrant-VMware-utility_1.0.14_x86_64.deb
```

2. **Install the Vagrant Plugin for VMware**. This plugin enables Vagrant to communicate with VMware.

```bash
vagrant plugin install vagrant-VMware-desktop
```

3. **Configure the Vagrantfile**. Modify your Vagrantfile to set VMware as the provider.

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.provider "VMware_desktop" do |v|
    v.vmx["memsize"] = "1024"
    v.vmx["numvcpus"] = "2"
  end
end
```

Using VMware with Vagrant offers a powerful virtualization setup that strengthens our development environment. It delivers improved performance and access to advanced features, making it especially advantageous for larger or more complex projects.

This alternative approach supports our goal of strengthening the virtualization setup, improving the development workflow, and simplifying the transition to production-like environments.

## Conclusion

This report detailed the setup and execution of **Class Assignment 2 – Part 2**, which centered on virtualization using Vagrant. Through configuring the Vagrant environment, linking a Spring Boot application to an H2 database, and successfully running the project, I demonstrated how virtualization concepts apply in practice. Additionally, I examined VMware as an alternative to VirtualBox, outlining its advantages for more advanced virtualization requirements.

## Introduction

The main objective of this assignment is to gain hands-on experience with Docker by creating Docker images and running containers for a chat application. Originally developed in `CA1` and hosted in a Bitbucket repository, the chat server is now being containerized to ensure consistent behavior across various environments. 
The assignment is structured into two versions:

- Compiling the chat server directly within the `Dockerfile`.
- In the second approach, the chat server is built on the host machine, and the resulting JAR file is then copied into the Docker image using the `Dockerfile`. This report outlines the steps taken to complete both versions, covering environment setup, `Dockerfile` contents, and the procedures for building and running the Docker images.

## Environment Setup

To begin working with Docker and the chat server from `CA2`, I first verified that Docker was installed on my system. In addition, I needed access to the chat server repository hosted on Bitbucket, which contains the basic Gradle-based application developed during `CA1`. 
The repository can be cloned using the following command:

```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git
```

## Dockerfile - version 1

Below are the steps I followed to configure and run the chat server within a Docker container:

1. I confirmed that Docker was actively running on my system.

2. I moved to the directory containing the Dockerfile.

3. Below is the content of the Dockerfile I utilized:

```dockerfile
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
```

The Dockerfile is a script that defines a series of instructions for building a Docker image for the chat server. It begins by using a Gradle image with JDK 17 to clone and build the project from a Bitbucket repository. After the build process is completed, it switches to a lightweight JRE image to create a more compact, production-ready container. The generated JAR file is copied from the build stage to the final image, and the server is set to run on port 59001.

4. I created the Docker image by running the following command:

```bash
docker build -t nunonelascruz/chat-server:version1 .
 ```
The `-t` flag tags the image with a name and version. In this case, the image is tagged as `nunonelascruz/chat-server:version1 .`

5. To ensure that the image was built correctly, I ran the following command:

```bash
    docker images
```

Here is the output of the command, displaying the newly created Docker image:

[![Captura-de-ecr-2025-05-19-112541.png](https://i.postimg.cc/dtq6FPPK/Captura-de-ecr-2025-05-19-112541.png)](https://postimg.cc/K4HB7W89)

6. I started the Docker container with the following command:
 
```bash
    docker run -p 59001:59001 nunonelascruz/chat-server:version1 
```

The -p flag maps the host port to the container port. In this case, it maps port 59001 on the host to port 59001 on the container. Below is the output of the command, which shows the Docker container running the chat server:

[![Captura-de-ecr-2025-05-19-114338.png](https://i.postimg.cc/LsTfNh5Y/Captura-de-ecr-2025-05-19-114338.png)](https://postimg.cc/SXnJKSWk)

7. In a new terminal window, I navigated to the directory containing the chat client and executed the following commands to build and launch the chat client:

```bash
    ./gradlew build
    ./gradlew runClient
 ```

8. I tested the chat functionality by connecting to the chat server with two different clients. Below is the output from one of the clients connected to the chat server running in the Docker container, displaying a sample message:

[![Captura-de-ecr-2025-05-19-114215.png](https://i.postimg.cc/kMhQ0VcW/Captura-de-ecr-2025-05-19-114215.png)](https://postimg.cc/hhTQxtNG)

In the terminal where the Docker container was running, I observed the connections and disconnections of new clients on the chat server.

[![Captura-de-ecr-2025-05-19-114417.png](https://i.postimg.cc/D0WXvggL/Captura-de-ecr-2025-05-19-114417.png)](https://postimg.cc/fSNL5Y1R)

9. Finally, I uploaded the Docker image to Docker Hub using the following command:

```bash
    docker push nunonelascruz/chat-server:version1 
```

The image was successfully pushed to the Docker Hub repository and is now available for use, as displayed in the image below:

[![Captura-de-ecr-2025-05-19-114501.png](https://i.postimg.cc/c4nnPF6N/Captura-de-ecr-2025-05-19-114501.png)](https://postimg.cc/fJMy9jxH)

## Dockerfile - version 2

For the second approach, I built the chat server on my host machine and then transferred the JAR file into the Docker image. 
Here are the steps I followed:

1. First, I moved to the project directory and executed the Gradle build command to create the JAR file:

    ```bash
    ./gradlew build
    ```
   
This resulted in the creation of the basic_demo-0.1.0.jar file in the build/libs directory.

2. Next, I moved to the directory containing the Dockerfile for version 2.

3. Below is the content of the Dockerfile I used:

```dockerfile
# Runtime image using a slim JRE
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the .jar file from CA1 to the Docker image
COPY ../../../../CA1/part2/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar ./basic_demo-0.1.0.jar

# Expose port 59001
EXPOSE 59001

# Set the entrypoint to run the ChatServerApp
ENTRYPOINT ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

This Dockerfile is more straightforward than the previous one, as it doesn't require cloning the repository or building the project. Instead, it copies the JAR file generated by the Gradle build command on the host machine into the Docker image and sets the server to run on port 59001.

4. I created the Docker image by running the following command:

```bash
    docker build -f DockerfileV2 -t nunonelascruz/chat-server:version2 ../../..
```

The -f flag specifies the Dockerfile to use for the build, while ../../.. sets the build context to three levels up from the current directory, ensuring all required files are available during the build process.
The -t flag tags the image with a name and version. In this case, the image is tagged as `nunonelascruz/chat-server:version2.`

5. To verify that the image was built correctly, I executed the following command:

```bash
    docker images
```

Here is the output of the command, displaying the newly created Docker image:

[![Captura-de-ecr-2025-05-19-115409.png](https://i.postimg.cc/GmNhrCf7/Captura-de-ecr-2025-05-19-115409.png)](https://postimg.cc/RW7zQj4K)

6. I started the Docker container with the following command:

```bash
    docker run -p 59001:59001 nunonelascruz/chat-server:version2
```

Here is the output of the command, indicating that the Docker container is running the chat server:

[![Captura-de-ecr-2025-05-19-115637.png](https://i.postimg.cc/KvpmrbmG/Captura-de-ecr-2025-05-19-115637.png)](https://postimg.cc/nC78p6sg)

7. I navigated to the directory containing the chat client and executed the following commands to run it:

```bash
    ./gradlew runClient
```

I launched two clients in separate terminals to test the chat functionality. Below is the output from the chat:

[![Captura-de-ecr-2025-05-19-115856.png](https://i.postimg.cc/MKL8fYYX/Captura-de-ecr-2025-05-19-115856.png)](https://postimg.cc/Xp9PTdm0)

In the terminal where the Docker container was running, I observed the connections and disconnections of new clients in the chat:

[![Captura-de-ecr-2025-05-19-115928.png](https://i.postimg.cc/Lstd7FFZ/Captura-de-ecr-2025-05-19-115928.png)](https://postimg.cc/06yHM3Q8)

8. Finally, I uploaded the Docker image to Docker Hub using the following command:

```bash
    docker push nunonelascruz/chat-server:version2
```

The image was successfully pushed to the Docker Hub repository and is now available for use, as shown in the image below:

[![Captura-de-ecr-2025-05-19-120046.png](https://i.postimg.cc/B6prS56S/Captura-de-ecr-2025-05-19-120046.png)](https://postimg.cc/2bqcTZhM)

## Conclusion

In this assignment, I successfully containerized a chat server application using Docker. By following the outlined steps, I created two versions of the Docker image. The first version involved building the application directly within the `Dockerfile`, while the second version built the application on the host machine and copied the resulting JAR file into the Docker image. Both approaches showcased Docker's flexibility and effectiveness in managing and deploying applications consistently across different environments.


## Introduction

In this report, I outline the process of containerizing a web application with Docker. The objective of this project was to illustrate the steps involved in building, deploying, and managing both a web application and its associated database within Docker containers. I also explored an alternative deployment method using Heroku, a cloud platform that simplifies the process of deploying and maintaining applications. The report includes the creation of Dockerfiles for both the web application and the database, as well as the use of Docker Compose to manage the services. Furthermore, I detail the process of tagging and uploading Docker images to a repository. Through this experience, I gained a deeper understanding of containerization and modern deployment strategies.

## DB Dockerfile

I began by developing a `Dockerfile` for the database service, which was set up to run an H2 database server. The `Dockerfile` was stored in a `db` directory and contained the following configuration:

```dockerfile
FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk-headless && \
    apt-get install unzip -y && \
    apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD ["java", "-cp", "./h2-1.4.200.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

##### Explanation:

- **Base Image**: The ubuntu:latest image was chosen as the base to provide a clean, up-to-date environment for the container.
- **Install Java**: OpenJDK 11 was installed to supply the required Java runtime for the H2 database. In addition, tools such as unzip and wget were also included to facilitate the installation and management of necessary files.
- **Directory Setup**: A directory, /usr/src/app, was created to store the application files within the container.
- **Download H2 Database**: The H2 database JAR file was downloaded from Maven's repository to ensure the correct version of the database is used in the container.
- **Port Exposure**: Ports 8082 and 9092 are exposed to allow web access and TCP connections, respectively, for the database service.
- **Start Command**: The specified command runs the H2 database server with options to enable both web and TCP access, as well as to create the database if it does not already exist.

## Web Dockerfile

Next, I created a `Dockerfile` in a `web` directory with the following content:

```dockerfile
# Use Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Create working directory
WORKDIR /usr/src/app

# Install git
RUN apt-get update && apt-get install -y git

# Clone the repository
RUN git clone https://github.com/NunoNC/devops-24-25-1241919.git .

# Navigate to the project directory
WORKDIR /usr/src/app/CA1/part3/react-and-spring-data-rest-basic

# Make Gradle wrapper executable and build the project
RUN chmod +x gradlew && ./gradlew build

# Expose the application port
EXPOSE 8080

# Run the Spring Boot JAR
CMD ["java", "-jar", "build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war"]
```

##### Explanation:

- **Base Image**: The tomcat:10-jdk17-openjdk-slim image was chosen as it offers a lightweight version of Tomcat 10, bundled with OpenJDK 17, providing an efficient environment for running the web application.
- **Directory Setup**: A directory, /usr/src/app, was created to store the project files within the container.
- **Install Git**: Git was installed to facilitate the cloning of the repository into the container.
- **Clone Repository**: The project repository was cloned from GitHub into the working directory inside the container.
- **Build Project**: The script navigates to the specific project directory, grants executable permissions to the Gradle wrapper, and then executes the Gradle build command to compile the project.
- **Deploy WAR File**: The generated WAR file is copied to the Tomcat webapps directory for deployment.
- **Port Exposure**: Port 8080 is made accessible to allow users to access the web application externally.
- **Start Command**: The specified command starts Tomcat to host and serve the web application.

## Docker Compose

To orchestrate both the database and web application containers, I created a `docker-compose.yml` file. This file outlines the services and specifies how they communicate with one another.

```yaml
services:
  web:
    build: ./web
    ports:
      - "8080:8080"
    networks:
      my_custom_network:
        ipv4_address: 192.168.56.10
    depends_on:
      - db

  db:
    build: ./db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      my_custom_network:
        ipv4_address: 192.168.56.11

networks:
  my_custom_network:
    driver: bridge
    ipam:
      config:
        - subnet: "192.168.56.0/24"

volumes:
  db_data: {}
```

##### Explanation:

- **Services**:
  - **Web Service**:
    - The web service is built from the ./web directory, where the relevant Dockerfile and application files are located.
    - Port 8080 is mapped to the host to allow external access to the web service running within the container.
    - The service is connected to a custom network, my_custom_network, and assigned a static IP address of 192.168.56.10 for consistent and reliable communication.
    - The web service depends on the db service, ensuring that the database is fully up and running before the web application starts.
  - **DB Service**:
    - The database service is built from the ./db directory, where the corresponding Dockerfile for the database setup is located.
    - Ports 8082 and 9092 are mapped to the host to allow web and TCP access to the database service, respectively.
    - A volume is mounted to the database service to ensure data persistence, allowing the data to be retained even if the container is restarted or recreated.
    - The database service is connected to my_custom_network with a static IP address of 192.168.56.11, ensuring stable and predictable network communication.
- **Networks**:
  - my_custom_network: An external custom network is utilized, which must be created separately from the docker-compose.yml file. This ensures proper IP address assignment and network configuration for the services.

To build and run the services defined in the `docker-compose.yml` file, I executed the following command:

```bash
docker-compose up --build
```

When the services were running, I could access the web application at:
- http://localhost:8080/basic-0.0.1-SNAPSHOT/ 

And the H2 database console at: 
- http://localhost:8082

Below are screenshots demonstrating successful access to both the web application and the H2 database console:

[![Captura-de-ecr-2025-05-12-123645.png](https://i.postimg.cc/MZsCgYNt/Captura-de-ecr-2025-05-12-123645.png)](https://postimg.cc/4n929pJh)

[![Captura-de-ecr-2025-05-12-123651.png](https://i.postimg.cc/fTJGs9hs/Captura-de-ecr-2025-05-12-123651.png)](https://postimg.cc/3dQSCWRf)

## Tag and Push Images

To verify that the images were properly labeled and uploaded to the Docker Hub repository, I took the following steps:

Initially, I displayed all available Docker images along with their IDs by running this command:
```bash
docker images
```

This helped me determine the Image IDs of the specific images I needed to tag and upload.

Afterward, I assigned appropriate tags to the images by referencing their Image IDs from the earlier step. I used the following commands to apply the correct repository name and tag:

```bash
docker tag part4-web:latest nunonelascruz/part4-web:latest
docker tag part4-db:latest nunonelascruz/part4-db:latest
```

These commands labeled the `part2-web` image with the web tag and the `part2-db` image with the db tag, as confirmed by the output from the docker images command.

```bash
docker push nunonelascruz/part2-web:web
docker push nunonelascruz/part2-db:db
```

The screenshot below illustrates the successful upload of the images to Docker Hub:

[![Captura-de-ecr-2025-05-12-124421.png](https://i.postimg.cc/85XZXZc6/Captura-de-ecr-2025-05-12-124421.png)](https://postimg.cc/tZPdYthq)

## Working with volumes

To verify that the database file was properly placed in the volume, I used the `docker-compose` exec command to access the running database container and manually transfer the required file:

```bash
docker-compose exec db bash
```

Within the container shell, I transferred the `h2-1.4.200.jar` file to the appropriate volume directory:

```bash
cp /usr/src/app/h2-1.4.200.jar /usr/src/data-backup
exit
```

This sequence of commands accesses the db container, copies the specified file into the volume directory, and then exits the container shell. This guarantees that the database file is saved to the volume and persisted on the host machine.

## Alternative solution

As an alternative deployment option, I considered using Heroku to deploy the web application.

1. Initially, I set up a Heroku account and installed the Heroku CLI on my local machine. Afterward, I authenticated with my Heroku account by running the following command:

    ```bash
    heroku login
    ```

2. Then, I initialized a new Heroku app by running the following command:

    ```bash
    heroku create my-app-name
    ```

3. To deploy the web application on Heroku, I pushed the WAR file to the Git repository of the Heroku app:

    ```bash
    git push heroku master
    ```

4. Once the deployment was finished, I accessed the web application in the browser by running the following command:

    ```bash
    heroku open
    ```

5. To upload the images to the Heroku Container Registry, I ran the following commands:

    ```bash
    heroku container:login
    heroku container:push web --app my-app-name
    heroku container:push db --app my-app-name
    ```

6. Finally, I deployed the images to the Heroku app by executing the following command:

    ```bash
    heroku container:release web db --app my-app-name
    ```

7. Once the release was successful, I accessed the deployed web application on Heroku through the provided URL. In the browser, the web application was running smoothly.

## Conclusion

This project allowed me to successfully containerize both a web application and a database using Docker, while orchestrating their deployment with Docker Compose. The process included creating Dockerfiles for each service, configuring volumes for data persistence, and managing the services through Docker Compose. In addition, I explored deploying the application on Heroku as an alternative solution, demonstrating the flexibility and scalability of cloud platforms for application deployment.
