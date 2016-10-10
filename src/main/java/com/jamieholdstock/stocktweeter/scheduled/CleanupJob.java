package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jamieholdstock.stocktweeter.Database;

public class CleanupJob extends Job {
	@Autowired private Database database;
	
	protected void runJob() throws SQLException {
		database.emptyStocks();
	}
}
