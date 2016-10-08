package com.jamieholdstock.stocktweeter.stockchecker;

public class Stock {
	private String ticker;
	private String change;
	
	public Stock(String ticker, String change) {
		this.ticker = ticker;
		this.change = change;
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + ", change=" + change + "]";
	}
}
