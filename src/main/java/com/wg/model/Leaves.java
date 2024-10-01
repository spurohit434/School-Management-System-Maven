package com.wg.model;

import java.time.LocalDate;

public class Leaves {
	String leaveId;
	String userId;
	String content;
	LocalDate startDate;
	LocalDate endDate;
	LeavesStatus status;

	public Leaves(String leaveId, String userId, String content, LocalDate startDate, LocalDate endDate,
			LeavesStatus status) {
		super();
		this.leaveId = leaveId;
		this.userId = userId;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public Leaves() {
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LeavesStatus getStatus() {
		return status;
	}

	public void setStatus(LeavesStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Leaves{" + "leaveId='" + leaveId + '\'' + ", content='" + content + '\'' + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", status=" + status + '}';
	}
}