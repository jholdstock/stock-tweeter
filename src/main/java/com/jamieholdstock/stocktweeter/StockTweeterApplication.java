package com.jamieholdstock.stocktweeter;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("beans.xml")
public class StockTweeterApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(StockTweeterApplication.class, args);
	}
}
