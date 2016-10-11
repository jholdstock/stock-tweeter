package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Job {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	protected abstract  void runJob() throws SQLException;
	
	public void run() throws SQLException {
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
