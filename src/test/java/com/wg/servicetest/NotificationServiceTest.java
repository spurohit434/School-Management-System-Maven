package com.wg.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dao.NotificationDAO;
import com.wg.model.Notification;
import com.wg.services.NotificationService;

public class NotificationServiceTest {

	@Mock
	private NotificationDAO notificationDAOMock;

	@InjectMocks
	private NotificationService notificationService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSendNotification_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		Notification notification = new Notification();
		when(notificationDAOMock.sendNotification(notification)).thenReturn(true);

		// Act
		boolean result = notificationService.sendNotification(notification);

		// Assert
		assertTrue(result, "Notification should be sent successfully");
		verify(notificationDAOMock).sendNotification(notification);
	}

	@Test
	public void testSendNotification_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		Notification notification = new Notification();
		when(notificationDAOMock.sendNotification(notification)).thenReturn(false);

		// Act
		boolean result = notificationService.sendNotification(notification);

		// Assert
		assertFalse(result, "Notification should not be sent");
		verify(notificationDAOMock).sendNotification(notification);
	}

	@Test
	public void testReadNotifications_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		List<Notification> expectedNotifications = new ArrayList<>();
		when(notificationDAOMock.readNotifications(userId)).thenReturn(expectedNotifications);

		// Act
		List<Notification> actualNotifications = notificationService.readNotifications(userId);

		// Assert
		assertEquals(expectedNotifications, actualNotifications, "The notifications list should match");
		verify(notificationDAOMock).readNotifications(userId);
	}

	@Test
	public void testReadNotifications_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(notificationDAOMock.readNotifications(userId)).thenReturn(null);

		// Act
		List<Notification> notifications = notificationService.readNotifications(userId);

		// Assert
		assertNull(notifications, "The notifications list should be null on failure");
		verify(notificationDAOMock).readNotifications(userId);
	}
}
