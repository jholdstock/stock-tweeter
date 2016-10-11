package com.jamieholdstock.stocktweeter.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class TwitterFeed {
	private Twitter twitter;
	private String urlFormat;
    
	public TwitterFeed(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, String urlFormat) {
		twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		this.urlFormat = urlFormat;
	}

	public void tweetMovedStock(Stock stock) {
		String fullUrl = urlFormat + stock.getTicker();
		twitter.timelineOperations().updateStatus(stock.getTicker() + " has moved " + stock.getChange() + "% " + fullUrl);
	}
}
