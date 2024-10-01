package com.wg.servicetest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doNothing;
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

import com.wg.dao.LeavesDAO;
import com.wg.model.Leaves;
import com.wg.services.LeavesService;

public class LeavesServiceTest {

	@Mock
	private LeavesDAO leavesDAOMock;

	@InjectMocks
	private LeavesService leavesService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testApproveLeave_Success() throws SQLException, ClassNotFoundException {
		String userId = "user123";
		when(leavesDAOMock.approveLeave(userId)).thenReturn(true);

		leavesService.approveLeave(userId);

		verify(leavesDAOMock).approveLeave(userId);
	}

	@Test
	public void testApproveLeave_Failure() throws SQLException, ClassNotFoundException {
		String userId = "user123";
		when(leavesDAOMock.approveLeave(userId)).thenReturn(false);

		leavesService.approveLeave(userId);

		verify(leavesDAOMock).approveLeave(userId);
	}

	@Test
	public void testRejectLeave_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(leavesDAOMock.rejectLeave(userId)).thenReturn(true);

		// Act
		leavesService.rejectLeave(userId);

		// Assert
		verify(leavesDAOMock).rejectLeave(userId);
	}

	@Test
	public void testRejectLeave_Failure() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(leavesDAOMock.rejectLeave(userId)).thenReturn(false);

		// Act
		leavesService.rejectLeave(userId);

		// Assert
		verify(leavesDAOMock).rejectLeave(userId);
	}

	@Test
	public void testApplyLeave_Success() throws SQLException, ClassNotFoundException {
		// Arrange
		Leaves leave = new Leaves();
		doNothing().when(leavesDAOMock).applyLeave(leave);

		// Act
		leavesService.applyLeave(leave);

		// Assert
		verify(leavesDAOMock).applyLeave(leave);
	}

	@Test
	public void testViewAllLeave_Success() throws SQLException, ClassNotFoundException {
		List<Leaves> expectedLeaves = new ArrayList<>();
		when(leavesDAOMock.viewAllLeave()).thenReturn(expectedLeaves);

		List<Leaves> actualLeaves = leavesService.viewAllLeave();

		assertEquals(expectedLeaves, actualLeaves);
		verify(leavesDAOMock).viewAllLeave();
	}

	@Test
	public void testCheckLeaveStatus_Success() throws SQLException, ClassNotFoundException {
		String userId = "user123";
		List<Leaves> expectedStatus = new ArrayList<>();
		when(leavesDAOMock.checkLeaveStatus(userId)).thenReturn(expectedStatus);

		List<Leaves> actualStatus = leavesService.checkLeaveStatus(userId);

		assertEquals(expectedStatus, actualStatus);
		verify(leavesDAOMock).checkLeaveStatus(userId);
	}

	@Test
	public void testExceptionHandelingApproveLeave() throws ClassNotFoundException, SQLException {
		String userId = "1234";
		doThrow(new SQLException()).when(leavesDAOMock).approveLeave(userId);

		boolean result = leavesService.approveLeave(userId);

		assertFalse(result);
		verify(leavesDAOMock, times(1)).approveLeave(userId);
	}

	@Test
	public void testExceptionHandelingRejectLeave() throws ClassNotFoundException, SQLException {
		String userId = "1234";
		doThrow(new SQLException()).when(leavesDAOMock).rejectLeave(userId);

		boolean result = leavesService.rejectLeave(userId);

		assertFalse(result);
		verify(leavesDAOMock, times(1)).rejectLeave(userId);
	}

	@Test
	public void testExceptionHandelingViewAllLeave() throws ClassNotFoundException, SQLException {
		doThrow(new SQLException()).when(leavesDAOMock).viewAllLeave();

		List<Leaves> list = leavesService.viewAllLeave();

		assertNull(list);
		verify(leavesDAOMock, times(1)).viewAllLeave();
	}

	@Test
	public void testExceptionHandelingApplyLeave() throws ClassNotFoundException, SQLException {
		Leaves leave = new Leaves();
		doThrow(new SQLException()).when(leavesDAOMock).applyLeave(leave);

		leavesService.applyLeave(leave);

		verify(leavesDAOMock, times(1)).applyLeave(leave);
	}

	@Test
	public void testExceptionHandelingCheckLeaveStatus() throws ClassNotFoundException, SQLException {
		String userId = "1234";
		doThrow(new SQLException()).when(leavesDAOMock).checkLeaveStatus(userId);

		List<Leaves> list = leavesService.checkLeaveStatus(userId);

		assertNull(list);

		verify(leavesDAOMock, times(1)).checkLeaveStatus(userId);
	}
}
