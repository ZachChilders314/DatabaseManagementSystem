package com.x10host.burghporter314.DatabaseManagementSystem;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String INPUT_FILE_PATH_LOG = "LOG";
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
		
		while(true) {
			
			System.out.println("1 - Create New Database | 2 - Add Entry | 3 - Remove Entry | 4 - List Database | 5 - Purge Database | 6 - Exit");
			userInput = input.nextInt(); input.nextLine();
			
			DatabaseFile file;
			Column[] columns;
			Record record;
			
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
					break;
					
				case 2:
					
					System.out.print("Enter File Name: ");
					file = new DatabaseFile(input.nextLine());
					
					columns = file.getColumnArray();
					record = new Record(columns);
					
					for(Column column: columns) {
						System.out.print(column.getName() + ": ");
						record.addValue(input.nextLine());
					}
					
					file.insert(record);
					break;
				case 3:
					
					System.out.print("Enter File Name: ");
					file = new DatabaseFile(input.nextLine());
					
					columns = file.getColumnArray();
					record = new Record(columns);
					
					for(Column column: columns) {
						System.out.print(column.getName() + ": ");
						record.addValue(input.nextLine());
					}
					
					file.remove(record);
					break;
				case 4:
					
					System.out.print("Enter File Name: ");
					file = new DatabaseFile(input.nextLine());
					
					file.listFile();
					break;
				case 5:
					//Statements
					break;
				case 6:
					//Statements
					break;
				default:
					//Statements
					break;
			}
		}
		
	}

}
