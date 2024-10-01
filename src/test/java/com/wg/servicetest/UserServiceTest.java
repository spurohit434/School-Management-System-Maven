package com.wg.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
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

import com.wg.dao.UserDAO;
import com.wg.helper.PasswordUtil;
import com.wg.model.Role;
import com.wg.model.User;
import com.wg.services.UserService;

public class UserServiceTest {

	@Mock
	private UserDAO userDAOMock;

	@Mock
	private User user1;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddUser_Success() throws ClassNotFoundException, SQLException {
		// Arrange
		User user = new User();
		when(userDAOMock.addUser(user)).thenReturn(true);
		// Act
		boolean result = userService.addUser(user);
		// Assert
		assertTrue(result);
		verify(userDAOMock, times(1)).addUser(user);
	}

	@Test
	public void testAddUser_Failure() throws ClassNotFoundException, SQLException {
		// Arrange
		User user = new User();
		when(userDAOMock.addUser(user)).thenReturn(false);
		// Act
		boolean result = userService.addUser(user);
		// Assert
		assertFalse(result);
		verify(userDAOMock, times(1)).addUser(user);
	}

	@Test
	public void testGetUserById_Success() throws ClassNotFoundException, SQLException {
		// Arrange
		String userId = "U123";
		User user = new User();
		when(userDAOMock.getUserById(userId)).thenReturn(user);
		// Act
		User result = userService.getUserById(userId);
		// Assert
		assertNotNull(result);
		verify(userDAOMock, times(1)).getUserById(userId);
	}

	@Test
	public void testGetUserById_Failure() throws ClassNotFoundException, SQLException {
		// Arrange
		String userId = "U123";
		when(userDAOMock.getUserById(userId)).thenReturn(null);
		// Act
		User result = userService.getUserById(userId);
		// Assert
		assertNull(result);
		verify(userDAOMock, times(1)).getUserById(userId);
	}

	@Test
	public void testGetClassDetails_Success() throws ClassNotFoundException, SQLException {
		// Arrange
		int standard = 10;
		List<User> userList = new ArrayList<>();
		User user = new User();
		userList.add(user);
		when(userDAOMock.getClassDetails(standard)).thenReturn(userList);
		// Act
		List<User> result = userService.getClassDetails(standard);
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(userDAOMock, times(1)).getClassDetails(standard);
	}

	@Test
	public void testGetClassDetails_Failure() throws ClassNotFoundException, SQLException {
		// Arrange
		int standard = 10;
		when(userDAOMock.getClassDetails(standard)).thenReturn(null);
		// Act
		List<User> result = userService.getClassDetails(standard);
		// Assert
		assertNull(result);
		verify(userDAOMock, times(1)).getClassDetails(standard);
	}

	@Test
	public void testDeleteUser_Failure_UserNotFound() throws ClassNotFoundException, SQLException {
		// Arrange
		String userId = "U123";
		when(userDAOMock.getUserById(userId)).thenReturn(null);
		// Act
		boolean result = userService.deleteUser(userId);
		// Assert
		assertFalse(result);
		verify(userDAOMock, times(1)).getUserById(userId);
	}

	@Test
	public void testDeleteUser_Failure_Admin() throws ClassNotFoundException, SQLException {
		// Arrange
		String userId = "U123";
		User user = new User();
		user.setRole(Role.Admin);
		when(userDAOMock.getUserById(userId)).thenReturn(user);
		// Act
		boolean result = userService.deleteUser(userId);
		// Assert
		assertFalse(result);
		verify(userDAOMock, times(1)).getUserById(userId);
	}

	@Test
	public void testAuthenticateUserSuccess() throws Exception {
		// Arrange
		String username = "user";
		String password = "pass";
		String userId = "1234";
		User user = new User();
		user.setUsername(username);
		user.setUserId(userId);
		PasswordUtil passwordUtil = new PasswordUtil();
		user.setPassword(passwordUtil.hashPassword(password));
		when(userDAOMock.getUserByUsername(username)).thenReturn(user);
		// Act
		User authenticatedUser = userService.authenticateUser(username, password);
		// Assert
		assertNotNull(authenticatedUser);
		assertEquals(user, authenticatedUser);
		verify(userDAOMock).getUserByUsername(username);
	}

	@Test
	public void testAuthenticateUser_Failure_InvalidCredentials() throws ClassNotFoundException, SQLException {
		// Arrange
		String username = "user";
		when(userDAOMock.getUserByUsername(username)).thenReturn(null);
		// Act
		User user = userService.getUserByUsername(username);
		// Assert
		assertEquals(null, user);
		verify(userDAOMock, times(1)).getUserByUsername(username);
	}

	@Test
	public void testGetAllUser_Success() throws ClassNotFoundException, SQLException {
		// Arrange
		List<User> userList = new ArrayList<>();
		User user = new User();
		userList.add(user);
		when(userDAOMock.getAllUser()).thenReturn(userList);
		// Act
		List<User> result = userService.getAllUser();
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(userDAOMock, times(1)).getAllUser();
	}

	@Test
	public void testGetAllUser_Failure() throws ClassNotFoundException, SQLException {
		// Arrange
		when(userDAOMock.getAllUser()).thenReturn(null);
		// Act
		List<User> result = userService.getAllUser();
		// Assert
		assertNull(result);
		verify(userDAOMock, times(1)).getAllUser();
	}

	@Test
	public void testGetUserByUsername_Success() throws ClassNotFoundException, SQLException {
		// Arrange
		String username = "user";
		User user = new User();
		when(userDAOMock.getUserByUsername(username)).thenReturn(user);
		// Act
		User user1 = userService.getUserByUsername(username);
		// Assert
		assertNotNull(user1);
		verify(userDAOMock, times(1)).getUserByUsername(username);
	}

	@Test
	public void testGetUserByUsername_Failure() throws ClassNotFoundException, SQLException {
		String username = "user";
		when(userDAOMock.getUserByUsername(username)).thenReturn(null);

		User result = userService.getUserByUsername(username);

		assertNull(result);
		verify(userDAOMock, times(1)).getUserByUsername(username);
	}

	@Test
	public void testUpdateUser_Success() throws ClassNotFoundException, SQLException {
		User user = new User();
		String userId = "U123";
		String columnToUpdate = "username";
		when(userDAOMock.updateUser(user, userId, columnToUpdate)).thenReturn(true);

		// Act
		boolean result = userService.updateUser(user, userId, columnToUpdate);
		assertEquals(result, true); // Assert
		verify(userDAOMock, times(1)).updateUser(user, userId, columnToUpdate);
	}

	@Test
	public void testUpdateUser_Failure() throws ClassNotFoundException, SQLException {
		User user = new User();
		String userId = "U123";
		String columnToUpdate = "username";
		when(userDAOMock.updateUser(user, userId, columnToUpdate)).thenReturn(false);

		boolean result = userService.updateUser(user, userId, columnToUpdate);
		assertEquals(result, false);

		verify(userDAOMock, times(1)).updateUser(user, userId, columnToUpdate);
	}

	@Test
	public void testAuthenticateUserWithNullUserId() throws ClassNotFoundException, SQLException {
		// Arrange
		String username = "ram";
		String password = "qwerty";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		when(userDAOMock.getUserByUsername(username)).thenReturn(null);
		// Act
		User result = userService.authenticateUser(username, password);
		// Assert
		assertNull(result);
		verify(userDAOMock, times(1)).getUserByUsername(username);
	}

	@Test
	public void testDeleteUserHandlesException() throws Exception {
		// Arrange
		String userId = "123";
		doThrow(new SQLException("Database error")).when(userDAOMock).getUserById(userId);
		// Act
		boolean result = userService.deleteUser(userId);
		// Assert
		assertFalse(result);
	}
}