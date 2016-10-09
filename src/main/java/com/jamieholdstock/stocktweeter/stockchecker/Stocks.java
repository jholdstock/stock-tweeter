package com.jamieholdstock.stocktweeter.stockchecker;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Stocks extends ArrayList<Stock> {
	private static final long serialVersionUID = 1L;

	public Stocks getMovedStocks() {
		return stream()
				.filter(s -> s.hasMoved5())
		        .collect(Collectors.toCollection(Stocks::new));
	}
}
