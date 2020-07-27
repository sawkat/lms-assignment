package com.assignment.lms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookDTO {
	
	private Long id;
	
	@NotEmpty(message = "Title can not be empty.")
	@NotNull(message = "Title can not be null.")
    private String title;
    
    private Integer noOfPages;
    
    @NotEmpty(message = "Author name can not be empty.")
	@NotNull(message = "Author name can not be null.")
    private String authorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
    
    

}
