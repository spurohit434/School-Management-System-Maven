//package com.servicetest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyDouble;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.wg.DAO.FeeDAO;
//import com.wg.Model.Fee;
//import com.wg.Services.FeeService;
//
//public class FeeServiceTest {
//
//	@Mock
//	private FeeDAO feeDAO; // Mocked DAO
//
//	@InjectMocks
//	private FeeService feeService; // Service with injected mock
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this); // Initialize mocks
//	}
//
//	@Test
//	void testAddFees_InsertFees() throws SQLException, ClassNotFoundException {
//		String userId = "123";
//		double feeAmount = 1200.00;
//		LocalDate deadline = LocalDate.now();
//		double fine = 0.0;
//		when(feeDAO.checkFees(userId)).thenReturn(null); // Simulate no existing fees
//		when(feeDAO.insertFees(userId, feeAmount, deadline, fine)).thenReturn(true);
//
//		boolean result = feeService.addFees(userId, feeAmount, deadline, fine);
//		// Assert
//		assertEquals(true, result); // Verify the result
//		verify(feeDAO).checkFees(userId); // Verify checkFees was called with userId
//		verify(feeDAO).insertFees(userId, feeAmount, deadline, fine); // Verify insertFees was called with correct //
//																		// parameters
//		verify(feeDAO, never()).updateFees(anyString(), anyDouble(), any(LocalDate.class), anyDouble()); // Ensure // //
//																											// called
//	}
//
//	@Test
//	void testAddFees() throws ClassNotFoundException, SQLException {
//		LocalDate date = LocalDate.now();
//		when(feeDAO.addFees("123", 1200.00, date, 0.0)).thenReturn(true);
//
//		boolean result = feeService.addFees1("123", 1200.00, date, 0.0);
//		assertEquals(true, result);
//	}
//
//	@Test
//	void testPayFees_NoFees() throws SQLException, ClassNotFoundException {
//		when(feeDAO.checkFees("user1")).thenReturn(null);
//
//		feeService.payFees("user1");
//
//		verify(feeDAO, times(1)).checkFees("user1");
//		verify(feeDAO, never()).payFees(anyString());
//	}
//
//	@Test
//	void testCheckFees_WithFees() throws SQLException, ClassNotFoundException {
//		Fee fee = new Fee();
//		fee.setFeeAmount(150);
//
//		when(feeDAO.checkFees("user1")).thenReturn(fee);
//
//		Double result = feeService.checkFees("user1");
//		assertEquals(result, 150);
//
//		verify(feeDAO, times(1)).checkFees("user1");
//	}
//
//	@Test
//	void testCalculateFine_WithFees() throws SQLException, ClassNotFoundException {
//		// Arrange
//		Fee fee = new Fee();
//		fee.setFeeAmount(300);
//		fee.setDeadline(LocalDate.now().minusDays(20)); // Overdue
//
//		when(feeDAO.calculateFine("user1")).thenReturn(fee);
//		// Act
//		feeService.calculateFine("user1");
//		// Assert
//		verify(feeDAO, times(1)).calculateFine("user1");
//	}
//
//	@Test
//	void testCalculateFine_NoFees() throws SQLException, ClassNotFoundException {
//		// Arrange
//		when(feeDAO.calculateFine("user1")).thenReturn(null);
//		// Act
//		feeService.calculateFine("user1");
//		// Assert
//		verify(feeDAO, times(1)).calculateFine("user1");
//	}
//}

package com.wg.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dao.FeeDAO;
import com.wg.model.Fee;
import com.wg.services.FeeService;

public class FeeServiceTest {

	@Mock
	private FeeDAO feeDAOMock;

	@InjectMocks
	private FeeService feeService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testPayFees_SuccessfulPayment() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		Fee fee = new Fee();
		fee.setFeeAmount(100);
		fee.setDeadline(LocalDate.now().minusDays(1));
		when(feeDAOMock.checkFees(userId)).thenReturn(fee);
		when(feeDAOMock.payFees(userId)).thenReturn(true);

		// Act
		feeService.payFees(userId);

		// Assert
		verify(feeDAOMock).checkFees(userId);
		verify(feeDAOMock).payFees(userId);
	}

	@Test
	public void testPayFees_NoFeesAdded() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(feeDAOMock.checkFees(userId)).thenReturn(null);

		// Act
		feeService.payFees(userId);

		// Assert
		verify(feeDAOMock).checkFees(userId);
		verify(feeDAOMock, never()).payFees(anyString());
	}

	@Test
	public void testCheckFees_FeesExist() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		Fee fee = new Fee();
		fee.setFeeAmount(200);
		when(feeDAOMock.checkFees(userId)).thenReturn(fee);

		// Act
		double feeAmount = feeService.checkFees(userId);

		// Assert
		assertEquals(200, feeAmount);
		verify(feeDAOMock).checkFees(userId);
	}

	@Test
	public void testCheckFees_NoFeesAdded() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		when(feeDAOMock.checkFees(userId)).thenReturn(null);

		// Act
		double feeAmount = feeService.checkFees(userId);

		// Assert
		assertEquals(0, feeAmount);
		verify(feeDAOMock).checkFees(userId);
	}

	@Test
	public void testAddFees_Insert() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		double feeAmount = 300;
		LocalDate deadline = LocalDate.now().plusDays(10);
		double fine = 0;
		Fee fee = new Fee(userId, feeAmount, deadline, fine);
		when(feeDAOMock.checkFees(userId)).thenReturn(null);
		when(feeDAOMock.insertFees(fee)).thenReturn(true);

		// Act
		boolean result = feeService.addFees(fee);

		// Assert
		assertTrue(result);
		verify(feeDAOMock).insertFees(fee);
	}

	@Test
	public void testAddFees_Update() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		double feeAmount = 300;
		LocalDate deadline = LocalDate.now().plusDays(10);
		double fine = 0;
		Fee existingFee = new Fee();
		existingFee.setFeeAmount(200);
		Fee fee = new Fee(userId, feeAmount, deadline, fine);
		when(feeDAOMock.checkFees(userId)).thenReturn(existingFee);
		when(feeDAOMock.updateFees(fee)).thenReturn(true);

		// Act
		boolean result = feeService.addFees(fee);

		// Assert
		assertTrue(result);
		verify(feeDAOMock).updateFees(fee);
	}

	@Test
	public void testCalculateFine_Overdue() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		Fee fee = new Fee();
		fee.setFeeAmount(100);
		fee.setDeadline(LocalDate.now().minusDays(10));
		when(feeDAOMock.calculateFine(userId)).thenReturn(fee);

		// Act
		feeService.calculateFine(userId);

		// Assert
		verify(feeDAOMock).calculateFine(userId);
	}

	@Test
	public void testCalculateFine_NoFine() throws SQLException, ClassNotFoundException {
		// Arrange
		String userId = "user123";
		Fee fee = new Fee();
		fee.setFeeAmount(100);
		fee.setDeadline(LocalDate.now().plusDays(10));
		when(feeDAOMock.calculateFine(userId)).thenReturn(fee);

		// Act
		feeService.calculateFine(userId);

		// Assert
		verify(feeDAOMock).calculateFine(userId);
	}
}
