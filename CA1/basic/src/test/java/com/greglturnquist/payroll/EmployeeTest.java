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

        // Act
        Employee employee = new Employee(firstName, lastName, description, jobYears);

        // Assert
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(jobYears, employee.getJobYears());
    }

    @Test
    void testEmployeeConstructorWithInvalidArguments() {
        // Arrange
        String invalidFirstName = null;

        // Act & Assert
        assertThrows(InstantiationException.class, () -> {
            new Employee(invalidFirstName, "Doe", "Developer", 5);
        });
    }

    @Test
    void testEqualsAndHashCode() throws InstantiationException {
        // Arrange
        Employee employee1 = new Employee("John", "Doe", "Developer", 5);
        Employee employee2 = new Employee("John", "Doe", "Developer", 5);

        // Act & Assert
        assertEquals(employee1, employee2, "Employee objects with the same field values should be equal");
        assertEquals(employee1.hashCode(), employee2.hashCode(), "Employee objects with the same field values should have the same hash code");
    }

    @Test
    void testSetAndGetMethods() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        String firstName = "Jane";
        String lastName = "Smith";
        String description = "Lead Developer";
        int jobYears = 10;

        // Act
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDescription(description);
        employee.setJobYears(jobYears);

        // Assert
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(jobYears, employee.getJobYears());
    }

    @Test
    void testEmployeeId() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        Long id = 1L;

        // Act
        employee.setId(id);

        // Assert
        assertEquals(id, employee.getId());
    }

    @Test
    void testToString() throws InstantiationException {
        // Arrange
        Employee employee = new Employee("John", "Doe", "Developer", 5);

        // Act
        String employeeString = employee.toString();

        // Assert
        String expectedString = "Employee{id=null, firstName='John', lastName='Doe', description='Developer', jobYears='5'}";
        assertEquals(expectedString, employeeString);
    }
}
