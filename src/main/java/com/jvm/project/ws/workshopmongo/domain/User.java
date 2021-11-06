package com.jvm.project.ws.workshopmongo.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"nome", "email"})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String email;

}