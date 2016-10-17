package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public abstract class Job {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	protected abstract void runJob() throws SQLException, StockException;
	
	public void run() throws SQLException, StockException {
		String className = this.getClass().getSimpleName();
		log.info("===========================================");
		log.info("Scheduled task '" + className + "' starting");
		log.info("===========================================");
		runJob();
		log.info("===========================================");
		log.info("Scheduled task '" + className + "' complete");
		log.info("===========================================");
	}
}
