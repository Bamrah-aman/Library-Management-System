package com.GrabbingTheCode.bookmng.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Book")
@Slf4j
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Book name cannot be left Blank")
	private String bookName;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "library_id")
	private Library library;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateTime;

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", bookName='" + (bookName != null ? bookName : null) +
				", authorName=" + (author != null ? author.getAuthorName() : "null") +
				", libraryName=" + (library != null ? library.getLibraryName() : "null") +
				'}';
	}

}
