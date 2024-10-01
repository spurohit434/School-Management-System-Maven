package com.wg.controller;

import java.util.List;

import com.wg.model.Notification;
import com.wg.services.NotificationService;

public class NotificationController {
	private final NotificationService notificationService = new NotificationService();

	public NotificationController() {
	}

	public boolean sendNotification(Notification notification) {
		return notificationService.sendNotification(notification);
	}

	public List<Notification> readNotifications(String userId) {
		return notificationService.readNotifications(userId);
	}
}