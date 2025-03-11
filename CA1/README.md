#  CA1: Version Control with Git: Technical Report
*Author:* Nuno Cruz

*Date:* 11/03/2025

*Course:* DevOps

*Program:* SWitCH DEV

*Institution:* ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents
- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1: Development Without Branches](#part-1-development-without-branches)
    - [Goals and Requirements](#goals-and-requirements)
    - [Key Developments](#key-developments)
- [Conclusion](#conclusion)

## Introduction
This report covers the Version Control with Git assignment for the DevOps course. The assignment consists of two sections: **Part 1** focuses on basic version control without branching, while **Part 2** involves implementing branches for features development and bug fixes. The Final Results section presents the project's progress, showcasing how the application evolved after integrating new functionalities and resolution of issues. Additionally, I examined an Alternative Solution to Git -Subversion (SVN)- by comparing its features and assessing its suitability for the objectives of this assignment.

## Environment Setup
I began by creating a local copy of the Tutorial React.js and Spring Data REST application by cloning an existing repository. Then, I established my own repository to organize and manage the class assignments, ensuring that all changes and progress were effectively tracked using version control.

*Creating My Repository:* I set up a new folder on my local machine and initialized it as a Git repository. This was the first step in creating my workspace for the project. I also created a .gitignore and README file.
shell
mkdir -p ~/path path/Switch/devops-24-25-1241919/CA1/part1
touch ~/path/Switch/devops-24-25-1241919/CA1/README.md
touch ~/path/Switch/devops-24-25-1241919/.gitignore
cd ~/path/Switch/devops-24-25-1241919
git init

*Linking to GitHub:* After copying the tutorial application into my repository, I proceeded to connect my local repository to a new GitHub repository. This setup enabled me to push changes to a remote server, ensuring both backup and easy sharing.
git bash
git remote add origin <repository-URL>

*First Commit:* Once the repository was set up and all the files were in order, I added the README and .gitignore files. I then committed this initial change with a message, officially beginning my work on the assignments.
shell
git add .
git commit -m "Added README and .gitignore"

*Pushing to Remote:* Finally, I uploaded my initial commit to the GitHub repository, establishing the version history of my assignments in a remote repository.
shell
git push -u origin master

## Part 1: Development Without Branches

## Goals and Requirements
- The first part of the assignment is centered on learning and applying basic version control operations without branching.
- The tasks involve setting up the project environment, making direct changes to the master branch, and committing those changes.
- A crucial requirement is to implement a new feature (such as adding a jobYears field to an Employee object) while maintaining proper version tagging, beginning with an initial version and updating it after the feature is added.
- The focus is on practicing commits, reviewing commit history, and utilizing tags for version control.

## Key Developments
During this first part, all development took place in the master branch, following these steps:

1. *Copy the code of the Tutorial React.js and Spring Data REST Application into a new folder named CA1.*

The following commands were used to recursively copy the tutorial directory into CA1 and pom.xml:
shell
cp -r ~/path/Switch/tut-react-and-spring-data-rest/basic ~/path/Switch/devops-24-25-1241919/CA1/part1
cp ~/path/Switch/tut-react-and-spring-data-rest/pom.xml ~/path/Switch/devops-24-25-1241919/CA1/part1

2. *Commit and push the changes.*

After setting up the CA1 directory with the Tutorial application, I committed these changes to the master branch using the following commands:
shell
git add .
git commit -m "copied the code from tut"
git push

3. *Tagging the repository to mark the version of the application.*

Following the versioning pattern specified in the assignment (major.minor.revision), I tagged the initial setup as v1.1.0 and then pushed this tag to the remote repository:
shell
git tag v1.1.0 
git push origin v1.1.0

4. *Develop a new feature to add a new field to the application.*

The primary objective of this first part was to introduce a new feature by adding a jobYears field to the application, which tracks the number of years an employee has been with the company. Additionally, I developed unit tests to verify that employee creation and attribute validation were working correctly. These tests ensured that only integer values were accepted for the jobYears field and that String-type fields could not be null or empty. The following files were modified to integrate this new feature:

- *Employee.java:* The Employee class was updated to include a jobYears field, along with getter/setter methods and validation to ensure data integrity. Key modifications focused on enforcing constraints and supporting the new functionality.

java
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

- *EmployeeTest.java:* To ensure the jobYears field functions reliably, unit tests were added to this file, focusing on:
- Validation Tests: Ensuring invalid inputs (null/empty Strings, negative jobYears) are rejected.
- Positive Cases: Confirming valid inputs create Employee objects without issues.
- Equality & Hashing: Verifying equals and hashCode for correct object comparison.
- String Representation: Testing toString for accurate object details in logs and debugging.

Here are a few of the tests implemented:

java
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

- *DatabaseLoader.java:* The DatabaseLoader class, which pre-loads the database with sample data, was updated to include jobYears for sample employees. This ensured the new field was functional from the start. Below is a code snippet showing the modification:

java
public class DatabaseLoader implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception { // <4>
        this.repository.save(new Employee("Frodo", "Baggins", "ring bearer", "explorer", 3));
    }
}

- *app.js:* The React components in app.js were updated to display the jobYears field in the employee list. The EmployeeList and Employee components now include a "Job Years" column, allowing users to see an employee's tenure alongside other details. Below is a code snippet showing these modifications:

javascript
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

javascript
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

5. *Debug the server and client parts of the solution.*

After confirming the integration of the jobYears field, I ran the application using mvn spring-boot:run and tested it at http://localhost:8080/. This hands-on testing ensured the feature functioned smoothly within the interface. Simultaneously, I performed a code review to verify proper data handling on the server side and accurate jobYears representation on the client side, maintaining feature correctness and code quality.


6. *End of the assignment.*

After ensuring the new feature’s stability and performance, I committed the changes with the message "Added jobYears as well as all the tests and modified gitignore". The updated code was then pushed to the remote repository to maintain collaboration. To mark this update, I tagged the commit as v1.2.0, following the project's semantic versioning. At the end of the assignment, I added the tag ca1-part1.1 to the repository.