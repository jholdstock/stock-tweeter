package com.jamieholdstock.stocktweeter.yahoo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.jamieholdstock.stocktweeter.RetryingHttpClient;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class NasdaqClient {
	private String baseUrl = "https://uk.finance.yahoo.com/q/cp?s=%5EIXIC";
	
	@Autowired private RetryingHttpClient httpClient;
	
	public NasdaqClient() {}
	
	public NasdaqPage getHomePage() throws StockException {
		return getPage("");
	}
	
	public NasdaqPage getPage(String url) throws StockException {
		url = baseUrl + url;
		Document doc = null;
				
		String html = null;
		try {
			html = httpClient.getHttpBody(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		doc = Jsoup.parse(html); 

		if (doc == null){
			throw new StockException("Couldn't connect to " + url);
		}
		else{
			return new NasdaqPage(doc);
		}

	}
}
