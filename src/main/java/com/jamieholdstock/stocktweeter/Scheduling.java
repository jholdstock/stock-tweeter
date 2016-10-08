package com.jamieholdstock.stocktweeter;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;
import com.jamieholdstock.stocktweeter.yahoo.NasdaqSite;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduling {
	
	@Scheduled(fixedDelay=100000)
	public void checkStock() {
		try {
			new NasdaqSite();
		} catch (StockException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return;
		}
		System.out.println("Done");
	}
	
}
