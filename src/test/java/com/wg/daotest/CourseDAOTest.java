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

import com.wg.constants.CourseConstants;
import com.wg.dao.CourseDAO;
import com.wg.model.Course;

public class CourseDAOTest {

	@InjectMocks
	private CourseDAO courseDAO;

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
	public void testAddCourse() throws SQLException, ClassNotFoundException {
		Course course = new Course();
		course.setCourseId("C101");
		course.setCourseName("Mathematics");
		course.setStandard(10);

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = courseDAO.addCourse(course);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testGetCourse() throws SQLException, ClassNotFoundException {
		Course course = new Course();
		course.setCourseId("C101");
		course.setCourseName("Mathematics");
		course.setStandard(10);

		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getString(CourseConstants.COURSE_ID)).thenReturn("C101");
		when(resultSet.getString(CourseConstants.COURSE_NAME)).thenReturn("Mathematics");
		when(resultSet.getInt(CourseConstants.STANDARD)).thenReturn(10);

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		Course result = courseDAO.getCourse("C101");

		assertNotNull(result);
		assertEquals("C101", result.getCourseId());
		assertEquals("Mathematics", result.getCourseName());
		assertEquals(10, result.getStandard());
	}

	@Test
	public void testGetAllCourses() throws SQLException, ClassNotFoundException {
		Course course1 = new Course();
		course1.setCourseId("C101");
		course1.setCourseName("Mathematics");
		course1.setStandard(10);

		Course course2 = new Course();
		course2.setCourseId("C102");
		course2.setCourseName("Physics");
		course2.setStandard(10);

		List<Course> courseList = new ArrayList<>();
		courseList.add(course1);
		courseList.add(course2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString(CourseConstants.COURSE_ID)).thenReturn("C101").thenReturn("C102");
		when(resultSet.getString(CourseConstants.COURSE_NAME)).thenReturn("Mathematics").thenReturn("Physics");
		when(resultSet.getInt(CourseConstants.STANDARD)).thenReturn(10).thenReturn(10);

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Course> result = courseDAO.getAllCourses();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("C101", result.get(0).getCourseId());
		assertEquals("C102", result.get(1).getCourseId());
	}

	@Test
	public void testUpdateCourse() throws SQLException, ClassNotFoundException {
		Course course = new Course();
		course.setCourseId("C101");
		course.setCourseName("Mathematics");

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = courseDAO.updateCourse(course, "CourseName");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testDeleteCourse() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = courseDAO.deleteCourse("C101");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}
}
