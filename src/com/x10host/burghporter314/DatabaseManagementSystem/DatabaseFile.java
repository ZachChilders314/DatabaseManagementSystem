/*
 * Dylan Porter (with Zachary Kuchar)
 * Duquesne University
 * Database Management Systems
 * Credit to https://docs.oracle.com/javase/7/docs/api/java/io/RandomAccessFile.html
 */

package com.x10host.burghporter314.DatabaseManagementSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DatabaseFile {
	
	private String name;
	private ArrayList<Column> columns;
	
	private int recordSize;
	private final String EXTENSION = ".dbf";
	
	private RandomAccessFile fileParser;
	private Scanner input;
	
	public DatabaseFile(String fileName) throws IOException {
		
		this();
		this.name = fileName;
		if(!((new File(fileName + EXTENSION)).exists())) {
			throw new java.lang.Error("Could not Find File"); // The File has not been created, so we can't open it.
		}
		
		this.fileParser = new RandomAccessFile(fileName + EXTENSION, "rw");
		
		int numColumns = this.fileParser.readInt();
		this.recordSize = this.fileParser.readInt();

		for(int i = 0; i < numColumns; i++) {
			this.columns.add(new Column(this.fileParser.readUTF(), 
					this.fileParser.readInt()));
		}
	}
	
	public DatabaseFile() {
		recordSize = 0;
		columns = new ArrayList<Column>();
		this.input = new Scanner(System.in);
	}
	
	public void createTable() throws IOException {
		
		(new File(this.name + EXTENSION)).createNewFile();
		this.fileParser = new RandomAccessFile(this.name + EXTENSION, "rw");
		
		this.fileParser.writeInt(this.columns.size());
		
		this.fileParser.writeInt(this.recordSize);
		
		for(Column column : this.columns) {
			this.fileParser.writeUTF(column.getName()); 
			this.fileParser.writeInt(column.getSize());
		}

		this.fileParser.close();
		
	}
	
	public void insert(Record record) throws IOException {
		
		HashMap<String, String> map = record.getValueMapper();
		long pos = (new File(this.name + this.EXTENSION)).length();
		
		this.fileParser.seek(pos);
		
		for(Column column: this.columns) {
			addEntry(column.getSize(), map.get(column.getName()));
		}
		
	}
	
	public void remove() {
		
	}
	
	public void listFile() {
		
	}
	
	public void purge() {
		
	}
	
	public void addEntry(int columnSize, String value) throws IOException {
		
		int paddedZeros = columnSize - value.length();
		int itemsRemaining = columnSize;
		
		while(paddedZeros > 0) {
			this.fileParser.writeChar(' ');
			paddedZeros--;
			itemsRemaining--;
		}
		
		for(int i = 0; itemsRemaining > 0; i++) {
			this.fileParser.writeChar(value.charAt(i));
			itemsRemaining--;
		}
		
	}
	
	public void addColumn(Column column) {
		this.columns.add(column);
		recordSize += column.getSize();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Column[] getColumnArray() {
		
		Column[] temp = new Column[this.columns.size()];
		
		for(int i = 0; i < this.columns.size(); i++) {
			temp[i] = this.columns.get(i);
		}
		
		return temp;
	}
	
}
