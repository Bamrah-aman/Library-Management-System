package com.GrabbingTheCode.bookmng.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author")
@NoArgsConstructor
@Data
@Slf4j
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Author name cannot be left Blank")
	private String authorName;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Book> bookList;

	private Long bookCount;

	public void addBook(Book book) {
		if(bookList == null) {
			bookList = new ArrayList<>();
		}
		bookList.add(book);
		if (bookCount == null) {
			bookCount = 0L;
		}
		bookCount++;
		book.setAuthor(this);
		book.setDateTime(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "Author{" +
				"id=" + id +
				", authorName='" + authorName + '\'' +
				", bookList=" + bookList +
				'}';
	}
}
