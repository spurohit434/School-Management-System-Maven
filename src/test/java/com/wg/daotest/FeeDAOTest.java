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
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dao.FeeDAO;
import com.wg.model.Fee;

public class FeeDAOTest {

	@InjectMocks
	private FeeDAO feeDAO;

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
	public void testCheckFees() throws SQLException, ClassNotFoundException {
		Fee fee = new Fee();
		fee.setStudentId("1");
		fee.setFeeAmount(100.0);
		fee.setDeadline(LocalDate.now());
		fee.setFine(10.0);

		// Mock ResultSet behavior
		when(resultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
		when(resultSet.getString("studentId")).thenReturn("1");
		when(resultSet.getDouble("feeAmount")).thenReturn(100.0);
		when(resultSet.getDate("deadline")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
		when(resultSet.getDouble("fine")).thenReturn(10.0);

		// Mock PreparedStatement behavior
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		// Execute the method under test
		Fee result = feeDAO.checkFees("1");

		// Assert the results
		assertNotNull(result);
		assertEquals("1", result.getStudentId());
		assertEquals(100.0, result.getFeeAmount());
		assertEquals(LocalDate.now(), result.getDeadline());
		assertEquals(10.0, result.getFine());
	}

	@Test
	public void testPayFees() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);

		boolean result = feeDAO.payFees("1");

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testInsertFees() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);
		Fee fee = new Fee("1", 100.0, LocalDate.now(), 10.0);

		boolean result = feeDAO.insertFees(fee);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

	@Test
	public void testUpdateFees() throws SQLException, ClassNotFoundException {
		when(preparedStatement.executeUpdate()).thenReturn(1);
		Fee fee = new Fee("1", 150.0, LocalDate.now().plusDays(10), 20.0);

		boolean result = feeDAO.updateFees(fee);

		assertTrue(result);
		verify(preparedStatement).executeUpdate();
	}

//	@Test
//	public void testAddFeesInsert() throws SQLException, ClassNotFoundException {
//		when(feeDAO.checkFees("1")).thenReturn(null); // Simulate no existing fees
//		when(preparedStatement.executeQuery()).thenReturn(resultSet);
//		when(preparedStatement.executeUpdate()).thenReturn(1);
//
//		boolean result = feeDAO.addFees("1", 100.0, LocalDate.now(), 10.0);
//
//		assertTrue(result);
//	}
//
//	@Test
//	public void testAddFeesUpdate() throws SQLException, ClassNotFoundException {
//		Fee existingFee = new Fee();
//		existingFee.setStudentId("1");
//		existingFee.setFeeAmount(50.0);
//		existingFee.setDeadline(LocalDate.now().minusDays(5));
//		existingFee.setFine(5.0);
//
//		when(feeDAO.checkFees("1")).thenReturn(existingFee); // Simulate existing fees
//
//		when(preparedStatement.executeUpdate()).thenReturn(1);
//
//		boolean result = feeDAO.addFees("1", 100.0, LocalDate.now(), 10.0);
//
//		assertTrue(result);
//	}
}
