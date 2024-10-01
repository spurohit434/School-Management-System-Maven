package com.wg.controller;

import java.time.LocalDate;
import java.util.Scanner;

import com.wg.model.Fee;
import com.wg.services.FeeService;

public class FeeController {
	private final FeeService feeService = new FeeService();
	Scanner scanner = new Scanner(System.in);

	public FeeController() {
	}

	public void payFees(String userId) {
		feeService.payFees(userId);
	}

	public void checkFees(String userId) {
		feeService.checkFees(userId);
	}

	public void addFees(String userId, double feeAmount, LocalDate deadline, double fine) {
		Fee fee = new Fee(userId, feeAmount, deadline, fine);
		feeService.addFees(fee);
	}

	public void calculateFine(String userId) {
		feeService.calculateFine(userId);
	}
}