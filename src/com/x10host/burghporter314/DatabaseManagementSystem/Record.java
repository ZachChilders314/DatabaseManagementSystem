package com.x10host.burghporter314.DatabaseManagementSystem;

public class Record {
	
	private Column[] columns;
	private String[] values;
	private int position = 0;
	
	public Record(Column[] columns) {
		this.columns = columns;
		this.values = new String[columns.length];
	}
	
	public void addValue(String value) {
		values[position++] = value;
	}
	
	public String[] getValues() {
		return this.values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
		this.position = values.length - 1;
	}
	
	public boolean isFull() {
		return ((position + 1) == this.values.length);
	}
}
