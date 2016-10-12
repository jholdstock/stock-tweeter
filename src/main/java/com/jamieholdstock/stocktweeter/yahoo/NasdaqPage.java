package com.jamieholdstock.stocktweeter.yahoo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;

public class NasdaqPage {

	private Document doc;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public NasdaqPage(Document doc) {
		this.doc = doc;
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
	
	public Stocks getStocks() throws StockException { 
		Stocks stocks = new Stocks();
		
		Element table = doc.select("table.yfnc_tableout1").first();
		
		for (Element row : table.select("tr tr")) {
			Elements td = row.select("td");
			if (td.size() < 1) continue;
			
			String ticker = td.get(0).text().trim();
			String change = td.get(3).text().trim();
			
			if ("n/a".equalsIgnoreCase(change)) {
				log.warn("No change info available for " + ticker + ". Assuming 0.0 and continuing");
				change = "0.0";
			}
			
			Pattern r = Pattern.compile("^\\S+\\s+\\((\\S+)%\\)");
			Matcher m = r.matcher(change);
			if (!m.find( )) {
				log.warn("Error finding change% for " + ticker + ". Input was '" + change + "'. Assuming 0.0 and continuing");
				change = "0.0";
			} 
			else {
				change = m.group(1);
			}
			
			
			
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
