package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	protected void runJob() throws SQLException, StockException {
		Stocks allStocks = nasdaqSite.getAllStocks();
		
		Stocks movedStocks = allStocks.getMovedStocks();
		log.info(movedStocks.size() + " have moved more than 5%");

		Stocks toTweet = new Stocks();
		for (Stock stock : movedStocks) {
			if (database.wasStockAlreadyTweeted(stock) == false) {
				toTweet.add(stock);
			}
		}

		log.info(toTweet.size() + " of these have not been tweeted");
		
		for (Stock stock : toTweet) {
			twitterFeed.tweetMovedStock(stock);
			database.insertStockTweet(stock, Instant.now().toString());
		}
	}
}
