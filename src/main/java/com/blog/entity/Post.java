package com.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
/* This annotation gives us the getters and setters 
 * @Getters and @Setters also gives us getters and setters*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=false, unique=true)
	private String title;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@Lob
	@Column(name="content", nullable=false)
	private String content;
	
	
	/*
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Comment> comment = new HashSet<>();
	*/
	//how will this know which variable is the mapped one 
	//this variable is mapped-by post variable which is present in other class
	//cascade changes in one table will affect changes in other table
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<Comment> comments = new HashSet<>();
	
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
