/*
 * Dylan Porter (with Zachary Kuchar)
 * Duquesne University
 * Database Management Systems
 */

package com.x10host.burghporter314.DatabaseManagementSystem;

public class Column {

	private String name;
	private int size;
	
	public Column(String input) {
		
		//Split the String by spaces
		String[] components = input.split("\\s");
		
		//First element will be the column name
		this.name = components[0];
		
		//Second element will be the length of the column
		this.size = Integer.parseInt(components[1]);
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
}
