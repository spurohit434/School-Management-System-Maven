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

import com.wg.constants.AttendanceConstants;
import com.wg.dao.AttendanceDAO;
import com.wg.model.Attendance;
import com.wg.model.Status;

public class AttendanceDAOTest {

	@InjectMocks
	private AttendanceDAO attendanceDAO;

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
	public void testAddAttendance() throws SQLException, ClassNotFoundException {
		Attendance attendance = new Attendance();
		attendance.setStudentId("S123");
		attendance.setStandard(10);
		attendance.setDate(java.time.LocalDate.now());
		attendance.setStatus(Status.P);

		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = attendanceDAO.addAttendance(attendance);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testViewAttendanceByStandard() throws SQLException, ClassNotFoundException {
		Attendance attendance1 = new Attendance();
		attendance1.setStudentId("S123");
		attendance1.setStandard(10);
		attendance1.setDate(java.time.LocalDate.now());
		attendance1.setStatus(Status.P);

		Attendance attendance2 = new Attendance();
		attendance2.setStudentId("S124");
		attendance2.setStandard(10);
		attendance2.setDate(java.time.LocalDate.now());
		attendance2.setStatus(Status.A);

		List<Attendance> attendanceList = new ArrayList<>();
		attendanceList.add(attendance1);
		attendanceList.add(attendance2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(resultSet.getString(AttendanceConstants.STUDENT_ID_COLUMN)).thenReturn("S123").thenReturn("S124");
		when(resultSet.getInt(AttendanceConstants.STANDARD_COLUMN)).thenReturn(10).thenReturn(10);
		when(resultSet.getDate(AttendanceConstants.DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getString(AttendanceConstants.STATUS_COLUMN)).thenReturn("P").thenReturn("A");

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Attendance> result = attendanceDAO.viewAttendanceByStandard(10);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("S123", result.get(0).getStudentId());
		assertEquals("S124", result.get(1).getStudentId());
	}

	@Test
	public void testViewAttendanceById() throws SQLException, ClassNotFoundException {
		Attendance attendance = new Attendance();
		attendance.setStudentId("S123");
		attendance.setStandard(10);
		attendance.setDate(java.time.LocalDate.now());
		attendance.setStatus(Status.P);

		List<Attendance> attendanceList = new ArrayList<>();
		attendanceList.add(attendance);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(false);

		when(resultSet.getString(AttendanceConstants.STUDENT_ID_COLUMN)).thenReturn("S123");
		when(resultSet.getInt(AttendanceConstants.STANDARD_COLUMN)).thenReturn(10);
		when(resultSet.getDate(AttendanceConstants.DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getString(AttendanceConstants.STATUS_COLUMN)).thenReturn("P");

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Attendance> result = attendanceDAO.viewAttendanceById("S123");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("S123", result.get(0).getStudentId());
	}
}
