package com.jamieholdstock.stocktweeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jamieholdstock.stocktweeter.stockchecker.Stock;

public class Database {
	
	private String createTable = "CREATE TABLE stocks (ticker, last_tweeted);";
	private String emptyTable = "DELETE FROM stocks;";
	
	public Database() throws SQLException {
		System.out.println("Initialising database");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        try {
        	//stat.executeUpdate("drop table if exists stocks;");
        	System.out.println(createTable);
        	stat.executeUpdate(createTable);
        	System.out.println("Created a new database");
        }
        catch (SQLException e) {
        	if ("table stocks already exists".equals(e.getMessage())) {
        		System.out.println("Using existing database");
        	}
        	else {
        		System.out.println("Failed to initialise database");
        		throw e;
        	}
        }

        conn.close();
	}
	
	public void emptyStocks() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();

        System.out.println(emptyTable);
    	stat.executeUpdate(emptyTable);
    	
        conn.close();
	}

	public boolean wasStockAlreadyTweeted(Stock stock) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        String query = "SELECT * FROM stocks WHERE ticker='" + stock.getTicker() + "';";
        System.out.println(query);
        
        ResultSet set = stat.executeQuery(query);
        boolean foundRow = set.next();

        conn.close();
        return foundRow;
	}
	
	public void insertStockTweet(Stock stock, String timestamp) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test.db");
        Statement stat = conn.createStatement();
        String query = "INSERT INTO stocks (ticker, last_tweeted) VALUES('" + stock.getTicker() + "', '" + timestamp +"');";
        System.out.println(query);
        
        stat.executeUpdate(query);

        conn.close();
	}
	
}
