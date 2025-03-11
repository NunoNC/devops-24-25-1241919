package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void shouldCreateEmployee() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10, "JGreen@gmail.com");
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
        assertThrows(Exception.class, () -> new Employee(null,"Verde","CEO",10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenFirstNameIsEmpty(){
        //assert
        assertThrows(Exception.class, () -> new Employee("","Verde","CEO",10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenLastNameIsNull(){
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose",null,"CEO",10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenLastNameIsEmpty(){
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","","CEO",10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenDescriptionIsNull(){
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","Verde",null,10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenDescriptionIsEmpty(){
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","Verde","",10,"JGreen@gmail.com"));
    }
    @Test
    void shouldCreateAnExceptionWhenJobYearsIsNegative(){
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","Verde","CEO",-1,"JGreen@gmail.com"));
    }

    @Test
    void shouldCreateEmployeeWhenJobYearsEqualToZero() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",0,"JGreen@gmail.com");
        //assert
        assertNotNull(employee);
    }

    @Test
    void shouldCreateEmployeeWhenEmailDoesntHaveTheAtSign() throws IllegalArgumentException{
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","Verde","CEO",10,"JGreengmail.com"));;
    }

    @Test
    void shouldCreateEmployeeWhenEmailIsNull() throws IllegalArgumentException{
        //assert
        assertThrows(Exception.class, () -> new Employee("Jose","Verde","CEO",10,null));;
    }

    //equals
    @Test
    void shouldReturnTrueWhenObjectsAreInSameLocation() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = employee;
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertTrue(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsAreFromDifferentInstances() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Object o = new Object();
        //act
        boolean result = employee.equals(o);
        //assert
        assertFalse(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsToCompareIsNull() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO", 3,"JGreen@gmail.com");
        Employee employee1 = null;
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }
    @Test
    void shouldReturnTrueWhenObjectsHaveSameContent() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertTrue(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentFirstNames() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Joseph","Verde","CEO",10,"JGreen@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentLastNames() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose","Green","CEO",10,"JGreen@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentDescriptions() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose","Verde","CFO",10,"JGreen@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentEmail() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose","Verde","CFO",10,"JVerde@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentJobYears() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose","Verde","CEO",10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose","Verde","CEO",11,"JGreen@gmail.com");
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }
    @Test
    void shouldReturnFalseWhenObjectsHaveDifferentId() throws IllegalArgumentException {
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        employee.setId(1L);
        employee1.setId(2L);
        //act
        boolean result = employee.equals(employee1);
        //assert
        assertFalse(result);
    }

    //hashCode
    @Test
    void shouldReturnTheSameForTheSameObject() throws IllegalArgumentException{
        //arrange
        int loc = 10;
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        int hash1 = employee.hashCode();
        int hash2 = employee.hashCode();
        //assert
        assertEquals(hash1,hash2);
    }
    @Test
    void shouldReturnDifferentForTheDifferentObject() throws IllegalArgumentException{
        //arrange
        int loc = 10;
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        Employee employee1 = new Employee("Joseph", "Green", "CEO", 10,"JGreen@gmail.com");
        //act
        int hash1 = employee.hashCode();
        int hash2 = employee1.hashCode();
        //assert
        assertNotEquals(hash1,hash2);
    }

    //get

    @Test
    void shouldReturnID() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        employee.setId(1L);
        //act
        Long result = employee.getId();
        //assert
        assertEquals(1L,result);
    }
    @Test
    void shouldReturnFirstName() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        String result = employee.getFirstName();
        //assert
        assertEquals("Jose",result);
    }

    @Test
    void shouldReturnLastName() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        String result = employee.getLastName();
        //assert
        assertEquals("Verde",result);
    }

    @Test
    void shouldReturnDescription() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        String result = employee.getDescription();
        //assert
        assertEquals("CEO",result);
    }
    @Test
    void shouldReturnJobYears() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        int result = employee.getJobYears();
        //assert
        assertEquals(10,result);
    }

    @Test
    void shouldReturnEmail() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        String result = employee.getEmail();
        //assert
        assertEquals("JGreen@gmail.com",result);
    }

    @Test
    void shouldReturnSetEmail() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        employee.setEmail("JVerde@gmail.com");
        //assert
        assertEquals("JVerde@gmail.com",employee.getEmail());
    }

    //set
    @Test
    void shouldReturnSetFirstName() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        employee.setFirstName("Alfredo");
        //assert
        assertEquals("Alfredo",employee.getFirstName());
    }
    @Test
    void shouldReturnSetLastName() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        employee.setLastName("Red");
        //assert
        assertEquals("Red",employee.getLastName());
    }
    @Test
    void shouldReturnSetDescription() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        employee.setDescription("CFF");
        //assert
        assertEquals("CFF",employee.getDescription());
    }
    @Test
    void shouldReturnSetJobYears() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        //act
        employee.setJobYears(9);
        //assert
        assertEquals(9,employee.getJobYears());
    }
    //toString

    @Test
    void shouldReturnResult() throws IllegalArgumentException{
        //arrange
        Employee employee = new Employee("Jose", "Verde", "CEO", 10,"JGreen@gmail.com");
        employee.setId(1L);
        String expected = "Employee{" +
                "id=1" +
                ", firstName='Jose'" +
                ", lastName='Verde'" +
                ", description='CEO'" +
                ", jobYears='10'" +
                ", email='JGreen@gmail.com'}";
        //act
        String result = employee.toString();
        //assert
        assertEquals(expected,result);
    }

}