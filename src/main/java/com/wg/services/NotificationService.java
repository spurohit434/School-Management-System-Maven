package com.wg.services;

import java.sql.SQLException;
import java.util.List;

import com.wg.dao.NotificationDAO;
import com.wg.dao.interfaces.InterfaceNotificationDAO;
import com.wg.model.Notification;

public class NotificationService {
	private InterfaceNotificationDAO notificationDAO = new NotificationDAO();

	public NotificationService() {
	}

//	public NotificationService(NotificationDAO notificationDAO) {
//		this.notificationDAO = notificationDAO;
//	}

	public boolean sendNotification(Notification notification) {
		boolean sendStatus = false;
		try {
			sendStatus = notificationDAO.sendNotification(notification);
			return sendStatus;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return sendStatus;
	}

	public List<Notification> readNotifications(String userId) {
		List<Notification> notificationList = null;
		try {
			notificationList = notificationDAO.readNotifications(userId);
			return notificationList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return notificationList;
	}
}
