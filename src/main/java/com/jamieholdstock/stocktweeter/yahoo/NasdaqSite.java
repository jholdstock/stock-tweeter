package com.jamieholdstock.stocktweeter.yahoo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;

public class NasdaqSite {
	
	@Autowired
	private NasdaqClient client;
	
	public NasdaqSite() {}
	
	public Stocks getAllStocks() throws StockException {
		Stocks stocks = new Stocks();
		for (NasdaqPage page : getAllPages()) {
			stocks.addAll(page.getStocks());
		}
		return stocks;
	}
	
	private List<NasdaqPage> getAllPages() throws StockException {
		NasdaqPage homePage = client.getHomePage();
		List<NasdaqPage> pages = new ArrayList<NasdaqPage>();
		String lastPageUrl = homePage.getLastPageUrl(); 
		int lastPageId = parseUrl(lastPageUrl);
		for (int i = 0; i <= lastPageId; i++) {
			pages.add(client.getPage("&c="+i));
		}
		return pages;
	}
	
	private int parseUrl(String lastPageUrl) throws StockException {
		Pattern r = Pattern.compile("c=(\\d+)");
		
		Matcher m = r.matcher(lastPageUrl);
		if (!m.find( )) {
			throw new StockException(String.format("Couldn't find last page number using url '%s'", lastPageUrl));
		}
		
		String match = m.group(1);
		
		return Integer.parseInt(match);
	}
}
