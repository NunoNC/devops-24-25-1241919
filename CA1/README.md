#  CA1: Version Control with Git: Technical Report
**Author:** Nuno Cruz

**Date:** 11/03/2025

**Course:** DevOps

**Program:** SWitCH DEV

**Institution:** ISEP - Instituto Superior de Engenharia do Porto

## Table of Contents
- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1: Development Without Branches](#part-1-development-without-branches)
    - [Goals and Requirements](#goals-and-requirements)
    - [Key Developments](#key-developments)
- [Part 2: Development Using Branches](#part-2-development-using-branches)
    - [Goals and Requirements](#goals-and-requirements-1)
    - [Key Developments](#key-developments-1)
- [Alternative Solution](#alternative-solution)
    - [Comparison of SVN and Git](#comparison-of-svn-and-git)
    - [Utilizing SVN for the Assignment](#utilizing-svn-for-the-assignment)
- [Conclusion](#conclusion)

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
## Part 1: Development Without Branches

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

## Part 2: Development Using Branches

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
