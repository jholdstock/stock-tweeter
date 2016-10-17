package com.jamieholdstock.stocktweeter.stockchecker;

public class Stock {
	private String ticker;
	private String change;
	
	public Stock(String ticker, String change) {
		this.ticker = ticker;
		this.change = change;
	}

	public String getTicker() {
		return ticker;
	}

	public String getChange() {
		return change;
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + ", change=" + change + "]";
	}
	
	public boolean hasMoved5() {
		Double d = Double.parseDouble(change);
		return (d >= 5.0d || d <= -5.0d);
	}
}
