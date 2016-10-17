package com.jamieholdstock.stocktweeter.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.ResourceAccessException;

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
//		try {
//			twitter.timelineOperations().updateStatus(stock.getTicker() + " has moved " + stock.getChange() + "% " + fullUrl);
//		}
//		catch(ResourceAccessException e) {
//			for (int i = 1; i <= 3; i++) {
//				 +			try{
//				 +				doc = Jsoup.connect(url).get();
//				 +				break; 
//				 +			}
//				 +			catch (IOException e){
//				 +				System.out.println("NasdaqClient error occurred " + i + " time(s)");
//				 +			}                 
//				 +		}
//		}
	}
}
