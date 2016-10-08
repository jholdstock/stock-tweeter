package com.jamieholdstock.stocktweeter;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.stockchecker.YahooNdqSite;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduling {
	
	@Scheduled(fixedDelay=10000)
	public void checkStock() {
		try {
			new YahooNdqSite();
		} catch (StockException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return;
		}
		System.out.println("Done");
	}
	
}
