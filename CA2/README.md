#  CA2: Virtualization with Vagrant: Technical Report
**Author:** Nuno Cruz

**Date:** 01/04/2025

**Course:** DevOps

**Program:** SWitCH DEV

**Institution:** ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents

- [Part 1: Virtualization with Vagrant: Technical Report](#part-1-virtualization-with-vagrant-technical-report)
  - [Introduction part 1](#introduction-part-1)
  - [Create a VM](#create-a-vm)
  - [Configure Network and Services](#configure-network-and-services)
  - [Clone the Repository](#clone-the-repository)
  - [Set Up Development Environment](#set-up-development-environment)
  - [Execute the spring boot tutorial basic project](#execute-the-spring-boot-tutorial-basic-project)
  - [Execute the gradle_basic_demo project - Part 1](#execute-the-gradle_basic_demo-project---part-1)
  - [Execute the gradle_basic_demo project - Part 2](#execute-the-gradle_basic_demo-project---part-2)
  - [Conclusion part 1](#conclusion-part-1)
- [Part 2: Virtualization with Vagrant: Technical Report](#part-2-virtualization-with-vagrant-technical-report)
  - [Introduction part 2](#introduction-part-2)
  - [Environment Setup](#environment-setup)
  - [Setup Base Project](#setup-base-project)
  - [Vagrantfile](#vagrantfile)
  - [Connecting Spring Boot to H2 Database](#connecting-spring-boot-to-h2-database)
  - [Running the Project](#running-the-project)
  - [Vagrant commands](#vagrant-commands)
  - [Alternative Solution](#alternative-solution)
  - [Conclusion part 2](#conclusion-part-2)




## Introduction part 1

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

## Conclusion part 1
This technical report details the setup and execution of a virtual environment using VirtualBox for **Class Assignment 2**. The tasks involved creating a virtual machine, configuring its network and services, and installing the development tools required to run software projects.

The virtualization work provided valuable insights into the configuration and management of virtual machines in a DevOps context. Successfully running the Spring Boot tutorial and gradle_basic_demo projects within this environment showcased the ability to effectively simulate real-world software deployment and operational scenarios.

Key outcomes from this assignment include a deeper understanding of network configuration in virtualized environments and the complexities of setting up software on virtual platforms. Challenges such as configuring network interfaces and ensuring seamless communication between the host and guest machines were addressed, leading to a more thorough understanding of virtualization technologies.

Overall, the experiences gained from this assignment are crucial for building the skills needed to manage complex environments. These insights will be valuable in my continued education and professional growth in the field of DevOps.


## Introduction part 2

This report outlines the workflow and results of **Class Assignment 2 - Part 2**, which focused on utilizing `Vagrant` for virtualization. The task involved creating a virtual environment with `Vagrant` to deploy a `Spring Boot application` integrated with an `H2 database`. In this document, I describe the configuration steps taken to set up the `Vagrant` environment, establish the connection between the `Spring Boot app` and the `H2 database`, and ensure the application ran smoothly. I also examine an alternative setup using `VMware` with `Vagrant` and discuss the main distinctions between `VMware` and `VirtualBox`.

## Environment Setup

I implemented the virtual environment setup with `Vagrant` by following a series of key steps.

**Download Vagrant**:
I went to the [official Vagrant website](https://www.vagrantup.com/downloads) and got the necessary version to my system.

**Install Vagrant**:
I executed the installer obtained from the official `Vagrant` website. The installation was simple and involved following the on-screen instructions.

**Verify Installation**:
To verify a successful `Vagrant` installation, I launched a terminal or command prompt and executed the following command:

   ```bash
   vagrant --version
   ```

Running this command showed the `Vagrant` version number, confirming that the installation had completed successfully.

**Update .gitignore**:
To maintain a clean repository, I updated the `.gitignore` file with specific entries to exclude the `Vagrant` directory and any `.war` files from version control.

```bash
.vagrant/
*.war
```

## Setup Base Project

**Clone the Base Project**:
I began by cloning the base `Vagrant` project, which provided the essential configuration files. To do this, I ran the following command:

```bash
git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/
```

This command duplicated the repository containing the foundational setup and configuration files required for the project.

**Copy the Vagrantfile**:
Once I cloned the base project, I transferred the `Vagrantfile` from the cloned repository into the specific directory of my project. Here’s how I accomplished that:

```bash
cp -r vagrant-multi-spring-tut-demo/Vagrantfile C/Switch/devops-24-25-1241919/CA2/Part2
```

This step guaranteed that my project directory contained the necessary initial Vagrant configuration to continue with the setup.

## Vagrantfile

The `Vagrantfile` serves as the configuration file that specifies the settings and provisions for the virtual machine (VM). Once the initial `Vagrantfile` was set up, I made several important modifications to customize it for our project needs:

1. **Changed the Repository URL**: I modified the repository URL in the `Vagrantfile` to link to my specific project.
2. **Changed the Path**: I updated the path in the `Vagrantfile` to direct it to the correct directory.
3. **Added bootRun Command**: I included the `./gradlew bootRun` command to start the `Spring Boot` application.
4. **Updated the Java Version**: I updated the Java version in the setup to `OpenJDK 17`.

Next is the revitalized `Vagrantfile`:

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

To link the `Spring Boot` application with the `H2 database`, I implemented the following changes in the `react-and-spring-data-rest-basic` project:

**Modify application.properties**:
I included the required properties in `src/main/resources/application.properties` to establish a connection with the `H2` database:

```properties
spring.data.rest.base-path=/api
server.servlet.context-path=/basic-0.0.1-SNAPSHOT
spring.datasource.url=jdbc:h2:tcp://192.168.33.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
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
The `src/App.js` file required modifications to align with the updated backend path:

```javascript
    client({method: 'GET', path: '/basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {})
```

## Running the Project

Prior to running the project, I verified that VirtualBox was properly initialized and confirmed that the repository to be cloned was public. Afterward, I navigated to the project directory and ran the following command:

```bash
vagrant up
```

This command initiated the VMs and set them up based on the configurations specified in the Vagrantfile.

Once the VMs were fully operational, I opened a web browser and visited http://localhost:8080/basic-0.0.1-SNAPSHOT/ to verify that the `Spring Boot` application was functioning properly. A screenshot of the outcome is shown below:

[![Captura-de-ecr-2025-04-15-114054.png](https://i.postimg.cc/Cxz3txQs/Captura-de-ecr-2025-04-15-114054.png)](https://postimg.cc/mPWp1BRP)

Additionally, I accessed the H2 console by navigating to http://localhost:8082/ and connected to the `H2` database using the JDBC URL `jdbc:h2:tcp://192.168.33.11:9092/./jpadb`. Below is a screenshot of the `H2` login page where I entered the necessary connection details:

[![Captura-de-ecr-2025-04-15-114204.png](https://i.postimg.cc/C1GTNrQy/Captura-de-ecr-2025-04-15-114204.png)](https://postimg.cc/ftRrWCs5)

Once connected to the `H2` database, I was able to view the available tables and the data stored within them. This configuration enabled smooth interaction between the `Spring Boot` application and the `H2` database. Below is a screenshot of the `H2` console displaying the `EMPLOYEE` table:

[![Captura-de-ecr-2025-04-15-114305.png](https://i.postimg.cc/jqcBSfVh/Captura-de-ecr-2025-04-15-114305.png)](https://postimg.cc/0r6Vtby6)

These steps validated that the `Spring Boot` application was operating correctly and successfully communicating with the `H2` database.

## Vagrant commands

Below are the primary `Vagrant` commands I utilized throughout the setup process and while resolving issues:

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

These commands were instrumental in effectively managing and interacting with the virtual environments.

## Alternative Solution

In this part, I examine `VMware` as an alternative to `VirtualBox` for virtualization. The following is a detailed comparison between `VMware` and `VirtualBox`, along with steps on how VMware can be integrated with `Vagrant` to accomplish the objectives of this assignment.

**Comparison of VMware and VirtualBox**

- **VirtualBox:**
  - **Overview**: 
    - An open-source hypervisor provided by `Oracle`, widely appreciated for its user-friendly interface and compatibility with multiple operating systems.
  - **Pros**:
    - Available at no cost and distributed as open-source software.
    - Intuitive and easy-to-navigate graphical user interface.
    - Compatible with a wide range of guest operating systems.
  - **Cons**:
    - Lacks some advanced functionality found in other hypervisors.
    - Performance may lag with 3D graphics or when handling larger virtual machines.

- **VMware (Workstation and Fusion):**
  - **Overview**: 
    - A high-end virtualization platform by VMware, recognized for its strong performance and rich set of advanced features.
  - **Pros**:
    - Delivers excellent performance and reliable stability.
    - Offers advanced capabilities such as snapshots, VM cloning, and shared virtual machines.
    - Seamlessly integrates with other VMware enterprise-grade tools and solutions.
  - **Cons**:
    - Costly, as it requires a paid license after the trial period ends.
    - Has a steeper learning curve due to its extensive enterprise-level features.

**Using VMware with Vagrant**

Setting up `VMware` to work with `Vagrant` requires completing a few key steps:

1. **Install the Vagrant VMware Utility**. This step enables `Vagrant` to control and manage virtual machines through `VMware`.

```bash
# Example for installing on Linux
wget https://releases.hashicorp.com/vagrant-VMware-utility/1.0.14/vagrant-VMware-utility_1.0.14_x86_64.deb
sudo dpkg -i vagrant-VMware-utility_1.0.14_x86_64.deb
```

2. **Install the Vagrant Plugin for VMware**. This plugin enables `Vagrant` to communicate and work with `VMware`.

```bash
vagrant plugin install vagrant-VMware-desktop
```

3. **Configure the Vagrantfile**. Modify your `Vagrantfile` to set `VMware` as the chosen provider.

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.provider "VMware_desktop" do |v|
    v.vmx["memsize"] = "1024"
    v.vmx["numvcpus"] = "2"
  end
end
```

Opting for `VMware` with `Vagrant` offers a powerful solution that improves the virtualization capabilities of our development setup. It provides advanced features and superior performance, making it especially advantageous for larger and more complex projects.

This alternative solution supports the goal of enhancing our virtualization setup, ultimately improving the development process and facilitating smoother transitions to production-like environments.

## Conclusion part 2

This technical report outlines the setup and execution of **Class Assignment 2 - Part 2**, centered on virtualization with `Vagrant`. By configuring the `Vagrant` environment, linking the Spring Boot application to the `H2` database, and successfully running the project, I have showcased the practical use of virtualization concepts in a real-world context. Additionally, the alternative approach using `VMware` with `Vagrant` has been examined, emphasizing the differences between `VMware` and `VirtualBox`, as well as the advantages of utilizing `VMware` for more advanced virtualization requirements.