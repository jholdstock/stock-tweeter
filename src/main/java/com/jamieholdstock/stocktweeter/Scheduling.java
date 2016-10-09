package com.jamieholdstock.stocktweeter;

import java.sql.SQLException;
import java.time.Instant;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.DuplicateStatusException;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;
import com.jamieholdstock.stocktweeter.twitter.TwitterFeed;
import com.jamieholdstock.stocktweeter.yahoo.NasdaqSite;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduling {
	
	private TwitterFeed twitterFeed;
	
	public void setTwitterFeed(TwitterFeed twitterFeed) {
		this.twitterFeed = twitterFeed;
	}

	@Scheduled(fixedDelay=1000000)
	public void checkStock() throws SQLException {
		
		Stocks allStocks = null;
		try {
			NasdaqSite site = new NasdaqSite();
			allStocks = site.getAllStocks();
		} 
		catch (StockException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			System.exit(1);
		}
		
		System.out.println("All: " + allStocks.size());
		System.out.println("Moved: " + allStocks.getMovedStocks().size());
		System.out.println("");
		
		for (Stock stock : allStocks.getMovedStocks()) {
			System.out.println("");
			System.out.println(stock);
			
			if (Database.wasStockAlreadyTweeted(stock)) {
				System.out.println("Found in DB. Already tweeted this stock.");
				continue;
			}
			
			System.out.println("Will tweet now");
			try {
				twitterFeed.tweetMovedStock(stock);
				System.out.println("Tweeted");
			}
			catch (DuplicateStatusException e) {
				System.out.println("ERROR: Already tweeted this. Continuing");
			}
			
			try {
				Database.insertStockTweet(stock, Instant.now().toString());
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Tweeted but couldn't record in database");
				System.exit(1);
			}
		}
		
		System.out.println("");
		System.out.println("Scheduled task 'checkStock' complete");
	}
	
}
