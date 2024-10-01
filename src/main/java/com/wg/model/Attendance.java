package com.wg.model;

import java.time.LocalDate;

public class Attendance {
	String studentId;
	int standard;
	LocalDate date;
	Status status;

	public Attendance() {

	}

	public Attendance(String studentId, int standard, LocalDate date, Status status) {
		this.studentId = studentId;
		this.standard = standard;
		this.date = date;
		this.status = status;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "standard=" + standard + ", date=" + date + ", status=" + status + '}';
	}
}