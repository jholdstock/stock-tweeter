package com.jamieholdstock.stocktweeter.yahoo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;

public class NasdaqSite {
	
	@Autowired
	private NasdaqClient client;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public NasdaqSite() {}
	
	public Stocks getAllStocks() throws StockException {
		Stocks stocks = new Stocks();
		for (NasdaqPage page : getAllPages()) {
			stocks.addAll(page.getStocks());
		}
		return stocks;
	}
	
	private List<NasdaqPage> getAllPages() throws StockException {
		log.info("Checking how many pages of NASDAQ data to load");
		NasdaqPage homePage = client.getHomePage();
		List<NasdaqPage> pages = new ArrayList<NasdaqPage>();
		String lastPageUrl = homePage.getLastPageUrl(); 
		
		int pagesToLoad = parseUrl(lastPageUrl);
		//int pagesToLoad = 10;
		
		log.info("Collecting " + pagesToLoad + " pages");
		for (int i = 0; i <= pagesToLoad; i++) {
			pages.add(client.getPage("&c="+i));
			if (i > 0 && i != pagesToLoad && i%10 == 0) {
				log.info(i + "/" + pagesToLoad);
			}
		}
		log.info(pagesToLoad + "/" + pagesToLoad);
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
