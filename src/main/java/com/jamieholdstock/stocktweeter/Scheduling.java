package com.jamieholdstock.stocktweeter;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;
import com.jamieholdstock.stocktweeter.twitter.TwitterFeed;
import com.jamieholdstock.stocktweeter.yahoo.NasdaqSite;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduling {
	
	@Scheduled(fixedDelay=1000000)
	public void checkStock() {
		
		Stocks allStocks;
		try {
			NasdaqSite site = new NasdaqSite();
			allStocks = site.getAllStocks();
		} 
		catch (StockException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return;
		}
		
		System.out.println("All: " + allStocks.size());
		System.out.println("Moved: " + allStocks.getMovedStocks().size());
		
		for (Stock stock : allStocks.getMovedStocks()) {
			System.out.println(stock);
			TwitterFeed twitter = new TwitterFeed();
			if (twitter.hasAlreadyBeetTweeted(stock.getTicker()) == false) {
				System.out.println("Will tweet now");
				twitter.tweetMovedStock(stock);
			}
			else {
				System.out.println("Already been tweeted");
			}
		}
		
		System.out.println("Scheduled task 'checkStock' complete");
	}
	
}
