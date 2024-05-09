package com.GrabbingTheCode.bookmng.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Library")
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Library Name cannot be left Blank")
	private String libraryName;

	private Long totalBooks;

	@OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Book> listOfBooks;

	public Library() {
	}

}
