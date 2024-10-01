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

import com.wg.dao.CourseDAO;
import com.wg.model.Course;
import com.wg.services.CourseService;

public class CourseServiceTest {

	@Mock
	private CourseDAO courseDAOMock;

	@InjectMocks
	private CourseService courseService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetCourse_Success() throws ClassNotFoundException, SQLException {
		String courseId = "1";
		Course course = new Course();
		course.setCourseId(courseId);
		when(courseDAOMock.getCourse(courseId)).thenReturn(course);
		// Act
		Course result = courseService.getCourse(courseId);
		// Assert
		assertNotNull(result);
		assertEquals(courseId, result.getCourseId());
		verify(courseDAOMock, times(1)).getCourse(courseId);
	}

	@Test
	public void testGetCourse() throws ClassNotFoundException, SQLException {
		String courseId = "1";
		Course course = new Course();
		course.setCourseId(courseId);
		when(courseDAOMock.getCourse(courseId)).thenReturn(null);
		// Act
		Course result = courseService.getCourse(courseId);
		// Assert
		assertNull(result);
		verify(courseDAOMock, times(1)).getCourse(courseId);
	}

	@Test
	public void testGetCourse_Failure() throws ClassNotFoundException, SQLException {
		String courseId = "2";
		when(courseDAOMock.getCourse(courseId)).thenReturn(null);
		// Act
		Course result = courseService.getCourse(courseId);
		// Assert
		assertNull(result);
		verify(courseDAOMock, times(1)).getCourse(courseId);
	}

	@Test
	public void testGetAllCourses() throws ClassNotFoundException, SQLException {
		// Arrange
		List<Course> courses = new ArrayList<>();
		Course course1 = new Course();
		course1.setCourseId("1");
		courses.add(course1);

		Course course2 = new Course();
		course2.setCourseId("2");
		courses.add(course2);

		when(courseDAOMock.getAllCourses()).thenReturn(courses);
		// Act
		List<Course> result = courseService.getAllCourses();
		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(courseDAOMock, times(1)).getAllCourses();
	}

	@Test
	public void testAddCourse_Success() throws ClassNotFoundException, SQLException {
		Course course = new Course();
		when(courseDAOMock.addCourse(course)).thenReturn(true);
		// Act
		courseService.addCourse(course);
		// Assert
		verify(courseDAOMock, times(1)).addCourse(course);
	}

	@Test
	public void testAddCourse_Failure() throws ClassNotFoundException, SQLException {
		Course course = new Course();
		when(courseDAOMock.addCourse(course)).thenReturn(false);
		// Act
		courseService.addCourse(course);
		// Assert
		verify(courseDAOMock, times(1)).addCourse(course);
	}

	@Test
	public void testDeleteCourse_Success() throws ClassNotFoundException, SQLException {
		String courseId = "1";
		Course course = new Course();
		course.setCourseId(courseId);
		when(courseDAOMock.getCourse(courseId)).thenReturn(course);
		when(courseDAOMock.deleteCourse(courseId)).thenReturn(true);
		// Act
		boolean result = courseService.deleteCourse(courseId);
		// Assert
		assertTrue(result);
		verify(courseDAOMock, times(1)).getCourse(courseId);
		verify(courseDAOMock, times(1)).deleteCourse(courseId);
	}

	@Test
	public void testUpdateCourseSuccess() throws Exception {
		String columnToUpdate = "courseName";
		Course course = new Course();
		when(courseDAOMock.updateCourse(course, columnToUpdate)).thenReturn(true);

		boolean result = courseService.updateCourse(course, columnToUpdate);

		assertTrue(result);
	}

	@Test
	public void testUpdateCourseHandlesException() throws Exception {
		String columnToUpdate = "courseName";
		Course course = new Course();

		doThrow(new SQLException("Database error")).when(courseDAOMock).updateCourse(course, columnToUpdate);

		boolean result = courseService.updateCourse(course, columnToUpdate);

		assertFalse(result);
	}

	@Test
	public void testGetCourseHandlesException() throws Exception {
		String courseId = "course1";

		doThrow(new SQLException("Database error")).when(courseDAOMock).getCourse(courseId);

		Course result = courseService.getCourse(courseId);

		assertNull(result);
	}

	@Test
	public void testGetAllCoursesHandlesException() throws Exception {
		doThrow(new SQLException("Database error")).when(courseDAOMock).getAllCourses();

		List<Course> result = courseService.getAllCourses();

		assertNull(result);
	}

	@Test
	public void testAddCourseHandlesException() throws Exception {
		Course course = new Course();

		doThrow(new SQLException("Database error")).when(courseDAOMock).addCourse(course);

		courseService.addCourse(course);
	}

	@Test
	public void testDeleteCourseHandlesException() throws Exception {
		String courseId = "course1";

		doThrow(new SQLException("Database error")).when(courseDAOMock).getCourse(courseId);

		boolean result = courseService.deleteCourse(courseId);

		assertFalse(result);
	}
}