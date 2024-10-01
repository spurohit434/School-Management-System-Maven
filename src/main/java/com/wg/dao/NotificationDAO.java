package com.wg.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.constants.NotificationConstants;
import com.wg.dao.interfaces.InterfaceNotificationDAO;
import com.wg.model.Notification;

public class NotificationDAO extends GenericDAO<Notification> implements InterfaceNotificationDAO {

	public NotificationDAO() {
		super();
	}

	@Override
	protected Notification mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Notification notification = new Notification();
		notification.setNotificationId(resultSet.getString(NotificationConstants.NOTIFICATION_ID_COLUMN));
		notification.setUserId(resultSet.getString(NotificationConstants.USER_ID_COLUMN));
		notification.setType(resultSet.getString(NotificationConstants.TITLE_COLUMN));
		notification.setDescription(resultSet.getString(NotificationConstants.MESSAGE_COLUMN));
		notification.setDateIssued(resultSet.getDate(NotificationConstants.DATE_COLUMN).toLocalDate());
		return notification;
	}

	public boolean sendNotification(Notification notification) throws ClassNotFoundException, SQLException {
		String sendNotificationQuery = String.format(
				"Insert into Notification (notificationId, userId, type ,description,dateIssued) values ('%s','%s','%s','%s','%s')",
				notification.getNotificationId(), notification.getUserId(), notification.getType(),
				notification.getDescription(), Date.valueOf(notification.getDateIssued()));
		return executeQuery(sendNotificationQuery);
	}

	public List<Notification> readNotifications(String userId) throws ClassNotFoundException, SQLException {
		String readNotificationQuery = String.format("SELECT * FROM Notification WHERE userId = '%s'", userId);
		return executeGetAllQuery(readNotificationQuery);
	}

	public List<Notification> getAllNotifications() throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM " + NotificationConstants.NOTIFICATION_TABLE_NAME;
		return executeGetAllQuery(query);
	}

	public Notification getNotificationById(String notificationId) throws ClassNotFoundException, SQLException {
		String query = String.format("SELECT * FROM %s WHERE %s = '%s'", NotificationConstants.NOTIFICATION_TABLE_NAME,
				NotificationConstants.NOTIFICATION_ID_COLUMN, notificationId);
		return executeGetQuery(query);
	}
}