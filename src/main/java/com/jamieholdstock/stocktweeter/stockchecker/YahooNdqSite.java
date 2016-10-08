package com.jamieholdstock.stocktweeter.stockchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class YahooNdqSite {
	
	private String baseUrl = "https://uk.finance.yahoo.com/q/cp?s=%5EIXIC";
	private int lastPageId;
	
	public YahooNdqSite() throws StockException {
		YahooNdqPage homePage = getPage(baseUrl);
		String lastPageUrl = homePage.getLastPageUrl(); 
		lastPageId = parseUrl(lastPageUrl);
		getAllPages();
	}
	
	private List<YahooNdqPage> getAllPages() throws StockException {
		List<YahooNdqPage> pages = new ArrayList<YahooNdqPage>();
		for (int i = 0; i <= lastPageId; i++) {
			pages.add(getPage(baseUrl+"&c="+i));
		}
		return pages;
	}
	
	private YahooNdqPage getPage(String url) throws StockException {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new StockException("Couldn't connect to Yahoo website", e);
		}
		 return new YahooNdqPage(doc);
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
