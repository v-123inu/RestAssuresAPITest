package com.POJO;

import java.io.Serializable;

public class CreatePOJO implements Serializable {
	private static final long serialVersionUID = -6470090944414208496L;
	
	private String name;
	private String salary;
	private String age;
	
	public CreatePOJO(String name, String salary, String age) {
		
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "createPOJO [name=" + this.name + ", salary=" + this.salary + ", age=" + this.age + "]";
	}

	
}
