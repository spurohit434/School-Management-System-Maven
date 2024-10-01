package com.wg.servicetest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.wg.dao.AttendanceDAO;
import com.wg.model.Attendance;
import com.wg.services.AttendanceServices;

public class AttendanceServicesTest {

	@Mock
	private AttendanceDAO attendanceDAOMock;

	@InjectMocks
	private AttendanceServices attendanceService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testViewAttendanceByStandard_Success() throws ClassNotFoundException, SQLException {
		int standard = 10;
		List<Attendance> attendanceList = new ArrayList<>();
		Attendance attendance = new Attendance();
		attendanceList.add(attendance);

		when(attendanceDAOMock.viewAttendanceByStandard(standard)).thenReturn(attendanceList);

		List<Attendance> result = attendanceService.viewAttendanceByStandard(standard);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(attendanceDAOMock, times(1)).viewAttendanceByStandard(standard);
	}

	@Test
	public void testViewAttendanceByStandard_Failure() throws ClassNotFoundException, SQLException {
		int standard = 12;
		when(attendanceDAOMock.viewAttendanceByStandard(standard)).thenReturn(null);

		List<Attendance> result = attendanceService.viewAttendanceByStandard(standard);

		assertNull(result);
		verify(attendanceDAOMock, times(1)).viewAttendanceByStandard(standard);
	}

	@Test
	public void testViewAttendanceById_Success() throws ClassNotFoundException, SQLException {
		String studentId = "S123";
		List<Attendance> attendanceList = new ArrayList<>();
		Attendance attendance = new Attendance();
		attendanceList.add(attendance);

		when(attendanceDAOMock.viewAttendanceById(studentId)).thenReturn(attendanceList);

		List<Attendance> result = attendanceService.viewAttendanceById(studentId);

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(attendanceDAOMock, times(1)).viewAttendanceById(studentId);
	}

	@Test
	public void testViewAttendanceById_Failure() throws ClassNotFoundException, SQLException {
		String studentId = "S124";
		when(attendanceDAOMock.viewAttendanceById(studentId)).thenReturn(null);

		List<Attendance> result = attendanceService.viewAttendanceById(studentId);

		assertNull(result); // Check that the result is null when an exception is thrown
		verify(attendanceDAOMock, times(1)).viewAttendanceById(studentId);
	}

	@Test
	public void testAddAttendance_Success() throws ClassNotFoundException, SQLException {
		Attendance attendance = new Attendance();
		when(attendanceDAOMock.addAttendance(attendance)).thenReturn(true);

		boolean result = attendanceService.addAttendance(attendance);

		assertTrue(result);
		verify(attendanceDAOMock, times(1)).addAttendance(attendance);
	}

	@Test
	public void testAddAttendance_Failure() throws ClassNotFoundException, SQLException {
		Attendance attendance = new Attendance();
		when(attendanceDAOMock.addAttendance(attendance)).thenReturn(false);

		boolean result = attendanceService.addAttendance(attendance);

		assertFalse(result);
		verify(attendanceDAOMock, times(1)).addAttendance(attendance);
	}

	@Test
	public void testExceptionHandelingGetStandard() throws ClassNotFoundException, SQLException {
		int standard = 12;

		doThrow(new SQLException("Query error")).when(attendanceDAOMock).viewAttendanceByStandard(standard);

		List<Attendance> result = attendanceService.viewAttendanceByStandard(standard);
		assertNull(result);
	}

	@Test
	public void testExceptionHandelingAddAttendance() throws ClassNotFoundException, SQLException {
		Attendance attendance = new Attendance();
		doThrow(new SQLException("Query error")).when(attendanceDAOMock).addAttendance(attendance);

		boolean result = attendanceService.addAttendance(attendance);
		assertFalse(result);
	}
}
