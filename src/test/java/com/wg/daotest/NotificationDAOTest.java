package com.wg.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.constants.NotificationConstants;
import com.wg.dao.NotificationDAO;
import com.wg.model.Notification;

public class NotificationDAOTest {

	@InjectMocks
	private NotificationDAO notificationDAO;

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement preparedStatement;

	@Mock
	private ResultSet resultSet;

	@BeforeEach
	public void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	public void testSendNotification() throws SQLException, ClassNotFoundException {
		Notification notification = new Notification();
		notification.setNotificationId("N101");
		notification.setUserId("U101");
		notification.setType("Info");
		notification.setDescription("Sample notification");
		notification.setDateIssued(java.time.LocalDate.now());

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = notificationDAO.sendNotification(notification);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testReadNotifications() throws SQLException, ClassNotFoundException {
		Notification notification1 = new Notification();
		notification1.setNotificationId("N101");
		notification1.setUserId("U101");
		notification1.setType("Info");
		notification1.setDescription("Notification 1");
		notification1.setDateIssued(java.time.LocalDate.now());

		Notification notification2 = new Notification();
		notification2.setNotificationId("N102");
		notification2.setUserId("U101");
		notification2.setType("Alert");
		notification2.setDescription("Notification 2");
		notification2.setDateIssued(java.time.LocalDate.now().plusDays(1));

		List<Notification> notificationList = new ArrayList<>();
		notificationList.add(notification1);
		notificationList.add(notification2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString(NotificationConstants.NOTIFICATION_ID_COLUMN)).thenReturn("N101").thenReturn("N102");
		when(resultSet.getString(NotificationConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U101");
		when(resultSet.getString(NotificationConstants.TITLE_COLUMN)).thenReturn("Info").thenReturn("Alert");
		when(resultSet.getString(NotificationConstants.MESSAGE_COLUMN)).thenReturn("Notification 1")
				.thenReturn("Notification 2");
		when(resultSet.getDate(NotificationConstants.DATE_COLUMN)).thenReturn(Date.valueOf(java.time.LocalDate.now()))
				.thenReturn(Date.valueOf(java.time.LocalDate.now().plusDays(1)));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Notification> result = notificationDAO.readNotifications("U101");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("N101", result.get(0).getNotificationId());
		assertEquals("N102", result.get(1).getNotificationId());
	}

	@Test
	public void testGetAllNotifications() throws SQLException, ClassNotFoundException {
		Notification notification1 = new Notification();
		notification1.setNotificationId("N101");
		notification1.setUserId("U101");
		notification1.setType("Info");
		notification1.setDescription("Notification 1");
		notification1.setDateIssued(java.time.LocalDate.now());

		Notification notification2 = new Notification();
		notification2.setNotificationId("N102");
		notification2.setUserId("U102");
		notification2.setType("Alert");
		notification2.setDescription("Notification 2");
		notification2.setDateIssued(java.time.LocalDate.now().plusDays(1));

		List<Notification> notificationList = new ArrayList<>();
		notificationList.add(notification1);
		notificationList.add(notification2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString(NotificationConstants.NOTIFICATION_ID_COLUMN)).thenReturn("N101").thenReturn("N102");
		when(resultSet.getString(NotificationConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U102");
		when(resultSet.getString(NotificationConstants.TITLE_COLUMN)).thenReturn("Info").thenReturn("Alert");
		when(resultSet.getString(NotificationConstants.MESSAGE_COLUMN)).thenReturn("Notification 1")
				.thenReturn("Notification 2");
		when(resultSet.getDate(NotificationConstants.DATE_COLUMN)).thenReturn(Date.valueOf(java.time.LocalDate.now()))
				.thenReturn(Date.valueOf(java.time.LocalDate.now().plusDays(1)));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Notification> result = notificationDAO.getAllNotifications();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("N101", result.get(0).getNotificationId());
		assertEquals("N102", result.get(1).getNotificationId());
	}

	@Test
	public void testGetNotificationById() throws SQLException, ClassNotFoundException {
		Notification notification = new Notification();
		notification.setNotificationId("N101");
		notification.setUserId("U101");
		notification.setType("Info");
		notification.setDescription("Sample notification");
		notification.setDateIssued(java.time.LocalDate.now());

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getString(NotificationConstants.NOTIFICATION_ID_COLUMN)).thenReturn("N101");
		when(resultSet.getString(NotificationConstants.USER_ID_COLUMN)).thenReturn("U101");
		when(resultSet.getString(NotificationConstants.TITLE_COLUMN)).thenReturn("Info");
		when(resultSet.getString(NotificationConstants.MESSAGE_COLUMN)).thenReturn("Sample notification");
		when(resultSet.getDate(NotificationConstants.DATE_COLUMN)).thenReturn(Date.valueOf(java.time.LocalDate.now()));

		// Mock PreparedStatement behavior
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		// Execute the method under test
		Notification result = notificationDAO.getNotificationById("N101");

		// Assert the results
		assertNotNull(result);
		assertEquals("N101", result.getNotificationId());
		assertEquals("U101", result.getUserId());
		assertEquals("Info", result.getType());
		assertEquals("Sample notification", result.getDescription());
		assertEquals(java.time.LocalDate.now(), result.getDateIssued());
	}
}
