package com.jamieholdstock.stocktweeter.scheduled;

import java.sql.SQLException;

public abstract class Job {
	abstract protected void runJob() throws SQLException;
	
	public void run() throws SQLException {
		String className = this.getClass().getSimpleName();
		System.out.println("Scheduled task '" + className + "' starting");
		runJob();
		System.out.println("Scheduled task '" + className + "' complete");
	}
}
