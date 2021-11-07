package com.jvm.project.ws.workshopmongo.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String text;
	private Date date;
	private AuthorDTO author;

}