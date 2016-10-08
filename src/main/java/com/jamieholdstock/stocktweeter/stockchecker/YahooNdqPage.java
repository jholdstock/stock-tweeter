package com.jamieholdstock.stocktweeter.stockchecker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YahooNdqPage {

	private Document doc;
	
	public YahooNdqPage(Document doc) {
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
}
