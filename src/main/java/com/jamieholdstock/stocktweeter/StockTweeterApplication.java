package com.jamieholdstock.stocktweeter;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockTweeterApplication {

	public static void main(String[] args) throws SQLException {
        Database.initialise();
		SpringApplication.run(StockTweeterApplication.class, args);
	}
}
