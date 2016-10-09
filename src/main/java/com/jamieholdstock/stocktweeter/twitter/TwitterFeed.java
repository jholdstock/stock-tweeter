package com.jamieholdstock.stocktweeter.twitter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class TwitterFeed {
	
	private static String consumerKey = "Cz7UZurxtpN2mjoF9BUkoEXf4"; 
	private static String consumerSecret = "eS9RjPA4b7CfdVGPWh17WOdGnXWc39kq52cnSCbq9IchqAxMoH";
    private static String accessToken = "784130958370959361-6x9S801anSsqWldidCBCd61gcaLIUxm"; 
    private static String accessTokenSecret = "4Fk9SCpovlfJe7Kv8VkEi2nisaB8pwqWfKssLc3uzULYW";
    private static String twitterName = "jamiebot526";
    
    private static Twitter twitter;
    
	public TwitterFeed() {
		twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
		
    public boolean hasAlreadyBeetTweeted(String ticker) {
    	String search = "@" + twitterName + " " + ticker;
    	System.out.println("Searching for '" + search + "'");
    	List<Tweet> tweets = twitter.searchOperations().search(search).getTweets();
    	System.out.println("Found " + tweets.size());
    	if (tweets.size() == 0) {
    		return false;
    	}

    	for (Tweet tweet : tweets) {
    		System.out.println("Testing '" + tweet.getText() + "'");
    		Pattern p = Pattern.compile(ticker + " has moved");
    		Matcher m = p.matcher(tweet.getText());
    		if (m.matches()) {
    			System.out.println("Matches pattern");
    			return true;
    			
    		}
    		System.out.println("Doesnt match pattern");
    	}
    	
		return false;
	}
	
	public void tweetMovedStock(Stock stock) {
		twitter.timelineOperations().updateStatus(stock.getTicker() + " has moved " + stock.getChange() + "%");
	}
}
