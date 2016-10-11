package com.jamieholdstock.stocktweeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class Database {
	
	private String createTable = "CREATE TABLE stocks (ticker, last_tweeted);";
	private String emptyTable = "DELETE FROM stocks;";
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Database() throws SQLException {
		log.info("Initialising database");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        try {
        	//stat.executeUpdate("drop table if exists stocks;");
        	stat.executeUpdate(createTable);
        	log.info("Created a new database");
        }
        catch (SQLException e) {
        	if ("table stocks already exists".equals(e.getMessage())) {
        		log.info("Using existing database");
        	}
        	else {
        		log.error("Failed to initialise database");
        		throw e;
        	}
        }

        conn.close();
	}
	
	public void emptyStocks() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();

    	stat.executeUpdate(emptyTable);
    	
        conn.close();
	}

	public boolean wasStockAlreadyTweeted(Stock stock) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        String query = "SELECT * FROM stocks WHERE ticker='" + stock.getTicker() + "';";
        
        ResultSet set = stat.executeQuery(query);
        boolean foundRow = set.next();

        conn.close();
        return foundRow;
	}
	
	public void insertStockTweet(Stock stock, String timestamp) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        String query = "INSERT INTO stocks (ticker, last_tweeted) VALUES('" + stock.getTicker() + "', '" + timestamp +"');";
        
        stat.executeUpdate(query);

        conn.close();
	}
	
}
