package com.wg.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.wg.dao.CourseMarksDAO;
import com.wg.model.CourseMarks;
import com.wg.services.CourseMarksService;

public class CourseMarksServiceTest {

	@Mock
	private CourseMarksDAO courseMarksDAOMock;

	@InjectMocks
	private CourseMarksService courseMarksService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddMarks_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		CourseMarks courseMarks = new CourseMarks();
		when(courseMarksDAOMock.addMarks(courseMarks)).thenReturn(true);

		// Act
		courseMarksService.addMarks(courseMarks);

		// Assert
		verify(courseMarksDAOMock).addMarks(courseMarks);
	}

	@Test
	public void testAddMarks_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		CourseMarks courseMarks = new CourseMarks();
		when(courseMarksDAOMock.addMarks(courseMarks)).thenReturn(false);

		// Act
		courseMarksService.addMarks(courseMarks);

		// Assert
		verify(courseMarksDAOMock).addMarks(courseMarks);
	}

	@Test
	public void testCheckMarks_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		List<CourseMarks> expectedMarks = new ArrayList<>();
		when(courseMarksDAOMock.checkMarks(userId)).thenReturn(expectedMarks);

		// Act
		List<CourseMarks> actualMarks = courseMarksService.checkMarks(userId);

		// Assert
		assertEquals(expectedMarks, actualMarks, "The course marks list should match");
		verify(courseMarksDAOMock).checkMarks(userId);
	}

	@Test
	public void testCheckMarks_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(courseMarksDAOMock.checkMarks(userId)).thenReturn(null);

		// Act
		List<CourseMarks> marks = courseMarksService.checkMarks(userId);

		// Assert
		assertNull(marks, "The course marks list should be null on failure");
		verify(courseMarksDAOMock).checkMarks(userId);
	}
}