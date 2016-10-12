package com.jamieholdstock.stocktweeter.yahoo;

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
		
		NasdaqPage homePage = client.getHomePage();
		String lastPageUrl = homePage.getLastPageUrl(); 
		
		int pagesToLoad = parseUrl(lastPageUrl);
		//int pagesToLoad = 10;
		
		log.info("Collecting " + pagesToLoad + " pages of NASDAQ data");
		for (int i = 0; i <= pagesToLoad; i++) {
			NasdaqPage page = client.getPage("&c="+i);
			stocks.addAll(page.getStocks());
			if (i > 0 && i != pagesToLoad && i%10 == 0) {
				log.info(i + "/" + pagesToLoad);
			}
		}
		
		log.info(pagesToLoad + "/" + pagesToLoad);

		return stocks;
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
