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
	private long firstRecordPosition;
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
		
		this.columns = getColumns();
		this.firstRecordPosition = this.fileParser.getFilePointer();
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

		this.fileParser.seek(0);
		this.columns = getColumns();
		this.firstRecordPosition = this.fileParser.getFilePointer();
		//this.fileParser.close();
		
	}
	
	public void insert(Record record) throws IOException {
		
		HashMap<String, String> map = record.getValueMapper();
		long pos = (new File(this.name + this.EXTENSION)).length();
		
		this.fileParser.seek(pos);
		
		for(Column column: this.columns) {
			addEntry(column.getSize(), map.get(column.getName()));
		}
		
	}
	
	public void remove(Record record) throws IOException {
		for(long i = this.firstRecordPosition; i < this.fileParser.length(); i += this.recordSize * 2) {
			removeIfEquals(i, record);
		}
	}
	
	public void listFile() throws IOException {
		
		this.fileParser.seek(0);
		int numColumns = this.fileParser.readInt();
		
		for(Column column: this.columns) {
			System.out.print(String.format("%1$-" + (column.getSize() + 2) + "s", "Column: " + column.getName()));;
		}
		System.out.println("");
		
		for(long i = this.firstRecordPosition; i < this.fileParser.length(); i += this.recordSize * 2) {
			
			this.fileParser.seek(i);
			if(this.fileParser.readChar() == '#') { continue; }
			
			Record record = getRecord(i);
			int counter = 0;
			
			for(String s : record.getValues()) {
				System.out.print(String.format("%1$-" + (this.columns.get(counter++).getSize() + 2) + "s", s));
			}
			
			System.out.println("");
		}

	}
	
	public void purge() throws IOException {
		
		String originalName = this.getName();
		this.setName(this.getName() + "temp");
		
		createTable();
		
		RandomAccessFile tempReader = new RandomAccessFile(originalName + EXTENSION, "rw");
		tempReader.seek(this.firstRecordPosition);

		while(tempReader.getFilePointer() < tempReader.length()) {
			char s = tempReader.readChar();
			if(s == '#') {
				tempReader.skipBytes((this.recordSize * 2) - 2);
				continue;
			} 

			this.fileParser.writeChar(s);
		}
		
		(new File(originalName + EXTENSION)).delete();
		(new File(this.getName() + EXTENSION)).renameTo(new File(originalName + EXTENSION));
		
	}
	
	private void addEntry(int columnSize, String value) throws IOException {
		
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
	
	private ArrayList<Column> getColumns() throws IOException {
		
		ArrayList<Column> columns = new ArrayList<Column>();
		
		int numColumns = this.fileParser.readInt();
		this.recordSize = this.fileParser.readInt();

		for(int i = 0; i < numColumns; i++) {
			columns.add(new Column(this.fileParser.readUTF(), 
					this.fileParser.readInt()));
		}
		
		return columns;
		
	}
	
	private Record getRecord(long pos) throws IOException {
		
		this.fileParser.seek(pos);
		Record record = new Record(this.getColumnArray());
		
		for(Column column: this.columns) {
			
			StringBuilder s = new StringBuilder("");
			for(int i = 0; i < column.getSize(); i++) {
				s.append(this.fileParser.readChar());
			}
			
			record.addValue(s.toString().trim());
		}
		
		return record;
		
	}
	
	private void removeIfEquals(long index, Record record) throws IOException {
		Record currentRecord = getRecord(index);
		if(currentRecord.equals(record)) {
			this.fileParser.seek(index);
			this.fileParser.writeChar('#'); //Tombstone
		}
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
