package com.wg.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.constants.UserConstants;
import com.wg.dao.UserDAO;
import com.wg.model.Role;
import com.wg.model.User;

public class UserDAOTest {

	@InjectMocks
	private UserDAO userDAO;

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
	public void testAddUser() throws SQLException, ClassNotFoundException {
		User user = new User();
		user.setUserId("1");
		user.setUsername("john_doe");
		user.setName("John Doe");
		user.setAge(30);
		user.setPassword("password");
		user.setEmail("john@example.com");
		user.setRole(Role.Student);
		user.setDOB(java.time.LocalDate.now());
		user.setContactNumber("1234567890");
		user.setStandard(10);
		user.setGender("Male");
		user.setRollNo("123");
		user.setMentorOf(0);

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = userDAO.addUser(user);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testGetUserById() throws SQLException, ClassNotFoundException {
		User user = new User();
		user.setUserId("1");
		user.setUsername("john_doe");
		user.setName("John Doe");
		user.setAge(30);
		user.setPassword("password");
		user.setEmail("john@example.com");
		user.setRole(Role.Student);
		user.setDOB(java.time.LocalDate.now());
		user.setContactNumber("1234567890");
		user.setStandard(10);
		user.setGender("Male");
		user.setRollNo("123");
		user.setMentorOf(0);

		when(resultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
		when(resultSet.getString(UserConstants.USER_ID_COLUMN)).thenReturn("1");
		when(resultSet.getString(UserConstants.USERNAME_COLUMN)).thenReturn("john_doe");
		when(resultSet.getString(UserConstants.NAME_COLUMN)).thenReturn("John Doe");
		when(resultSet.getInt(UserConstants.AGE_COLUMN)).thenReturn(30);
		when(resultSet.getString(UserConstants.PASSWORD_COLUMN)).thenReturn("password");
		when(resultSet.getString(UserConstants.EMAIL_COLUMN)).thenReturn("john@example.com");
		when(resultSet.getString(UserConstants.ROLE_COLUMN)).thenReturn("Student");
		when(resultSet.getDate(UserConstants.DOB_COLUMN)).thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getString(UserConstants.CONTACT_NO_COLUMN)).thenReturn("1234567890");
		when(resultSet.getInt(UserConstants.STANDARD_COLUMN)).thenReturn(10);
		when(resultSet.getString(UserConstants.GENDER_COLUMN)).thenReturn("Male");
		when(resultSet.getString(UserConstants.ROLL_NO_COLUMN)).thenReturn("123");
		when(resultSet.getInt(UserConstants.MENTOR_OF_COLUMN)).thenReturn(0);

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		User result = userDAO.getUserById("1");

		assertNotNull(result);
		assertEquals("1", result.getUserId());
		assertEquals("john_doe", result.getUsername());
		assertEquals("John Doe", result.getName());
		assertEquals(30, result.getAge());
		assertEquals("password", result.getPassword());
		assertEquals("john@example.com", result.getEmail());
		assertEquals(Role.Student, result.getRole());
		assertEquals(java.time.LocalDate.now(), result.getDOB());
		assertEquals("1234567890", result.getContactNumber());
		assertEquals(10, result.getStandard());
		assertEquals("Male", result.getGender());
		assertEquals("123", result.getRollNo());
		assertEquals(0, result.getMentorOf());
	}

	@Test
	public void testGetAllUsers() throws SQLException, ClassNotFoundException {
//		User user1 = new User();
//		user1.setUserId("1");
//		user1.setName("John Moe");
//		user1.setAge(30);
//		user1.setPassword("password");
//		user1.setEmail("john@example.com");
//		user1.setRole(Role.Student);
//		user1.setDOB(java.time.LocalDate.now());
//		user1.setContactNumber("1234567890");
//		user1.setStandard(10);
//		user1.setGender("Male");
//		user1.setRollNo("123");
//		user1.setMentorOf(0);
//
//		User user2 = new User();
//		user2.setUserId("2");
//		user2.setName("John Doe");
//		user2.setAge(30);
//		user2.setPassword("password");
//		user2.setEmail("john@example.com");
//		user2.setRole(Role.Student);
//		user2.setDOB(java.time.LocalDate.now());
//		user2.setContactNumber("1234567890");
//		user2.setStandard(10);
//		user2.setGender("Male");
//		user2.setRollNo("123");
//		user2.setMentorOf(0);
//
//		List<User> userList = new ArrayList<>();
//		userList.add(user1);
//		userList.add(user2);

		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(resultSet.getString(UserConstants.USER_ID_COLUMN)).thenReturn("1").thenReturn("2").thenReturn(null);
		when(resultSet.getString(UserConstants.NAME_COLUMN)).thenReturn("John Moe").thenReturn("John Doe")
				.thenReturn(null);
		when(resultSet.getInt(UserConstants.AGE_COLUMN)).thenReturn(30).thenReturn(30);
		when(resultSet.getString(UserConstants.PASSWORD_COLUMN)).thenReturn("password").thenReturn("password");
		when(resultSet.getString(UserConstants.EMAIL_COLUMN)).thenReturn("john@example.com")
				.thenReturn("john@example.com");
		when(resultSet.getString(UserConstants.ROLE_COLUMN)).thenReturn("Student").thenReturn("Student");
		when(resultSet.getDate(UserConstants.DOB_COLUMN)).thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getString(UserConstants.CONTACT_NO_COLUMN)).thenReturn("1234567890").thenReturn("1234567890");
		when(resultSet.getInt(UserConstants.STANDARD_COLUMN)).thenReturn(10).thenReturn(10);
		when(resultSet.getString(UserConstants.GENDER_COLUMN)).thenReturn("Male").thenReturn("Male");
		when(resultSet.getString(UserConstants.ROLL_NO_COLUMN)).thenReturn("123").thenReturn("123");
		when(resultSet.getInt(UserConstants.MENTOR_OF_COLUMN)).thenReturn(0).thenReturn(0);

		List<User> result = userDAO.getAllUser();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("1", result.get(0).getUserId());
		assertEquals("2", result.get(1).getUserId());
	}

	@Test
	public void testUpdateUser() throws SQLException, ClassNotFoundException {
		User user = new User();
		user.setUserId("1");
		user.setName("John Doe");

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = userDAO.updateUser(user, "1", "name");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testDeleteUser() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = userDAO.deleteUser("1");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}
}
