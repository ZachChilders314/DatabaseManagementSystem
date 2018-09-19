package com.x10host.burghporter314.DatabaseManagementSystem;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String INPUT_FILE_PATH_LOG = "LOG";
	
	public static void main(String[] args) throws SecurityException, IOException {
		
		//Logger Formatter Stuff
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n"); 
		
		FileHandler fh = new FileHandler(INPUT_FILE_PATH_LOG,true);
		LOGGER.addHandler(fh);
		
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		
		LOGGER.info("Hellfdsao");
		
	}

}
