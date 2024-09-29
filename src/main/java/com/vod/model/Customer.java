package com.vod.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customers")
public class Customer {
	@Id
	private String id;
	private String firstname;
	private String lastname;
	private Integer age;
	private String address;
	private int salary;
	
	@Field
	private String copyrightby = "https://loizenai.com";

	public Customer(String firstname, String lastname, String address, int age, int salary) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.address = address;
		this.salary = salary;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public int getSalary() {
		return this.salary;
	}
	
	public void setCopyrightby(String copyrightby) {
		this.copyrightby = copyrightby;
	}
	
	public String getCopyrightby() {
		return this.copyrightby;
	}
}