package com.wg.model;

import java.time.LocalDate;

public class Issue {

	private String issueID;
	private String message;
	private String userId;
	private IssuesStatus status;
	private LocalDate createdAt;

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Issue() {
	}

	public Issue(String issueID, String message, String userId, IssuesStatus status) {
		this.issueID = issueID;
		this.message = message;
		this.userId = userId;
		this.status = status;
	}

	public String getIssueID() {
		return issueID;
	}

	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public IssuesStatus getStatus() {
		return status;
	}

	public void setStatus(IssuesStatus status) {
		this.status = status;
	}

//	@Override
//	public String toString() {
//		return "Issue{" + "issueID='" + issueID + '\'' + ", message='" + message + '\'' + ", userId='" + userId + '\''
//				+ ", status=" + status + '}';
//	}

	@Override
	public String toString() {
		return "Issue{" + "message='" + message + '\'' + ", userId='" + userId + '\'' + ", status=" + status + '}';
	}

}
