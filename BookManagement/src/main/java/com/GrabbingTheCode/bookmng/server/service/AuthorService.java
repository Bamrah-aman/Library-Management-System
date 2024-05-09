package com.GrabbingTheCode.bookmng.server.service;


import com.GrabbingTheCode.bookmng.core.dto.UpdateAuthor;
import com.GrabbingTheCode.bookmng.core.entity.Author;
import com.GrabbingTheCode.bookmng.core.entity.Book;
import com.GrabbingTheCode.bookmng.core.entity.Library;
import com.GrabbingTheCode.bookmng.core.exception.AuthorExceptionReason;
import com.GrabbingTheCode.bookmng.core.exception.AuthorNotFoundException;
import com.GrabbingTheCode.bookmng.core.exception.BookExceptionReason;
import com.GrabbingTheCode.bookmng.core.exception.BookNotFoundException;
import com.GrabbingTheCode.bookmng.core.repo.AuthorRepo;
import com.GrabbingTheCode.bookmng.core.repo.BookRepo;
import com.GrabbingTheCode.bookmng.core.repo.LibRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;

    private final BookRepo bookRepo;

    private final LibRepo libRepo;

    @Transactional
    public Book updateAuthor(UpdateAuthor updateBookOrAuthorDeleteBook) {
        Book book = bookRepo.bookFindByName(updateBookOrAuthorDeleteBook.bookName());
        Author oldAuthor = authorRepo.authorFindByName(updateBookOrAuthorDeleteBook.oldAuthorName());
        Author newAuthor = authorRepo.authorFindByName(updateBookOrAuthorDeleteBook.newAuthorName());
        log.info("book: {}, Old Author: {}, New Author: {}", book, oldAuthor, newAuthor);
        if (book != null && oldAuthor != null) {
            if (newAuthor == null) {
                Author newAuthorCreated = new Author();
                newAuthorCreated.setAuthorName(updateBookOrAuthorDeleteBook.newAuthorName());
                newAuthorCreated.addBook(book);
                authorRepo.save(newAuthorCreated);
                oldAuthor.getBookList().remove(book);
                authorRepo.save(oldAuthor);
            } else {
                newAuthor.addBook(book);
                authorRepo.saveAndFlush(newAuthor);
                oldAuthor.getBookList().remove(book);
                authorRepo.save(oldAuthor);
            }
            if (oldAuthor.getBookList().isEmpty()) {
                authorRepo.delete(oldAuthor);
            }
        } else {
            throw new BookNotFoundException(BookExceptionReason.BOOK_OR_AUTHOR_NOT_MATCH_CHECK_AGAIN);
        }
        log.info("Updated Book author: {}", book);
        return book;
    }

    @Transactional
    public void deleteAuthor(String author) {
        Author getAuthor = authorRepo.authorFindByName(author);
        if (getAuthor != null) {
            updateTotalBooksWhenDeleteAuthor(getAuthor);
        } else {
            throw new AuthorNotFoundException(AuthorExceptionReason.AUTHOR_NOT_FOUND);
        }
    }

    private void updateTotalBooksWhenDeleteAuthor(Author author) {
        if (author != null) {
            List<Book> listOfBooks = author.getBookList();
            if(listOfBooks != null && !listOfBooks.isEmpty()) {
                //Since we have only one Library we can get library instance from any element
                Library library =  listOfBooks.get(0).getLibrary();
                if (library != null) {
                    Long totalBooks = library.getTotalBooks();
                    if (totalBooks != null && listOfBooks.size() < totalBooks) {
                        totalBooks -= listOfBooks.size();
                    } else {
                        totalBooks = 0L;
                    }
                    library.setTotalBooks(totalBooks);
                    libRepo.save(library);
                }
            }
            authorRepo.delete(author);
        }
    }
}
