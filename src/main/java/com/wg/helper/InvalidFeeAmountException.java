package com.wg.helper;

public class InvalidFeeAmountException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFeeAmountException(String message) {
		super(message);
	}
}
