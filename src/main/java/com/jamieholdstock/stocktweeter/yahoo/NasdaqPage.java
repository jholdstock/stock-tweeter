package com.jamieholdstock.stocktweeter.yahoo;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class NasdaqPage {

	private Document doc;
	
	public NasdaqPage(Document doc) {
		this.doc = doc;
		System.out.println("Loaded page succesfully" + doc.location());
	}
	
	public String getLastPageUrl() throws StockException {
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
		  String linkText = link.text();
		  if ("Last".equals(linkText)) {
			  return link.attr("href");
		  }
		}
		throw new StockException("Failed to find last page link");
	}
	
	public List<Stock> getStocks() { 
		List<Stock> stocks = new ArrayList<Stock>();
		
		Element table = doc.select("table.yfnc_tableout1").first();
		
		for (Element row : table.select("tr tr")) {
			Elements td = row.select("td");
			if (td.size() < 1) continue;
			
			String ticker = td.get(0).text();
			String change = td.get(3).text();
			
			Elements img = td.get(3).select("img");
			if (img.size() > 0) {
				String alt = img.attr("alt");
				if ("down".equalsIgnoreCase(alt)) {
					change = "-" + change;
				}
			}
			
			Stock stock = new Stock(ticker, change);
			stocks.add(stock);
		}
		
		return stocks;
	}
}
