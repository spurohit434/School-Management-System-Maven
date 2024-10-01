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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dao.CourseMarksDAO;
import com.wg.model.CourseMarks;

public class CourseMarksDAOTest {

	@InjectMocks
	private CourseMarksDAO courseMarksDAO;

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
	public void testAddMarks() throws SQLException, ClassNotFoundException {
		CourseMarks courseMarks = new CourseMarks();
		courseMarks.setUserId("U101");
		courseMarks.setCourseId("C101");
		courseMarks.setMarks(85.5);
		courseMarks.setStandard(12);

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = courseMarksDAO.addMarks(courseMarks);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testCheckMarks() throws SQLException, ClassNotFoundException {
		CourseMarks courseMarks1 = new CourseMarks();
		courseMarks1.setUserId("U101");
		courseMarks1.setCourseId("C101");
		courseMarks1.setMarks(85.5);
		courseMarks1.setStandard(12);

		CourseMarks courseMarks2 = new CourseMarks();
		courseMarks2.setUserId("U101");
		courseMarks2.setCourseId("C102");
		courseMarks2.setMarks(90.0);
		courseMarks2.setStandard(11);

		List<CourseMarks> courseMarksList = new ArrayList<>();
		courseMarksList.add(courseMarks1);
		courseMarksList.add(courseMarks2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString("userId")).thenReturn("U101").thenReturn("U101");
		when(resultSet.getString("courseId")).thenReturn("C101").thenReturn("C102");
		when(resultSet.getDouble("marks")).thenReturn(85.5).thenReturn(90.0);
		when(resultSet.getInt("standard")).thenReturn(11).thenReturn(12);

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<CourseMarks> result = courseMarksDAO.checkMarks("U101");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("U101", result.get(0).getUserId());
		assertEquals("C101", result.get(0).getCourseId());
		assertEquals(85.5, result.get(0).getMarks());
		assertEquals(11, result.get(0).getStandard());

		assertEquals("U101", result.get(1).getUserId());
		assertEquals("C102", result.get(1).getCourseId());
		assertEquals(90.0, result.get(1).getMarks());
		assertEquals(12, result.get(1).getStandard());
	}
}
