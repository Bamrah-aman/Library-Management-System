package com.GrabbingTheCode.bookmng.server.service;


import com.GrabbingTheCode.bookmng.core.dto.DeleteBook;
import com.GrabbingTheCode.bookmng.core.dto.UpdateBook;
import com.GrabbingTheCode.bookmng.core.entity.Author;
import com.GrabbingTheCode.bookmng.core.entity.Book;
import com.GrabbingTheCode.bookmng.core.entity.Library;
import com.GrabbingTheCode.bookmng.core.exception.*;
import com.GrabbingTheCode.bookmng.core.repo.AuthorRepo;
import com.GrabbingTheCode.bookmng.core.repo.BookRepo;
import com.GrabbingTheCode.bookmng.core.repo.LibRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    private final LibRepo libRepo;
    private final AuthorRepo authorRepo;

    @Transactional
    public Book getDashboard() {
        return bookRepo.getLatestBook();
    }

    @Transactional
    public List<Book> getBooks(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize,
                Sort.by("author.authorName").ascending());
        Page<Book> pageContent = bookRepo.findAll(pageRequest);
        return pageContent.getContent();
    }

    @Transactional
    public List<Book> getBooksByAuthorOrBookName(String authorOrBookName, Integer pageNo, Integer pageSize) {
        List<Book> books = bookRepo.getBooksByPartialName(authorOrBookName);
        Author author = authorRepo.authorFindByName(authorOrBookName);
        if (!books.isEmpty()) {
            return books;
        } else if (author != null) {
            return author.getBookList();
        } else {
            throw new BookNotFoundException(BookExceptionReason.BOOKS_NOT_FOUND);
        }
    }

    @Transactional
    public List<Book> addBooksList(List<Book> book, String libraryName) throws LimitExceedsException {
        Library getLibrary = libRepo.libraryFindByName(libraryName);
        if (getLibrary != null) {
            if (book.size() <= 5) {
                for (var setBook : book) {
                    Optional<Book> findBook = Optional.ofNullable(bookRepo.bookFindByName(setBook.getBookName()));
                    if (findBook.isEmpty()) {
                        setLibraryAndAddBook(getLibrary, setBook);
                    } else {
                        throw new BookAlreadyExistsException(BookAlreadyExistsReason.BOOK_ALREADY_EXISTS);
                    }
                }
            } else {
                throw new LimitExceedsException(LimitExceedsExceptionReason.LIMIT_EXCEEDS_FOR_PAGE);
            }
        }
        log.info("Saved the books {}", book);
        return book;
    }

    @Transactional
    public Book addBook(Book book, String libraryName) {
        Library getLibrary = libRepo.libraryFindByName(libraryName);
        if (getLibrary != null) {
            Optional<Book> findBook = Optional.ofNullable(bookRepo.bookFindByName(book.getBookName()));
            if (findBook.isEmpty()) {
                setLibraryAndAddBook(getLibrary, book);
            } else {
                throw new BookAlreadyExistsException(BookAlreadyExistsReason.BOOK_ALREADY_EXISTS);
            }
        }
        log.info("Saved the book: {}", book);
        return book;
    }

    @Transactional
    public Book updateBookName(UpdateBook updateBook) {
        Book oldbook = bookRepo.bookFindByName(updateBook.oldBook());
        Author author = authorRepo.authorFindByName(updateBook.authorName());
        if (author != null) {
            if (oldbook == null) {
                throw new BookNotFoundException(BookExceptionReason.INCORRECT_OLD_BOOK_NAME);
            } else {
                oldbook.setBookName(updateBook.newBook());
                bookRepo.save(oldbook);
                return oldbook;
            }
        } else {
            throw new AuthorNotFoundException(AuthorExceptionReason.AUTHOR_NOT_EXIST_CHECK_AGAIN_CANNOT_UPDATE_BOOK);
        }
    }


    @Transactional
    public void deleteBook(DeleteBook deleteBook) {
        Author author = authorRepo.authorFindByName(deleteBook.authorName());
        Book book = bookRepo.bookFindByName(deleteBook.bookName());
        log.info("Author: {}", author);
        log.info("Book: {}", book);
        if (author != null && book != null && book.getAuthor().getAuthorName().equals(author.getAuthorName())) {
            if ((long) author.getBookList().size() == 1L) {
                updateTotalBooksDelete(book);
                authorRepo.delete(author);
            } else {
                author.getBookList().remove(book);
                book.setAuthor(null);
                bookRepo.delete(book);
                author.setBookCount(author.getBookCount() - 1);

                //Updating the total number or books here
                updateTotalBooksDelete(book);
            }
        } else {
            throw new BookNotFoundException(BookExceptionReason.BOOK_OR_AUTHOR_NOT_MATCH_CHECK_AGAIN);
        }
    }

    private void setLibraryAndAddBook(Library getLibrary, Book setBook) {
        setBook.setLibrary(getLibrary);
        Author author = setBook.getAuthor();
        Author authorFindByName = authorRepo.authorFindByName(author.getAuthorName());
        log.info("Getting the authorByName: {}", authorFindByName);
        if (authorFindByName != null) {
			/*In this case author already exists and need to save the book first coz it is transient state and
			then save the updated author also*/
            bookRepo.save(setBook);
            authorFindByName.addBook(setBook);
            updateTotalBooksAdd(getLibrary, authorFindByName);
            authorRepo.saveAndFlush(authorFindByName);
        } else {
			/*In this case author is new and need to save the book first coz it is transient state
			and then save the updated author also*/
            bookRepo.save(setBook);
            author.addBook(setBook);
            updateTotalBooksAdd(getLibrary, author);
            authorRepo.saveAndFlush(author);
        }
    }

    private void updateTotalBooksDelete(Book book) {
        Library library = book.getLibrary();
        if (library != null) {
            var totalBooks = library.getTotalBooks();
            if (totalBooks != null && totalBooks >= 0) {
                library.setTotalBooks(totalBooks - 1);
            }
        }
    }

    private void updateTotalBooksAdd(Library library, Author author) {
        if (library != null) {
            Long totalBooks = library.getTotalBooks();
            if (totalBooks == null) {
                totalBooks = 0L;
            }
            ++totalBooks;
            library.setTotalBooks(totalBooks);
        }
    }

}
