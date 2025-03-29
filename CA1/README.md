#  CA1: Version Control with Git: Technical Report
**Author:** Nuno Cruz

**Date:** 11/03/2025

**Course:** DevOps

**Program:** SWitCH DEV

**Institution:** ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents
- [Part 1: Version Control with Git](#part-1-version-control-with-git)
  - [Introduction Part 1](#introduction-part-1)
  - [Environment Setup Part1](#environment-setup-part-1)
  - [Part 1.1: Development Without Branches](#part-11-development-without-branches)
    - [Goals and Requirements](#goals-and-requirements)
    - [Key Developments](#key-developments)
  - [Part 1.2: Development Using Branches](#part-12-development-using-branches)
    - [Goals and Requirements](#goals-and-requirements-1)
    - [Key Developments](#key-developments)
  - [Final Results](#final-results)
    - [Implementation](#implementation)
    - [Branches](#branches)
    - [Tags](#tags)
    - [Issue Tracking](#issue-tracking)
  - [Alternative Solution](#alternative-solution)
    - [Comparison of Fossil and Git](#comparison-of-fossil-and-git)
    - [Utilizing Fossil for the Assignment](#utilizing-fossil-for-the-assignment)
  - [Conclusion Part 1](#conclusion-part-1)
- [Part 2: Build Tools with Gradle](#part-2-build-tools-with-gradle)
  - [Introduction Part 2](#introduction-part-2)
  - [Environment Setup Part 2](#environment-setup-part-2)
  - [Gradle Basic Demo](#gradle-basic-demo)
  - [Adding a runServer Task for Simplified Server Startup](#adding-a-runserver-task-for-simplified-server-startup)
  - [Add a unit test](#add-a-unit-test)
  - [Add a new task of type Copy](#add-a-new-task-of-type-copy)
  - [Add a new task of type Zip](#add-a-new-task-of-type-zip)
  - [Conclusion Part 2](#conclusion-part-2)
- [Part 3: Build Tools with Gradle](#part-3-build-tools-with-gradle)
  - [Introduction Part 3](#introduction-part-3)
  - [Set Up Initial Gradle Project](#set-up-initial-gradle-project)
  - [Integrate Existing Code](#integrate-existing-code)
  - [Configure Frontend Plugin for Gradle](#configure-frontend-plugin-for-gradle)
  - [Add Gradle Tasks for File Management](#add-gradle-tasks-for-file-management)
  - [Alternative Solution](#alternative-solution)
  - [Conclusion Part 3](#conclusion-part-3)

# Part 1: Version Control with Git

## Introduction
This report covers the Version Control with Git assignment for the DevOps course. The assignment consists of two sections: **Part 1** focuses on basic version control without branching, while **Part 2** involves implementing branches for features development and bug fixes. The Final Results section presents the project's progress, showcasing how the application evolved after integrating new functionalities and resolution of issues. Additionally, I examined an Alternative Solution to Git -Subversion (SVN)- by comparing its features and assessing its suitability for the objectives of this assignment.

## Environment Setup
I began by creating a local copy of the Tutorial React.js and Spring Data REST application by cloning an existing repository. Then, I established my own repository to organize and manage the class assignments, ensuring that all changes and progress were effectively tracked using version control.

*Creating My Repository:* I set up a new folder on my local machine and initialized it as a Git repository. This was the first step in creating my workspace for the project. I also created a .gitignore and README file.
```shell
mkdir -p ~/path path/Switch/devops-24-25-1241919/CA1/part1
touch ~/path/Switch/devops-24-25-1241919/CA1/README.md
touch ~/path/Switch/devops-24-25-1241919/.gitignore
cd ~/path/Switch/devops-24-25-1241919
git init
```
*Linking to GitHub:* After copying the tutorial application into my repository, I proceeded to connect my local repository to a new GitHub repository. This setup enabled me to push changes to a remote server, ensuring both backup and easy sharing.
```git bash
git remote add origin <repository-URL>
```
*First Commit:* Once the repository was set up and all the files were in order, I added the README and .gitignore files. I then committed this initial change with a message, officially beginning my work on the assignments.
```shell
git add .
git commit -m "Added README and .gitignore"
```
*Pushing to Remote:* Finally, I uploaded my initial commit to the GitHub repository, establishing the version history of my assignments in a remote repository.
```shell
git push -u origin master
```
## Part 1.1: Development Without Branches

### Goals and Requirements
- The first part of the assignment is centered on learning and applying basic version control operations without branching.
- The tasks involve setting up the project environment, making direct changes to the master branch, and committing those changes.
- A crucial requirement is to implement a new feature (such as adding a `jobYears` field to an Employee object) while maintaining proper version tagging, beginning with an initial version and updating it after the feature is added.
- The focus is on practicing commits, reviewing commit history, and utilizing tags for version control.

### Key Developments
During this first part, all development took place in the master branch, following these steps:

1. **Copy the code of the Tutorial React.js and Spring Data REST Application into a new folder named `CA1`.**

The following commands were used to recursively copy the tutorial directory into `CA1 and pom.xml`:
```shell
cp -r ~/path/Switch/tut-react-and-spring-data-rest/basic ~/path/Switch/devops-24-25-1241919/CA1/part1
cp ~/path/Switch/tut-react-and-spring-data-rest/pom.xml ~/path/Switch/devops-24-25-1241919/CA1/part1
```
2. **Commit and push the changes.**

After setting up the `CA1` directory with the Tutorial application, I committed these changes to the master branch using the following commands:
```shell
git add .
git commit -m "copied the code from tut"
git push
```
3. **Tagging the repository to mark the version of the application.**

Following the versioning pattern specified in the assignment (major.minor.revision), I tagged the initial setup as `v1.1.0` and then pushed this tag to the remote repository:
```shell
git tag v1.1.0 
git push origin v1.1.0
```
4. **Develop a new feature to add a new field to the application.**

The primary objective of this first part was to introduce a new feature by adding a `jobYears` field to the application, which tracks the number of years an employee has been with the company. Additionally, I developed unit tests to verify that employee creation and attribute validation were working correctly. These tests ensured that only integer values were accepted for the `jobYears` field and that String-type fields could not be null or empty. The following files were modified to integrate this new feature:

- **Employee.java:** The Employee class was updated to include a `jobYears` field, along with getter/setter methods and validation to ensure data integrity. Key modifications focused on enforcing constraints and supporting the new functionality.
```java
public class Employee {

    public Employee(String firstName, String lastName, String description, String jobTitle, int jobYears) throws IllegalArgumentException {  
        if (!validParameters(firstName) || !validParameters(lastName) || !validParameters(description) || !validJobYears(jobYears)) {  
           throw new IllegalArgumentException("Invalid parameter");  
        }  
        this.firstName = firstName;  
        this.lastName = lastName;  
        this.description = description;   
        this.jobYears = jobYears;  
    }

    public int getJobYears() {
        return jobYears;
    }

    public void setJobYears(int jobYears) {
        if (!validJobYears(jobYears)) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        this.jobYears = jobYears;
    }

    private boolean validJobYears(int jobYears) {
        return jobYears >= 0;
    }

    private boolean validParameters(String parameters) {
        return parameters != null && !parameters.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
            Objects.equals(firstName, employee.firstName) &&
            Objects.equals(lastName, employee.lastName) &&
            Objects.equals(description, employee.description) &&
            Objects.equals(jobYears, employee.jobYears);
    }
}
```
- **EmployeeTest.java:** To ensure the `jobYears` field functions reliably, unit tests were added to this file, focusing on:
- Validation Tests: Ensuring invalid inputs (null/empty Strings, negative jobYears) are rejected.
- Positive Cases: Confirming valid inputs create Employee objects without issues.
- Equality & Hashing: Verifying equals and hashCode for correct object comparison.
- String Representation: Testing toString for accurate object details in logs and debugging.

Here are a few of the tests implemented:

```java
class EmployeeTest {

    @Test
    void shouldCreateEmployee() throws IllegalArgumentException {
        //arrange
        Employee employee = new Employee("Joseph","Green","CEO",10);
        //assert
        assertNotNull(employee);
    }

    @Test
    void shouldCreatAnEmployeeUsingAnEmptyConstructor() {
        // Arrange
        Employee employee = new Employee();
        // Assert
        assertNotNull(employee);
    }

    @Test
    void shouldCreateAnExceptionWhenFirstNameIsNull(){
        //assert
        assertThrows(Exception.class, () -> new Employee(null,"Green","CEO",10));
    }

    @Test
    void shouldCreateEmployeeWhenJobYearsEqualToZero() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Joseph","Green","CEO",0);
        //assert
        assertNotNull(employee);
    }

    @Test
    void shouldReturnFirstName() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Joseph", "Green", "CEO", 10);
        //act
        String result = employee.getFirstName();
        //assert
        assertEquals("Joseph",result);
    }
}
```
- **DatabaseLoader.java:** The DatabaseLoader class, which pre-loads the database with sample data, was updated to include `jobYears` for sample employees. This ensured the new field was functional from the start. Below is a code snippet showing the modification:

```java
public class DatabaseLoader implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception { // <4>
        this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", "explorer", 3));
    }
}
```
- **app.js:** The React components in `app.js` were updated to display the `jobYears` field in the employee list. The `EmployeeList` and `Employee` components now include a "Job Years" column, allowing users to see an employee's tenure alongside other details. Below is a code snippet showing these modifications:

```javascript
class EmployeeList extends React.Component{

    render() {
        const employees = this.props.employees.map(employee =>
            <Employee key={employee._links.self.href} employee={employee}/>
        );
        return (
            <table>
                <tbody>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Description</th>
                        <th>Job Years</th>
                    </tr>
                    {employees}
                </tbody>
            </table>
        )
    }
}
```
```javascript
class Employee extends React.Component{

    render() {
        return (
            <tr>
                <td>{this.props.employee.firstName}</td>
                <td>{this.props.employee.lastName}</td>
                <td>{this.props.employee.description}</td>
                <td>{this.props.employee.jobYears}</td>
            </tr>
        )
    }
}
```
5. **Debug the server and client parts of the solution.**

After confirming the integration of the `jobYears` field, I ran the application using `mvn spring-boot:run` and tested it at http://localhost:8080/. This hands-on testing ensured the feature functioned smoothly within the interface. Simultaneously, I performed a code review to verify proper data handling on the server side and accurate `jobYears` representation on the client side, maintaining feature correctness and code quality.


6. **End of the assignment.**

After ensuring the new feature’s stability and performance, I committed the changes with the message "Added jobYears as well as all the tests and modified gitignore". The updated code was then pushed to the remote repository to maintain collaboration. To mark this update, I tagged the commit as `v1.2.0`, following the project's semantic versioning. At the end of the assignment, I added the tag `ca1-part1.1` to the repository.

## Part 1.2: Development Using Branches

### Goals and Requirements
-   The next section covers branching for feature development and bug fixes, stressing isolation and effective merge strategies.
-   Requirements involve using feature branches for new developments or fixes, keeping changes separate until they’re ready to merge.
-   This section ends with tagging the master branch post-merge to mark new versions, highlighting effective branch management and version control.

### Key Developments
The second part focused on branch-based development to improve features and fix bugs while maintaining the master branch stable for releasing the Tutorial React.js and Spring Data REST application.

The process for adding features and fixing bugs mirrors Part 1, so I won’t repeat all the code. The key distinction is the use of branches. Here are the main steps:

1. **Start using the master branch**

To confirm I was in the correct branch, especially the master branch for stable releases, I used the git branch command. This was essential for verifying my active branch, indicated by an asterisk (*) in the output.

2. **Develop new features in branches**

During the development phase of adding an `email` field to the application, managing branches was essential. The process began with the creation of a dedicated feature branch, followed by switching to it for development work. A new branch named `email-field` was specifically created to contain all changes related to this feature. Once the branch was set up, the next step was to switch to it to begin development. To ensure that the transition was successful, I used the git branch command again to verify the active branch. The commands used were as follows:
```shell
git branch email-field
git switch email-field
git branch
```
3. **Integration and Testing of the Email Field**

The process of implementing email field support and enforcing validation closely followed the approach used for the `jobYears` field in Part 1. Below are the key steps undertaken.
- **Code Implementation**: Following the same approach as previous feature developments, I expanded the `Employee` class to incorporate an `email` field with its corresponding getter and setter methods. This required modifying data models, forms, and views to seamlessly integrate the new field into both the frontend and backend of the application.
- **Unit Testing**: Following the established approach, I implemented thorough unit tests to ensure that `Employee` instances were correctly created with the new email field. These tests also enforced validation rules, such as requiring non-null and non-empty values for the email attribute.
- **Debugging**: Both the server and client components were rigorously debugged to detect and resolve any issues introduced by the `email` field addition, ensuring smooth functionality and an optimal user experience.

4. **Merge the code with the master**

Completing the `email` field feature required several steps to integrate the changes into the main branch and update the application’s version. First, the finalized changes in the `email-field` branch were committed and pushed to the remote repository. A no-fast-forward merge was then performed to maintain the commit history. After merging, the updated main branch was pushed to the remote repository. Finally, a new version was tagged and pushed to mark this milestone. The commands used were as follows:
```shell
git add .
git commit -m "email field added"

git push --set-upstream origin email-field

git checkout master
git merge email-field

git push

git tag v1.3.0 
git push origin v1.3.0
```

5. **Create a new branch to fix a bug**

To resolve the email validation bug in the `Employee` class, a dedicated branch named `fix-invalid-email` was created, adhering to the established workflow. The development, testing, and merging processes followed the same approach as previous features and fixes, ensuring code integrity and application stability.
The core of the bug fix focused on improving the `Employee` class by implementing validation logic for the email field, ensuring it includes an "@" symbol. The following code snippet demonstrates the added validation logic:
```java
private boolean validEmail(String email) {  
  return email != null && email.contains("@");  
}
```

6. **End of the assignment**

Once the fix was implemented and thoroughly tested for effectiveness, the changes were merged into the master branch, and the application version was updated to `v1.3.1` to reflect the minor fix. This version increment underscores the ongoing improvements in functionality and reliability. Finally, the repository was tagged as `ca1-part2` to mark the completion of this assignment.

## Final Results

### Implementation
Bellow follows all the new features, the final state of the application is illustrated below:

[![Captura-de-ecr-2025-03-20-143000.png](https://i.postimg.cc/Kvdg7Nbv/Captura-de-ecr-2025-03-20-143000.png)](https://postimg.cc/tYNTpF7L)

The original model started with `First Name`, `Last Name`, and `Description`. The development process began with `Job Years` in `Part 1` of `CA1` to monitor career duration. In `Part 2` of `CA1`, the `Email` field was integrated for communication purposes.
### Branches
The image below reveals the existing branches in the repository, as output by the git branch command.

[![Captura-de-ecr-2025-03-20-144235.png](https://i.postimg.cc/nVX4vMm2/Captura-de-ecr-2025-03-20-144235.png)](https://postimg.cc/pyt5vWq5)

This assignment highlighted the importance of utilizing branches to separate modifications for distinct features or bug fixes. This method preserves the stability of the codebase while providing a well-organized and transparent change history.
### Tags

[![Captura-de-ecr-2025-03-20-144520.png](https://i.postimg.cc/BvZDtvsx/Captura-de-ecr-2025-03-20-144520.png)](https://postimg.cc/bZMdBpZJ)

Using tags helped me identify significant milestones in the project's history, simplifying progress tracking and allowing for easy reversion to previous versions when necessary.
### Issue Tracking

During development, 17 issues were logged on `GitHub` to monitor and address various problems. Each issue was resolved and closed by referencing fixes #1 through fixes #17 in the commit messages. This approach maintains a well-documented history of identified issues and their solutions while enabling automatic issue closure upon pushing commits.

Issues play a crucial role in a project by helping track bugs, feature requests, and tasks. They can be assigned to team members, labeled for organization, and linked to commits or pull requests for better traceability. Going forward, the objective is to incorporate issues consistently throughout development to enhance task management, monitor progress effectively, and strengthen team collaboration.

## Alternative Solution
Exploring alternatives to Git, Fossil offers a centralized approach with built-in tools, differing from Git’s decentralized model. Similarly, SVN provides a centralized version control system.

This section compares SVN to Git in key features like branching, merging, and revision tracking, while also outlining how SVN could support the goals of this assignment.

### Comparison of Fossil and Git

# Fossil vs. Git Comparison

| Feature              | Fossil                                                                                             | Git                                                                                                                    |
|----------------------|--------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| *Architecture*      | Centralized, with a single repository as the source of truth.                                      | Distributed, allowing multiple full-version repositories for redundancy and collaboration.                             |
| *Versioning Model*  | Snapshot-based, storing the state of the entire project at each commit.                           | Snapshot-based, tracking the state of the entire repository at each commit.                                           |
| *Branching & Merging*| Efficient branching and merging with simpler workflows compared to Git.                           | Efficient branching and merging, ideal for parallel development workflows.                                             |
| *Binary Files Handling*| Efficient handling of binary files, optimized with delta storage.                                | Stores full binary files per change, increasing repository size but ensuring all versions are accessible.              |

---

# Utilizing Fossil for the Assignment

### Repository Setup and Import
Set up a *Fossil* repository and import the project files:

```bash
# Create a new Fossil repository
fossil init /path/to/fossil_repository.fossil
```

# Import the project into the Fossil repository

```bash
fossil open /path/to/fossil_repository.fossil
fossil import /path/to/TutorialReactSpringDataREST
```

### Feature Development and Branching

```bash
# Create a branch for the new feature
fossil branch new-feature
```

### Committing and Tagging

```bash
# Commit changes in the working directory
fossil commit -m "Implemented new feature"
```

# Tag a stable release

```bash
fossil tag v1.0 "Tagging version 1.0"
```


### Merging and Deployment Preparation

```bash
# Merge the feature branch into the trunk
fossil merge new-feature
```

# Commit the merge
```bash
fossil commit -m "Merged feature branch into trunk"
```

## Conclusion Part 1

Completing the `Version Control with Git` assignment strengthened my understanding of version control in software development. `Part 1` covered fundamental Git concepts like modifying the master branch, committing changes, and tagging versions. `Part 2` introduced branching, enhancing my ability to isolate changes for a clearer project history and improved management.The Final Results highlight the enhanced functionality through feature additions, demonstrating how version control principles apply in real-world development. The use of GitHub issues helped track and manage problems, providing a clear history of solutions.
Exploring Fossil as an alternative to `Git` offered insights into different version control models. Comparing Fossil's centralized approach with Git's distributed system broadened my perspective on how version control can be tailored to fit project needs.
This assignment strengthened my technical skills with `Git` and `Fossil` while highlighting the importance of version control in collaboration, code integrity, and project management.

# Part 2: Build Tools With Gradle

## Introduction Part 2

This report provides an overview of the Build Tools with Gradle assignment for the DevOps course, detailing essential Gradle applications. It covers the progression from setup to advanced tasks, including custom task creation and unit testing.

Following the Environment Setup, the Gradle Basic Demo section showcases a multithreaded chat server, demonstrating how Gradle handles build management and execution.

The Add a New Task section explores extending Gradle’s functionality, while Add a Unit Test highlights the integration of tests to ensure project reliability. Finally, Add a `Copy Task` and Add a `Zip Task` showcase Gradle’s capabilities in file handling for efficient project distribution.

The report concludes with a summary of the learning outcomes, challenges encountered, and practical skills acquired in utilizing Gradle for software development.

## Environment Setup Part 2

The setup started with creating a new directory, `/CA1/Part2`, and cloning the example application from the provided Bitbucket repository. The repository included a build.gradle file and the Gradle Wrapper, ensuring a stable and consistent build environment.

After installing Gradle, I verified the setup by running gradle -v. Next, I imported the project into a Gradle-supported IDE, utilizing its built-in tools. To confirm the configuration, I executed a basic Gradle build, ensuring all components were properly set up.

These steps established a strong foundation for the tasks that followed in the assignment.

## Gradle Basic Demo

The Gradle Basic Demo provided a hands-on experience in managing a multithreaded chat server, enabling it to handle multiple clients concurrently.

**Gradle Build Process:**

To set up the demo, I executed ./gradlew build in the project's root directory. This command compiled the code and produced an executable .jar file. The screenshot below confirms the successful build.

[![Captura-de-ecr-2025-03-20-152434.png](https://i.postimg.cc/8cyWRQH7/Captura-de-ecr-2025-03-20-152434.png)](https://postimg.cc/64vyKPFK)

**Server Launch:**

Next, I started the chat server using the command:

```bash
java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
```

The screenshot below confirms the server is running and ready to accept client connections.

[![Captura-de-ecr-2025-03-20-152845.png](https://i.postimg.cc/MGyQsj52/Captura-de-ecr-2025-03-20-152845.png)](https://postimg.cc/GBmttt6q)

[![image.png](https://i.postimg.cc/kGNQ2r2h/image.png)](https://postimg.cc/m1Dzn5NQ)

On the client side, I connected to the chat server by running:

```bash
./gradlew runClient
```
Each client was configured to connect to localhost on port 59001. The build.gradle file allowed easy modifications for different connection settings. To showcase the server’s ability to handle multiple clients, I launched several client instances from separate terminals.

The screenshots below illustrate the active chat sessions, demonstrating the multi-client functionality in action.

[![image.png](https://i.postimg.cc/wj5DFYFq/image.png)](https://postimg.cc/ZWR9Kg12)

## Adding a runServer Task for Simplified Server Startup

I added a `runServer` task to the build.gradle file to simplify the server startup process. This task enables launching the chat server directly with a Gradle command, removing the need for manual execution.

The `runServer` task is defined in the build.gradle file as follows, with its type set to JavaExec for executing Java applications. It depends on the classes task to ensure all required classes are compiled before launching the server. This task starts `ChatServerApp` on port 59001:

```java
///tasks.register('runServer', JavaExec) {
///group = "DevOps";
///description = "Launches a chat Server";
///
///    classpath = sourceSets.main.runtimeClasspath;
///
///    mainClass = "basic_demo.ChatServerApp";
///    args '59001'
///}
```

I verified this task by executing `./gradlew runServer` from the command line. The terminal output confirmed that the server started successfully, as shown in the screenshot below.

[![Captura-de-ecr-2025-03-20-153323.png](https://i.postimg.cc/XqXX0Zfv/Captura-de-ecr-2025-03-20-153323.png)](https://postimg.cc/dZPJ2VSM)

Integrating this task into the Gradle build script highlights Gradle's flexibility and boosts productivity by automating routine processes.

## Add a unit test

I implemented a unit test to validate the functionality of the App class. The test, located in `src/test/java/basic_demo/AppTest.java`, ensures that the App class returns a non-null greeting message.

To configure the test environment, I added the `JUnit` dependency to the `build.gradle` file:

```java
///testImplementation 'junit:junit:4.12'
```

This ensures that the project can recognize and run `JUnit` tests smoothly. Below is the content of `AppTest.java`, which contains the test case:

```java
package basic_demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
@Test public void testAppHasAGreeting() {
App classUnderTest = new App();
assertNotNull("app should have a greeting", classUnderTest.getGreeting());
}
}
```

I executed the test using the command `./gradlew test`. The screenshot below shows the terminal output, confirming the test passed successfully.

[![Captura-de-ecr-2025-03-20-154336.png](https://i.postimg.cc/5jdJkjpT/Captura-de-ecr-2025-03-20-154336.png)](https://postimg.cc/kRcL2J0v)

## Add a new task of type Copy

The next step was adding a `Copy` task to the `build.gradle` file to create a backup of the source code. This provides a reliable recovery point in case of unexpected issues during development.

The backup task leverages Gradle's Copy task type to duplicate the contents of the src directory into a specified backup location within the project. This ensures an up-to-date snapshot of the codebase, particularly before making major changes or updates.

```java
///task backup(type: Copy) {
///group = "DevOps"
///description = "Copies the sources of the application to a backup folder"
///
///from 'src'
///into 'backup'
///}
```

After adding the task, I verified its functionality by executing `./gradlew backup` from the command line. The terminal output confirmed the successful execution, ensuring that the source code was copied to the backup location. This validation demonstrates the task's effectiveness in protecting the project's code.

[![Captura-de-ecr-2025-03-20-154657.png](https://i.postimg.cc/sgzQM2yT/Captura-de-ecr-2025-03-20-154657.png)](https://postimg.cc/JGT4gMsX)

## Add a new task of type Zip

The final task was adding a `Zip task` to package the project's source code into a compressed .zip file. This simplifies the process of archiving the src directory, making it more convenient for backups and distribution. It is particularly useful for preserving project iterations or preparing the code for sharing.

Below is the task definition:

```java
///task archive(type: Zip) {
///group = "DevOps"
///description = "Creates a zip archive of the source code"
///
///    from 'src'
///    archiveFileName = 'src_archive.zip'
///    destinationDir(file('build'))
///}
```

After defining the zip task, I ran it using `./gradlew archive`. The terminal output confirmed that the task executed successfully, compressing the src directory into a ZIP archive. The screenshot below showcases the successful archive creation.

[![Captura-de-ecr-2025-03-26-111733.png](https://i.postimg.cc/ZKyMDxCM/Captura-de-ecr-2025-03-26-111733.png)](https://postimg.cc/tYpksPd3)
[![Captura-de-ecr-2025-03-20-155255.png](https://i.postimg.cc/1X60ZYcX/Captura-de-ecr-2025-03-20-155255.png)](https://postimg.cc/7JHJ39Rr)

## Conclusion Part 2

This assignment offered valuable insights into Gradle’s real-world applications as a build tool. The tasks completed showcased its flexibility in automating builds, integrating unit tests, and handling file operations—key aspects of an efficient development workflow.

Adding tasks like `runServer`, `backup`, and `archive` demonstrated Gradle’s extensibility, making development more efficient while improving project resilience and distribution. Integrating unit tests reinforced the importance of testing in software development and showcased how Gradle seamlessly supports this process.

Overall, this experience has enhanced my understanding of Gradle’s role in software develoj17pment, providing valuable knowledge to optimize future projects with more efficient and reliable workflows.

# Part 3: Build Tools With Gradle

## Introduction
This document presents a detailed report on the tasks accomplished in the Class Assignment 3 for the DevOps course. The focus of this assignment is on utilizing Gradle as a build automation tool. It outlines a structured series of tasks aimed at migrating a Spring Boot application from Maven to Gradle, emphasizing the practical implementation and benefits of Gradle within the software development lifecycle.

The `Set-Up Initial Gradle Project` section focuses on establishing the foundational Gradle setup. `Integrate Existing Code` details the process of migrating the application's source code into the Gradle project structure. `Configure Frontend Plugin for Gradle` explains how a plugin is integrated to handle frontend assets. In `Add Gradle Tasks for File Management`, custom tasks are implemented to enhance project maintenance. An `Alternative Solution` explores other build tools as potential options, while the `Conclusion` summarizes key takeaways and reflects on the practical application of Gradle in software development workflows.

## Set-Up Initial Gradle Project
The initial setup of the Gradle project required several crucial steps to successfully transition from a Maven-based structure to a Gradle-based one.
The process started with the creation of a new branch dedicated to this part of the assignment, ensuring that the project's setup and subsequent modifications remained isolated and manageable. This was achieved by running the following command:

```bash
git checkout -b tut-basic-gradle
```

Next, a new Spring Boot project was initialized using the Spring Initializr web interface at https://start.spring.io/, providing a structured foundation for the Gradle-based setup.
The project was set up with essential dependencies, including Rest Repositories, Thymeleaf, JPA, and H2, ensuring the necessary functionality for the application.
This configuration guarantees that all essential modules are included for the application's functionality and are efficiently managed by Gradle.

The generated `.zip` file containing the project skeleton was downloaded and extracted into the `CA1/part3/` directory within the repository.This structure forms the basis of an "empty" Spring application ready to be built using Gradle.
To confirm the project setup and inspect the available Gradle tasks, the following command was executed in the project's root directory:

```bash
./gradlew tasks
```

The output of this command displayed a list of available tasks and functionalities that can be executed using the Gradle build tool, confirming the successful setup.
```bash
Application tasks
-----------------
bootRun - Runs this project as a Spring Boot application.
bootTestRun - Runs this project as a Spring Boot application using the test runtime classpath.

Build tasks
-----------
assemble - Assembles the outputs of this project.
bootBuildImage - Builds an OCI image of the application using the output of the bootJar task
bootJar - Assembles an executable jar archive containing the main classes and their dependencies.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the classes of the 'main' feature.
resolveMainClassName - Resolves the name of the application's main class.
resolveTestMainClassName - Resolves the name of the application's test main class.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the 'main' feature.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'react-and-spring-data-rest-basic'.
dependencies - Displays all dependencies declared in root project 'react-and-spring-data-rest-basic'.
dependencyInsight - Displays the insight into a specific dependency in root project 'react-and-spring-data-rest-basic'.
dependencyManagement - Displays the dependency management declared in root project 'react-and-spring-data-rest-basic'.
help - Displays a help message.
javaToolchains - Displays the detected java toolchains.
outgoingVariants - Displays the outgoing variants of root project 'react-and-spring-data-rest-basic'.
projects - Displays the sub-projects of root project 'react-and-spring-data-rest-basic'.
properties - Displays the properties of root project 'react-and-spring-data-rest-basic'.
resolvableConfigurations - Displays the configurations that can be resolved in root project 'react-and-spring-data-rest-basic'.
tasks - Displays the tasks runnable from root project 'react-and-spring-data-rest-basic'.
```

The output demonstrated the creation of several tasks related to building and running the application, along with others, as outlined above.
This detailed list of tasks offered valuable insights into the capabilities now accessible through the Gradle build tool, laying the foundation for further customization and development in the following stages of the project.

## Integrate Existing Code
This phase of the project focused on integrating the existing codebase from a basic tutorial setup into the newly created Gradle project structure.
The integration process was carried out carefully to ensure that all components functioned properly within the new build management system.

The following steps were followed to successfully integrate the existing code into the Gradle project:

- **Replace the Source Directory**: The original `src` directory of the Gradle project was removed to allow for the integration of the existing codebase. The `src` folder, along with all its subdirectories, was then copied from the basic tutorial folder into the newly created Gradle project directory.

- **Include Additional Configuration Files**: The essential configuration files, `webpack.config.js` and `package.json`, were also copied into the root of the new project directory to preserve the frontend build configurations and dependencies.

- **Remove Redundant Directories**: After the migration, the `src/main/resources/static/built` directory was deleted. This directory is intended to be automatically generated by Webpack during the build process, and therefore should not be manually included in version control to prevent conflicts and redundancy.


The following steps were taken to resolve a compilation error that occurred after migrating the codebase from `CA1-part1` to `CA1-part3`:

- **Adjust Import Statements**: In line with the updated project dependencies and the transition from Java EE to Jakarta EE, modifications were made to the Java classes.
  In the `Employee.java` class, the import statements were updated from `javax.persistence` to `jakarta.persistence` to reflect the Jakarta EE transition.

- **Package Manager Configuration**: The `package.json` was updated to specify a fixed version of the package manager by adding `"packageManager": "npm@9.6.7"`. This ensures that the project consistently uses the same version of the package manager across different environments.

After the successful integration and configuration adjustments, the application was tested to verify its operational integrity:

- **Running the Application**: The command `./gradlew bootRun` was executed to start the application, which compiles the code and launches the backend.

- **Verifying the Frontend**: Accessing http://localhost:8080 in a web browser should display an empty page, indicating that the backend is running successfully but without any content yet.
  This behavior is expected at this stage, as the Gradle setup currently lacks a plugin required to handle the frontend code. This gap will be addressed in the following steps of the project setup.

This approach ensures that the foundational codebase is smoothly integrated into the Gradle environment, laying the groundwork for future enhancements and the inclusion of more complex functionalities.

## Configure Frontend Plugin for Gradle

To synchronize the frontend build processes with the newly adopted Gradle system, the `org.siouan.frontend-gradle-plugin` was introduced. This plugin is essential for managing frontend assets, similar to the role of the `frontend-maven-plugin` in Maven-based projects.

- **Adding the Plugin**: The Gradle build script was updated to include the `org.siouan.frontend` plugin suitable for the Java version used in the project. For Java 17, the following line was added to the `plugins` block in `build.gradle`:
   ```groovy
   id "org.siouan.frontend-jdk17" version "8.0.0"
   ```

- **Configuring the Plugin**: To ensure the frontend assets are correctly handled, configurations specific to node version and script commands were added to build.gradle. This setup specifies the version of Node.js used and the scripts for assembling, cleaning, and checking the frontend:
    ```groovy
    frontend {
        nodeVersion = "16.20.2"
        assembleScript = "run build"
        cleanScript = "run clean"
        checkScript = "run check"
    }
    ```

  - **Updating package.json**: The scripts section in `package.json` was updated to handle the execution of Webpack and other frontend-related tasks, facilitating seamless integration with the Gradle build process.
  
   ```groovy
"scripts": {
    "webpack": "webpack",
    "build": "npm run webpack",
    "check": "echo Checking frontend",
    "clean": "echo Cleaning frontend",
    "lint": "echo Linting frontend",
    "test": "echo Testing frontend"
    }
    ```

- **Testing the Configuration**: After configuring the frontend plugin, the build and runtime behaviors were tested to ensure that both the backend and frontend components were functioning correctly within the Gradle environment.

  - Build Test: Running `./gradlew build` confirmed that the project successfully builds, including the frontend integration, ensuring that all components are properly compiled and bundled.
  - Application Execution: `./gradlew bootRun` was executed, and the application was accessed at http://localhost:8080. Unlike previous stages, the webpage was now populated with frontend content, indicating that the Gradle plugin successfully handled the frontend resources during the build and serve processes.
    This configuration demonstrates the successful integration of frontend build management into the Gradle environment, improving the project's capacity to handle complex full-stack development workflows seamlessly.

## Add Gradle Tasks for File Management
To improve the management of the project's files, particularly for distribution and cleanup, two custom Gradle tasks were defined: `copyJar` and `cleanWebpack`.

1. **Task: `copyJar`**
  - **Purpose**: This task is responsible for transferring the `.jar` file produced by the bootJar task from its output directory to a `dist` folder located at the project's root. This method guarantees that only the properly built and complete `.jar` file is selected for distribution, reducing errors and ensuring that deployments always include the latest version of the build.
  - **Configuration**:
    ```groovy
    task copyJar(type: Copy) {
        dependsOn bootJar
        from bootJar.outputs
        into file("dist")
    }
    ```
  - **Dependencies**: It explicitly declares a dependency on the `bootJar` task, making sure that the copy process runs only after `bootJar` finishes successfully, thereby preserving a consistent and dependable build workflow.


2. **Task: `cleanWebpack`**
  - **Purpose**: The purpose of this task is to remove all files produced by Webpack within the `src/main/resources/static/built` directory. This helps maintain a clean build environment, ensuring that only up-to-date and relevant files are included in each build, avoiding issues caused by leftover or obsolete files.
  - **Configuration**:

  ```groovy
    task cleanWebpack(type: Delete) {
        delete 'src/main/resources/static/built'
    }
    clean.dependsOn cleanWebpack
   ```
    
  - **Dependencies**: This task is set to execute automatically before Gradle's standard `clean` task, integrating it into the regular cleanup routine to ensure a consistent and tidy build environment.


Both tasks were run to confirm that they function as expected.:

- **Executing `copyJar`**:
  - Command: `./gradlew copyJar`
  - **Outcome**: The `.jar` file, generated by the `bootJar` task, was successfully transferred to the `dist` directory. This verification ensured that the task correctly identifies and moves the intended artifact, confirming its seamless integration into the build process and its preparedness for deployment.


- **Executing `cleanWebpack`**:
  - Command: `./gradlew cleanWebpack`
  - **Outcome**: The contents of the `src/main/resources/static/built` directory were successfully removed, confirming that the cleanup task performs as expected and effectively preserves a clean and organized build environment.

These tasks were incorporated into the build process to automate file handling, improving both the efficiency and consistency of the build and deployment workflows. Each task executed its specific role successfully, contributing to a smoother and more maintainable project lifecycle.

## Alternative Solution

**Implementing the Assignment Goals with Maven**

To replicate the `Gradle` setup, I’ll outline the steps to configure `Maven` for your `Spring Boot` application. This ensures consistent build, artifact management, and file handling processes, aligned with the existing workflow.
This solution replicates the `Gradle` setup, integrating frontend assets, custom tasks, and file management to maintain the same functionality and efficiency in `Maven`.
Here’s a detailed step-by-step guide on setting up Maven for your Spring Boot application, including the necessary `pom.xml` configurations:

- **Project Setup:**
  A `pom.xml` was created for the `Spring Boot` application, including its dependencies.
  Below is a portion of the `pom.xml` file containing the required dependencies:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-rest</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

- **Frontend Integration:**
  The `frontend-maven-plugin` was configured to manage the installation of Node and npm, along with building the frontend.
```xml
<plugins>
    <plugin>
        <groupId>com.github.eirslett</groupId>
        <artifactId>frontend-maven-plugin</artifactId>
        <version>1.11.0</version>
        <configuration>
            <nodeVersion>v16.20.2</nodeVersion>
            <workingDirectory>src/main/resources/static</workingDirectory>
        </configuration>
        <executions>
            <execution>
                <id>install node and npm</id>
                <goals>
                    <goal>install-node-and-npm</goal>
                </goals>
            </execution>
            <execution>
                <id>npm install</id>
                <goals>
                    <goal>npm</goal>
                    <configuration>
                        <arguments>install</arguments>
                    </configuration>
                </goals>
            </execution>
            <execution>
                <id>npm run build</id>
                <goals>
                    <goal>npm</goal>
                    <configuration>
                        <arguments>run build</arguments>
                    </configuration>
                </goals>
            </execution>
        </executions>
    </plugin>
</plugins>
```

- **Copy JAR Task:** The `maven-resources-plugin` was configured to copy the generated `.jar` file to a distribution folder.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-resources-plugin</artifactId>
    <version>3.2.0</version>
    <executions>
        <execution>
            <id>copy-jar</id>
            <phase>package</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.directory}/dist</outputDirectory>
                <resources>
                    <resource>
                        <directory>${project.build.directory}</directory>
                        <includes>
                            <include>*.jar</include>
                        </includes>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

- **Delete Webpack Files Task:** The `maven-clean-plugin` was configured to delete the files generated by Webpack.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-clean-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>delete-webpack-files</id>
            <phase>clean</phase>
            <goals>
                <goal>clean</goal>
            </goals>
            <configuration>
                <filesets>
                    <fileset>
                        <directory>src/main/resources/static/built</directory>
                        <includes>
                            <include>*</include>
                        </includes>
                    </fileset>
                </filesets> 
            </configuration>
        </execution>
    </executions>
</plugin>
```

**Comparison Between Maven and Gradle**

This table offers a clear comparison of the key differences between `Maven` and `Gradle`, aiding in the selection of the most suitable tool for the project's requirements.

| Feature                  | Maven                                                       | Gradle                                                      |
|--------------------------|-------------------------------------------------------------|-------------------------------------------------------------|
| **Build Language**       | XML-based configuration.                                    | Uses Groovy or Kotlin DSL for configuration scripts.        |
| **Performance**          | Generally slower due to its linear phase-dependent approach.| Faster, supports incremental builds and up-to-date checks.  |
| **Flexibility**          | Less flexible, rigid lifecycle phases.                      | Highly customizable and flexible due to scripting support.  |
| **Dependency Management**| Uses centralized repository system.                         | Offers powerful dependency management with dynamic versions.|
| **Ease of Use**          | Simple to set up with standardized lifecycle phases.        | Steeper learning curve but more powerful due to flexibility.|
| **Plugins**              | Wide range of available plugins but adding new plugins can be more complex.| Extensive plugin ecosystem; easier to write and apply custom plugins.|
| **Community and Support**| Well-established, large community and lots of resources.    | Growing community, well-supported with ample documentation. |
| **Use Case**             | Better suited for conventional Java projects.               | Ideal for multi-project builds and projects needing high customization.|

`Maven` has been introduced as a viable alternative to `Gradle` for building and managing a `Spring Boot` project.
By outlining the setup and configuration required to replicate the functionality of `Gradle`, we've highlighted `Maven's` strong capabilities in dependency management, build automation, and plugin integration.
`Maven's` structured approach, with its predictable build lifecycle and vast plugin ecosystem, makes it ideal for projects that require a standardized build process.
Although `Maven` may lack some of `Gradle's` flexibility and performance optimizations, like incremental builds and a dynamic scripting environment, its simplicity and widespread adoption in the Java community still make it a strong choice for many development scenarios.

## Conclusion Part 3

This technical report has outlined the transition of a `Spring Boot` application from `Maven` to `Gradle`, emphasizing Gradle's strong capabilities in dependency management, frontend tool integration, and build task customization.
By comparing Maven with `Gradle`, we've examined how each tool caters to different project needs, highlighting `Maven's` structured approach and `Gradle's` flexibility in scriptable environments.
This experience has not only deepened my practical understanding of build automation tools but also highlighted the importance of selecting the right tool based on specific project needs and team expertise.
This knowledge will inform future decisions, ensuring the efficient and effective management of software development projects.
