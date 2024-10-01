package com.wg.model;

import java.time.LocalDate;

public class Notification {
	String notificationId;
	String userId;
	String description;
	String type;
	LocalDate dateIssued;

	public Notification(String notificationId, String userId, String description, String type, LocalDate dateIssued) {
		this.notificationId = notificationId;
		this.userId = userId;
		this.description = description;
		this.dateIssued = dateIssued;
		this.type = type;
	}

	public Notification() {
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(LocalDate dateIssued) {
		this.dateIssued = dateIssued;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Notification{" + "notificationId='" + notificationId + '\'' + ", userId='" + userId + '\''
				+ ", description='" + description + '\'' + ", dateIssued=" + dateIssued + ", type='" + type + '\''
				+ '}';
	}

}
