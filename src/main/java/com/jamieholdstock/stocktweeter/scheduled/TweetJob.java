package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.DuplicateStatusException;

import com.jamieholdstock.stocktweeter.Database;
import com.jamieholdstock.stocktweeter.stockchecker.Stock;
import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.Stocks;
import com.jamieholdstock.stocktweeter.twitter.TwitterFeed;
import com.jamieholdstock.stocktweeter.yahoo.NasdaqSite;

public class TweetJob extends Job {
	@Autowired private TwitterFeed twitterFeed;
	@Autowired private Database database;
	@Autowired private NasdaqSite nasdaqSite;
	
	protected void runJob() throws SQLException {
		Stocks allStocks = null;
		try {
			allStocks = nasdaqSite.getAllStocks();
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
			
			if (database.wasStockAlreadyTweeted(stock)) {
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
				database.insertStockTweet(stock, Instant.now().toString());
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Tweeted but couldn't record in database");
				System.exit(1);
			}
		}
	}
}
