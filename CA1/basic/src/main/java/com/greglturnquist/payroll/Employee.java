/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity // <1>
class Employee {

	private @Id @GeneratedValue Long id; // <2>
	private String firstName;
	private String lastName;
	private String description;
	private int jobYears;

	private String jobTitle;
	protected Employee() {}
	public Employee(String firstName, String lastName, String description, int jobYears, String jobTitle) throws InstantiationException {
		if(firstName == null || lastName == null || description == null || jobYears < 0 || firstName.isEmpty() || lastName.isEmpty() || description.isEmpty())
			throw new InstantiationException("Please enter a valid first name, last name, description and job years.");
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobYears = jobYears;
		this.jobTitle = jobTitle;
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
				Objects.equals(jobYears, employee.jobYears) &&
				Objects.equals(jobTitle, employee.jobTitle);

	}


	@Override
	public int hashCode() {

		return Objects.hash(id, firstName, lastName, description,jobYears,jobTitle);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getJobYears() {
		return jobYears;
	}

	public void setJobYears(int jobYears) {
		this.jobYears = jobYears;
	}
	public String getJobTitle() {return jobTitle;}
	public void setJobTitle(String jobTitle) {this.jobTitle = jobTitle;}
	@Override
	public String toString() {
		return "Employee{" +
			"id=" + this.id +
			", firstName='" + this.firstName + '\'' +
			", lastName='" + this.lastName + '\'' +
			", description='" + this.description + '\'' +
			", jobYears='" + this.jobYears + '\'' +
			", jobTitle='" + this.jobTitle + "'}";
	}
}
// end::code[]
