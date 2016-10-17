package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;

@Configuration
@EnableScheduling
public class Scheduling {
	
//	The NASDAQ Stock Market Trading Sessions (Eastern Time)
	
//	Pre-Market Trading Hours from 4:00 a.m. to 9:30 a.m.
//	Market Hours from 9:30 a.m. to 4:00 p.m.
//	After-Market Hours from 4:00 p.m. to 8:00 p.m.

	@Autowired private TweetJob tweetJob;
	@Autowired private CleanupJob cleanupJob;
	
	private final String tweetCron = "0 */5 10,11,12,13,14,15 * * 1,2,3,4,5";
	private final String earlyTweetCron = "0 1,6,11,16,21,26 9 * * 1,2,3,4,5";
	private final String cleanCron = "0 30 8 * * *";

	@Scheduled(cron=tweetCron, zone="EST")
	//@Scheduled(fixedDelay=10000, zone="EST")
	public void tweetJob() throws SQLException, StockException {
		tweetJob.run();
	}
	
	@Scheduled(cron=earlyTweetCron, zone="EST")
	//@Scheduled(fixedDelay=10000, zone="EST")
	public void earlyTweetJob() throws SQLException, StockException {
		tweetJob.run();
	}
	
	@Scheduled(cron=cleanCron, zone="EST")
	//@Scheduled(fixedDelay=30000, zone="EST")
	public void cleanupJob() throws SQLException, StockException {
		cleanupJob.run();
	}
}
