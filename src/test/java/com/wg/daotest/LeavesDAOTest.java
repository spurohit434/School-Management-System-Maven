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

import com.wg.constants.LeavesConstants;
import com.wg.dao.LeavesDAO;
import com.wg.model.Leaves;
import com.wg.model.LeavesStatus;

public class LeavesDAOTest {

	@InjectMocks
	private LeavesDAO leavesDAO;

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
	public void testApproveLeave() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = leavesDAO.approveLeave("U101");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testRejectLeave() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = leavesDAO.rejectLeave("U101");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testApplyLeave() throws SQLException, ClassNotFoundException {
		Leaves leave = new Leaves();
		leave.setLeaveId("L101");
		leave.setUserId("U101");
		leave.setContent("Sick Leave");
		leave.setStartDate(java.time.LocalDate.now());
		leave.setEndDate(java.time.LocalDate.now().plusDays(5));
		leave.setStatus(LeavesStatus.Pending);

		// Mock ResultSet behavior for checking existing leaves
		when(resultSet.next()).thenReturn(false); // No existing leave found

		// Mock PreparedStatement behavior for the SELECT query
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		// Mock PreparedStatement behavior for the INSERT query
		when(preparedStatement.executeUpdate()).thenReturn(1);

		// Execute the method under test
		leavesDAO.applyLeave(leave);

		// Verify that the INSERT query was executed
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testViewAllLeave() throws SQLException, ClassNotFoundException {
		Leaves leave1 = new Leaves();
		leave1.setLeaveId("L101");
		leave1.setUserId("U101");
		leave1.setContent("Leave 1");
		leave1.setStartDate(java.time.LocalDate.now());
		leave1.setEndDate(java.time.LocalDate.now().plusDays(1));
		leave1.setStatus(LeavesStatus.Pending);

		Leaves leave2 = new Leaves();
		leave2.setLeaveId("L102");
		leave2.setUserId("U102");
		leave2.setContent("Leave 2");
		leave2.setStartDate(java.time.LocalDate.now().plusDays(1));
		leave2.setEndDate(java.time.LocalDate.now().plusDays(2));
		leave2.setStatus(LeavesStatus.Approved);

		List<Leaves> leaveList = new ArrayList<>();
		leaveList.add(leave1);
		leaveList.add(leave2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString(LeavesConstants.ID_COLUMN)).thenReturn("L101").thenReturn("L102");
		when(resultSet.getString(LeavesConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U102");
		when(resultSet.getString(LeavesConstants.CONTENT_COLUMN)).thenReturn("Leave 1").thenReturn("Leave 2");
		when(resultSet.getString(LeavesConstants.STATUS_COLUMN)).thenReturn("Pending").thenReturn("Approved");
		when(resultSet.getDate(LeavesConstants.START_DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getDate(LeavesConstants.END_DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(1)));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Leaves> result = leavesDAO.viewAllLeave();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("L101", result.get(0).getLeaveId());
		assertEquals("L102", result.get(1).getLeaveId());
	}

	@Test
	public void testCheckLeaveStatus() throws SQLException, ClassNotFoundException {
		Leaves leave1 = new Leaves();
		leave1.setLeaveId("L101");
		leave1.setUserId("U101");
		leave1.setContent("Leave 1");
		leave1.setStartDate(java.time.LocalDate.now());
		leave1.setEndDate(java.time.LocalDate.now().plusDays(1));
		leave1.setStatus(LeavesStatus.Pending);

		Leaves leave2 = new Leaves();
		leave2.setLeaveId("L102");
		leave2.setUserId("U101");
		leave2.setContent("Leave 2");
		leave2.setStartDate(java.time.LocalDate.now().plusDays(1));
		leave2.setEndDate(java.time.LocalDate.now().plusDays(2));
		leave2.setStatus(LeavesStatus.Approved);

		List<Leaves> leaveList = new ArrayList<>();
		leaveList.add(leave1);
		leaveList.add(leave2);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		// Mock column values for the ResultSet
		when(resultSet.getString(LeavesConstants.ID_COLUMN)).thenReturn("L101").thenReturn("L102");
		when(resultSet.getString(LeavesConstants.USER_ID_COLUMN)).thenReturn("U101").thenReturn("U101");
		when(resultSet.getString(LeavesConstants.CONTENT_COLUMN)).thenReturn("Leave 1").thenReturn("Leave 2");
		when(resultSet.getString(LeavesConstants.STATUS_COLUMN)).thenReturn("Pending").thenReturn("Approved");
		when(resultSet.getDate(LeavesConstants.START_DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now()));
		when(resultSet.getDate(LeavesConstants.END_DATE_COLUMN))
				.thenReturn(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(1)));

		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		List<Leaves> result = leavesDAO.checkLeaveStatus("U101");

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("L101", result.get(0).getLeaveId());
		assertEquals("L102", result.get(1).getLeaveId());
	}
}
