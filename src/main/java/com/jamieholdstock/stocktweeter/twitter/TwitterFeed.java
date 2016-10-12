package com.jamieholdstock.stocktweeter.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class TwitterFeed {
	private Twitter twitter;
	private String urlFormat;
	private boolean actuallySend;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public TwitterFeed(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, String urlFormat, boolean actuallySend) {
		twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		this.urlFormat = urlFormat;
		this.actuallySend = actuallySend;
	}

	public void tweetMovedStock(Stock stock) {
		if (actuallySend) {
			String fullUrl = urlFormat + stock.getTicker();
			twitter.timelineOperations().updateStatus(stock.getTicker() + " has moved " + stock.getChange() + "% " + fullUrl);
			log.info("Tweeted " + stock.toString());
		}
		else {
			log.info("Tweeted " + stock.toString() + " (DRY RUN)");
		}
	}
}
