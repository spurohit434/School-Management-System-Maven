package com.wg.model;

import java.time.LocalDate;
import java.util.List;

public class User {
	private String userId;
	private String name;
	private LocalDate dob;
	private String contactNumber;
	private Role role;
	private String password;
	private int standard;
	private String address;
	private String username;
	private int age;
	private String email;
	private String gender;
	private String rollNo;
	private List<Integer> assignedToStandard;
	private int mentorOf;

	// Constructors
	public User() {
	}

	public User(String userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public User(String userId, String name, String email, String password) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(String userId, String name, int age, String password, String email, Role role) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.age = age;
		this.password = password;
		this.role = role;
	}

	public User(String userId, String name, String email, int age, int standard) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.age = age;
		this.standard = standard;
	}

	public User(String userId, String username, String name, int age, String password, String email, Role role) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.email = email;
		this.age = age;
		this.password = password;
		this.role = role;
	}

	public User(String userId, String username, String name, int age, String password, String email, Role role,
			LocalDate date, String contactNumber, int standard, String gender, String rollNo, int mentorOf) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.age = age;
		this.password = password;
		this.email = email;
		this.role = role;
		this.dob = date;
		this.contactNumber = contactNumber;
		this.standard = standard;
		this.gender = gender;
		this.rollNo = rollNo;
		this.mentorOf = mentorOf;
	}

	public User(String userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	// getter setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDOB() {
		return dob;
	}

	public void setDOB(LocalDate dateOfBirth) {
		this.dob = dateOfBirth;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public int getMentorOf() {
		return mentorOf;
	}

	public void setMentorOf(int mentorOf) {
		this.mentorOf = mentorOf;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	@Override
//	public String toString() {
//		return "User{" + "name='" + name + '\'' + ", username='" + username + '\'' + ", dateOfBirth='" + dob + '\''
//				+ ", contactNumber='" + contactNumber + '\'' + ", role=" + role + ", password='" + password + '\''
//				+ ", standard=" + standard + ", age=" + age + ", email='" + email + '\'' + ", gender='" + gender + '\''
//				+ ", rollNo='" + rollNo + '\'' + ", mentorOf=" + mentorOf + ", address='" + address + '\'' + '}';
//	}
}
