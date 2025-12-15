package com.smartbank;

public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String msg) {
        super(msg);
    }
}
