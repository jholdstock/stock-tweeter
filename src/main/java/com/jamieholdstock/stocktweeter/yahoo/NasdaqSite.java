package com.jamieholdstock.stocktweeter.yahoo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;

public class NasdaqSite {
	
	private String baseUrl = "https://uk.finance.yahoo.com/q/cp?s=%5EIXIC";
	private int lastPageId;
	
	public NasdaqSite() throws StockException {
		NasdaqPage homePage = getPage(baseUrl);
		
		String lastPageUrl = homePage.getLastPageUrl(); 
		lastPageId = parseUrl(lastPageUrl);
	}
	
	public Stocks getAllStocks() throws StockException {
		Stocks stocks = new Stocks();
		for (NasdaqPage page : getAllPages()) {
			stocks.addAll(page.getStocks());
		}
		return stocks;
	}
	
	private List<NasdaqPage> getAllPages() throws StockException {
		List<NasdaqPage> pages = new ArrayList<NasdaqPage>();
		for (int i = 28; i <= 42; i++) {
			pages.add(getPage(baseUrl+"&c="+i));
		}
		return pages;
	}
	
	private NasdaqPage getPage(String url) throws StockException {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			System.out.println("Loaded page succesfully: " + url);			
		} catch (IOException e) {
			throw new StockException("Couldn't connect to " + url, e);
		}
		 return new NasdaqPage(doc);
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
