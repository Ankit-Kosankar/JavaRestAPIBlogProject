package com.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
//hibernate validator wont give the response or message by default 
//you will need binding result class
//vlaidate the required field and bind result and see if error are there or not
@Data
@Getter
@Setter
public class PostDto {

	private Long id;
	
	@NotNull
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	@NotEmpty
	@NotNull
	@Size(min = 10, max = 250, message = "Post Description should have at least 10 chararacters")
	private String description;
	
	@NotNull
	private String content;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
