package com.smartbank;

public class NegativeAmountException extends Exception {
	private static final long serialVersionUID = 1L;

	public NegativeAmountException(String msg) {
        super(msg);
    }
}
