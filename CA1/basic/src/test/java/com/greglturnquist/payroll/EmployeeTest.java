package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeConstructorWithValidArguments() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Developer";
        int jobYears = 5;
        String jobTitle = "Lead Developer";
        String email = "john.doe@example.com";

        // Act
        Employee employee = new Employee(firstName, lastName, description, jobYears, jobTitle, email);

        // Assert
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(jobYears, employee.getJobYears());
        assertEquals(jobTitle, employee.getJobTitle());
        assertEquals(email, employee.getEmail());
    }

    @Test
    void testEmployeeConstructorWithInvalidArguments() {
        // Arrange
        String invalidFirstName = null;

        // Act & Assert
        assertThrows(InstantiationException.class, () -> {
            new Employee(invalidFirstName, "Doe", "Developer", 5, "Lead Developer", "john.doe@example.com");
        });
    }

    @Test
    void testEqualsAndHashCode() throws InstantiationException {
        // Arrange
        Employee employee1 = new Employee("John", "Doe", "Developer", 5,    "Lead Developer","john.doe@example.com");
        Employee employee2 = new Employee("John", "Doe", "Developer", 5,    "Lead Developer","john.doe@example.com");

        // Act & Assert
        assertEquals(employee1, employee2, "Employee objects with the same field values should be equal");
        assertEquals(employee1.hashCode(), employee2.hashCode(), "Employee objects with the same field values should have the same hash code");
    }

    @Test
    void testSetAndGetMethods() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5, "Lead Developer","john.doe@example.com");
        String firstName = "Jane";
        String lastName = "Smith";
        String description = "Lead Developer";
        int jobYears = 10;
        String jobTitle = "Lead Developer";
        String emaail = "john.doe@example.com";

        // Act
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDescription(description);
        employee.setJobYears(jobYears);
        employee.setJobTitle(jobTitle);
        employee.setEmail(emaail);

        // Assert
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(jobYears, employee.getJobYears());
        assertEquals(jobTitle, employee.getJobTitle());
        assertEquals(emaail, employee.getEmail());
    }

    @Test
    void testEmployeeId() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5, "Lead Developer","john.doe@example.com");
        Long id = 1L;

        // Act
        employee.setId(id);

        // Assert
        assertEquals(id, employee.getId());
    }

    @Test
    void testToString() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5, "Lead Developer","john.doe@example.com");

        // Act
        String employeeString = employee.toString();

        // Assert
        String expectedString = "Employee{id=null, firstName='John', lastName='Doe', description='Developer', jobYears='5', jobTitle='Lead Developer', email='john.doe@example.com'}";
        assertEquals(expectedString, employeeString);
    }

}
