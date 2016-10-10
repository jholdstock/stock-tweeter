package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduling {
	
	@Autowired private TweetJob tweetJob;
	@Autowired private CleanupJob cleanupJob;
	
	@Scheduled(fixedDelay=1000000, zone="UTC")
	public void tweetJob() throws SQLException {
		tweetJob.run();
	}
	
	@Scheduled(fixedDelay=1000000, zone="UTC")
	public void cleanupJob() throws SQLException {
		cleanupJob.run();
	}
}
