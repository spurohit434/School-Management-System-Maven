package com.wg.model;

public class Course {
	String courseId;
	String CourseName;
	int standard;

	public Course(String courseId, String courseName, int standard) {
		super();
		this.courseId = courseId;
		CourseName = courseName;
		this.standard = standard;
	}

	public Course() {
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	@Override
	public String toString() {
		return "courseName='" + CourseName + '\'' + ", standard=" + standard;
	}
}