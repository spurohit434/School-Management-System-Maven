package com.wg.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import com.wg.dao.FeeDAO;
import com.wg.dao.interfaces.InterfaceFeeDAO;
import com.wg.helper.LoggingUtil;
import com.wg.model.Fee;

public class FeeService {
	private InterfaceFeeDAO feeDAO = new FeeDAO();
	Logger logger = LoggingUtil.getLogger(FeeService.class);

	public FeeService() {
	}

	public void payFees(String userId) {
		try {
			Fee fees = null;
			try {
				fees = feeDAO.checkFees(userId);
				if (fees == null) {
					System.out.println("Fees not added yet");
					return;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			double fine = 0;
			double feesAmount = fees.getFeeAmount();
			LocalDate deadLine = fees.getDeadline();
			LocalDate currentDate = LocalDate.now();

			if (currentDate.isAfter(deadLine)) {
				long overdueDays = ChronoUnit.DAYS.between(deadLine, currentDate);
				fine = overdueDays * 5.0;
			}
			System.out.println("The fine is " + fine);
			double totalFees = feesAmount + fine;
			System.out.println("Total Payalbe amount is: " + totalFees);
			if (feesAmount == 0 && fine == 0) {
				System.out.println("No fee amount to pay");
				return;
			}
			try {
				boolean flag = feeDAO.payFees(userId);
				if (flag == true) {
					System.out.println("Fees paid successfully");
					logger.info("Fees paid successfully!!");

				} else {
					System.out.println("Fees not paid");
					logger.info("Fees payment unsuccessful!!");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double checkFees(String userId) {
		try {
			Fee fees = null;
			fees = feeDAO.checkFees(userId);
			if (fees == null) {
				System.out.println("Fees not added yet");
				return 0;
			} else {
				System.out.println("The fees Amount is: " + fees.getFeeAmount());
				return fees.getFeeAmount();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean addFees(Fee fee) {
		try {
			Fee fees = null;
			fees = feeDAO.checkFees(fee.getStudentId());
			if (fees == null) {
				boolean flag = feeDAO.insertFees(fee);
				if (flag == true) {
					System.out.println("Fees successfully added");
					return true;
				} else {
					System.out.println("No records inserted in fee service.");
					return false;
				}
			} else {
				boolean flag = feeDAO.updateFees(fee);
				if (flag == true) {
					System.out.println("Fees successfully updated");
					return true;
				} else {
					System.out.println("No records updated in fee service.");
					return false;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void calculateFine(String userId) {
		try {
			Fee fee = null;
			fee = feeDAO.calculateFine(userId);
			if (fee == null) {
				System.out.println("No fine applicable as fees not added yet");
				return;
			}
			double fine = 0;
			double feeAmount = fee.getFeeAmount();
			LocalDate deadLine = fee.getDeadline();
			LocalDate currentDate = LocalDate.now();

			if (currentDate.isAfter(deadLine)) {
				long overdueDays = ChronoUnit.DAYS.between(deadLine, currentDate);
				fine = overdueDays * 5.0;
			}

			System.out.println("The feeAmount is " + feeAmount);
			System.out.println("The deadline is " + deadLine);
			System.out.println("The fine is " + fine);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}