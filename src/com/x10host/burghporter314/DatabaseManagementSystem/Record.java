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
	
	@Override
	public boolean equals(Object o) {
		
		if(o == this) {
			return true;
		}
		
		if(!(o instanceof Record)) {
			return false;
		}
		
		Record record = (Record)(o);
		HashMap<String, String> map = record.getValueMapper();
		
		for(String key : map.keySet()) {
			
			if(map.get(key).isEmpty()) { continue; }
			
			if(!this.valueMapper.get(key).equals(map.get(key))) {
				return false;
			}
		}
		
		return true;
		
	}
	
	
}
