package com.jamieholdstock.stocktweeter.yahoo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class NasdaqClient {
	private String baseUrl = "https://uk.finance.yahoo.com/q/cp?s=%5EIXIC";
	
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
				System.out.println("NasdaqClient error occurred " + i + " time(s)");
			}                 
		}

		if (doc == null){
			throw new StockException("Couldn't connect to " + url);
		}
		else{
			System.out.println("Loaded page succesfully: " + url);
			return new NasdaqPage(doc);
		}

	}
}
