package com.x10host.burghporter314.DatabaseManagementSystem;

import java.util.HashMap;

public class Record {
	
	private HashMap<String, String> valueMapper = new HashMap<String, String>();	
	
	private Column[] columns;
	private String[] values;
	private int position = 0;
	
	public Record(Column[] columns) {
		this.columns = columns;
		this.values = new String[columns.length];
	}
	
	public void addValue(String value) {
		this.valueMapper.put(this.columns[position].getName(), value);
		values[position++] = value;
	}
	
	public String[] getValues() {
		return this.values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
		this.position = values.length - 1;
	}
	
	public Column[] getColumns() {
		return this.columns;
	}
	
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}
	
	public HashMap<String, String> getValueMapper() {
		return this.valueMapper;
	}
	
	public void setValueMapper(HashMap<String, String> valueMapper) {
		this.valueMapper = valueMapper;
	}
	
	public boolean isFull() {
		return ((position + 1) == this.values.length);
	}
}
