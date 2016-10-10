package com.jamieholdstock.stocktweeter.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class TwitterFeed {
	private Twitter twitter;
    
	public TwitterFeed(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	public void tweetMovedStock(Stock stock) {
		twitter.timelineOperations().updateStatus(stock.getTicker() + " has moved " + stock.getChange() + "% https://uk.finance.yahoo.com/q?s=" + stock.getTicker());
	}
}
