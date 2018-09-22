package com.x10host.burghporter314.DatabaseManagementSystem;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String INPUT_FILE_PATH_LOG = "dbFiles";
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws SecurityException, IOException {
		
		//Logger Formatter Stuff for Output
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n"); 
		
		//This writes all log output to a file named INPUT_FILE_PATH_LOG
		FileHandler fh = new FileHandler(INPUT_FILE_PATH_LOG,true);
		LOGGER.addHandler(fh);
		
		//We need to format our log output
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		
		int userInput = 0;
		DatabaseFile file = null;
		
		Column[] columns;
		Record record;
		
		System.out.println("1 - Create new Table | 2 - Load Existing Table");
		userInput = input.nextInt(); input.nextLine();
		
		switch(userInput) {
			case 1:
				
				file = new DatabaseFile();
				String columnData;
				
				System.out.print("Enter Table Name: ");
				file.setName(input.nextLine());
				
				while(true) {
					
					System.out.print("Enter Column Name and its Length: ");
					columnData = input.nextLine();
					
					if(columnData.isEmpty()) { break; }
					file.addColumn(new Column(columnData));
					
				}
				
				file.createTable();
				LOGGER.info(file.getName());

				break;
			
			case 2:
				
				System.out.print("Enter File Name: ");
				file = new DatabaseFile(input.nextLine());
				System.out.println(file.getName());
				
				break;
				
		}
		
		
		outer:
		while(true) {
			
			columns = file.getColumnArray();
			record = new Record(columns);
			
			System.out.println("1 - Add Entry | 2 - Remove Entry | 3 - List Database | 4 - Exit");
			userInput = input.nextInt(); input.nextLine();
			
			switch(userInput) {
					
				case 1:
					
					for(Column column: columns) {
						System.out.print(column.getName() + ": ");
						record.addValue(input.nextLine());
					}
					
					file.insert(record);
					break;
					
				case 2:
					
					for(Column column: columns) {
						System.out.print(column.getName() + ": ");
						record.addValue(input.nextLine());
					}
					
					file.remove(record);
					break;
					
				case 3:
					
					file.listFile();
					break;
					
				case 4:
					
					file.purge();
					break outer;
			
				default:
					System.out.println("ERROR: Invalid Input");
					break;
			}
		}
		
	}

}
