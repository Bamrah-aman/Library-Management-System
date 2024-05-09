package com.GrabbingTheCode.bookmng.server.controller;


import com.GrabbingTheCode.bookmng.core.dto.*;
import com.GrabbingTheCode.bookmng.core.entity.Book;
import com.GrabbingTheCode.bookmng.core.entity.Library;
import com.GrabbingTheCode.bookmng.core.exception.LimitExceedsException;
import com.GrabbingTheCode.bookmng.core.exception.successResponse.SuccessResponse;
import com.GrabbingTheCode.bookmng.core.exception.successResponse.SuccessResponseReason;
import com.GrabbingTheCode.bookmng.server.service.AuthorService;
import com.GrabbingTheCode.bookmng.server.service.BookService;
import com.GrabbingTheCode.bookmng.server.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.GrabbingTheCode.bookmng.core.utility.BookDTOUtil.mapToBookDto;
import static com.GrabbingTheCode.bookmng.core.utility.DashBoardDTOUtil.mapToDashBoard;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final LibraryService libService;

    private final AuthorService authorService;

	@GetMapping("/dashboard")
	public ResponseEntity<DashboardDTO> dashboard(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize) {
		Book book = bookService.getDashboard();
		if (book == null) {
			return ResponseEntity.noContent().build();
		} else {
			Library library = book.getLibrary();
			if (library != null) {
				DashboardDTO dashboardDTO = mapToDashBoard(library);
				return ResponseEntity.ok(dashboardDTO);
			} else {
				return ResponseEntity.noContent().build();
			}
		}
	}

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Book> list = bookService.getBooks(pageNo, pageSize);
        ArrayList<BookDTO> bookDTOArrayList = new ArrayList<>();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (var getBooks : list) {
                BookDTO bookDTO = mapToBookDto(getBooks);
                bookDTOArrayList.add(bookDTO);
            }
        }
        return ResponseEntity.ok(bookDTOArrayList);
    }

    @GetMapping("/books/{authorOrBookName}")
    public ResponseEntity<List<BookDTO>> getBookByAuthorOrBookName(@PathVariable("authorOrBookName") String authorOrBookName,
                                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Book> list = bookService.getBooksByAuthorOrBookName(authorOrBookName, pageNo, pageSize);
        ArrayList<BookDTO> bookDTOArrayList = new ArrayList<>();
        for (var getBooks : list) {
            BookDTO bookDTO = mapToBookDto(getBooks);
            bookDTOArrayList.add(bookDTO);
        }
        return ResponseEntity.ok(bookDTOArrayList);
    }

    @PostMapping("/add-library")
    public ResponseEntity<Library> addLibrary(@Valid @RequestBody Library library) {
        Library savedLibrary = libService.addLibrary(library);
        URI uri = URI.create("api/v1/add-library");
        return ResponseEntity.created(uri).body(savedLibrary);
    }

    @PostMapping("/add-book")
    public ResponseEntity<SuccessResponse> addBook(@Valid @RequestBody Book book,
                                                   @RequestParam(defaultValue = "College Library") String libraryName) {
        Book getBook = bookService.addBook(book, libraryName);
        if (getBook != null) {
            SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.BOOKS_ADDED_SUCCESSFULLY);
            return ResponseEntity.created(URI.create("api/v1/add-book")).body(successResponse);
        }
        throw new RuntimeException("Something went wrong");
    }

    @PostMapping("/add-booklist")
    public ResponseEntity<SuccessResponse> addBooksList(@Valid @RequestBody List<Book> book,
                                                        @RequestParam(defaultValue = "College Library") String libraryName)
            throws LimitExceedsException {
        List<Book> getBook = bookService.addBooksList(book, libraryName);
        if (getBook != null) {
            SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.BOOKS_ADDED_SUCCESSFULLY);
            return ResponseEntity.created(URI.create("api/v1/add-book")).body(successResponse);
        }
        throw new RuntimeException("Something went wrong");
    }

    @PutMapping("/update-author")
    public ResponseEntity<SuccessResponse> updateBookAuthor(@Valid @RequestBody UpdateAuthor
                                                            updateAuthor) {
        Book book = authorService.updateAuthor(updateAuthor);
        if(book != null) {
            SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.AUTHOR_UPDATED_SUCCESSFULLY);
            return ResponseEntity.ok(successResponse);
        }
        throw new RuntimeException("Something went wrong");
    }

    @PutMapping("/update-bookname")
    public ResponseEntity<SuccessResponse> updateBookName(@Valid @RequestBody UpdateBook
                                                          updateBook) {
        Book book = bookService.updateBookName(updateBook);
        if(book != null) {
            SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.BOOKS_UPDATED_SUCCESSFULLY);
            return ResponseEntity.ok(successResponse);
        }
        throw new RuntimeException("Something went wrong");
    }

    @DeleteMapping("/delete-author/{author}")
    public ResponseEntity<SuccessResponse> deleteAuthor(@PathVariable("author") String
                                                  author) {
        authorService.deleteAuthor(author);
        SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.DELETE_THE_AUTHOR);
        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping("/delete-book")
    public ResponseEntity<SuccessResponse> deleteBook(@Valid @RequestBody DeleteBook deleteBook) {
        bookService.deleteBook(deleteBook);
        SuccessResponse successResponse = new SuccessResponse(SuccessResponseReason.DELETE_THE_BOOK);
        return ResponseEntity.ok(successResponse);
    }

}

