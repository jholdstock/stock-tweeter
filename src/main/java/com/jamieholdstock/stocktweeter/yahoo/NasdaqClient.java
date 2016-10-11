package com.jamieholdstock.stocktweeter.yahoo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class NasdaqClient {
	private String baseUrl = "https://uk.finance.yahoo.com/q/cp?s=%5EIXIC";
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public NasdaqClient() {}
	
	public NasdaqPage getHomePage() throws StockException {
		return getPage("");
	}
	
	public NasdaqPage getPage(String url) throws StockException {
		url = baseUrl + url;
		Document doc = null;
		for (int i = 1; i <= 3; i++) {
			try{
				doc = Jsoup.connect(url).get();
				break; 
			}
			catch (IOException e){
				log.error("Failed to GET url " + url + " " + i + " time(s)");
			}                 
		}

		if (doc == null){
			throw new StockException("Couldn't connect to " + url);
		}
		else{
			return new NasdaqPage(doc);
		}

	}
}
