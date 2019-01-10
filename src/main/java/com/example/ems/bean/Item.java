package com.example.ems.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Item {

	private String name;

	private int count;

	@JsonIgnore
	public String id;

	@JsonIgnore
	public String assignedTo;

	@JsonIgnore
	public long assignedOn;

}
