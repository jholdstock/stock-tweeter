package com.jamieholdstock;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableAsync
@EnableScheduling
public class StockChecker {
	
	@Scheduled(fixedDelay=5000)
	public void doSomething() {
	    System.out.println("HI!");
	}
	
}
