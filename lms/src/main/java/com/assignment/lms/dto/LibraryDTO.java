package com.assignment.lms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LibraryDTO {

	private Long id;
	
	@NotEmpty(message = "Library name can not be empty.")
	@NotNull(message = "Library name can not be null.")
    private String name;
	
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
    
}
