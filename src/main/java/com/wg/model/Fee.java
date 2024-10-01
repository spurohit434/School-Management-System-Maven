package com.wg.model;

import java.time.LocalDate;

public class Fee {
	String studentId;
	double feeAmount;
	LocalDate deadline;

	public String getStudentId() {
		return studentId;
	}

	public Fee(String studentId, double feeAmount, LocalDate deadline, double fine) {
		super();
		this.studentId = studentId;
		this.feeAmount = feeAmount;
		this.deadline = deadline;
		this.fine = fine;
	}

	public Fee() {
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	double fine;
}
