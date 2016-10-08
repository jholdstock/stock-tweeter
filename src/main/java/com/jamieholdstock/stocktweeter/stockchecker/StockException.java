package com.jamieholdstock.stocktweeter.stockchecker;

public class StockException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private Exception cause;
	
	public StockException(String message, Exception cause) {
		this.message = message;
		this.cause = cause;
	}
	
	public StockException(String message) {
		this(message, null);
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public Exception getCause() {
		return cause;
	}
	
}
