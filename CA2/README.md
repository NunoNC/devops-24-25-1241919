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
