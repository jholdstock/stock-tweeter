package com.jamieholdstock.stocktweeter.yahoo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.jamieholdstock.stocktweeter.RetryingHttpClient;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class NasdaqClient {

	@Autowired private RetryingHttpClient httpClient;
	private String baseUrl;
	
	public NasdaqClient(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public NasdaqPage getHomePage() throws StockException {
		return getPage("");
	}
	
	public NasdaqPage getPage(String url) throws StockException {
		url = baseUrl + url;
		Document doc = null;
				
		String html = null;
		
		html = httpClient.getHttpBody(url);
		
		doc = Jsoup.parse(html); 

		if (doc == null){
			throw new StockException("Couldn't connect to " + url);
		}
		else{
			return new NasdaqPage(doc);
		}

	}
}
