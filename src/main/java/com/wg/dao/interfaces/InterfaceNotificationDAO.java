package com.wg.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.wg.model.Notification;

public interface InterfaceNotificationDAO {

	public boolean sendNotification(Notification notification) throws ClassNotFoundException, SQLException;

	public List<Notification> readNotifications(String userId) throws ClassNotFoundException, SQLException;

	public List<Notification> getAllNotifications() throws ClassNotFoundException, SQLException;

	public Notification getNotificationById(String notificationId) throws ClassNotFoundException, SQLException;
}