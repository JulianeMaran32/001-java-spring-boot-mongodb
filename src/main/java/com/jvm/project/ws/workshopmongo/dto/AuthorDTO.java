package com.jvm.project.ws.workshopmongo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;

}