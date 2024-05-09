package com.GrabbingTheCode.bookmng.core.repo;

import com.GrabbingTheCode.bookmng.core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer>{

    @Query("select b from Book b where b.bookName=?1")
    Book bookFindByName(String BookName);

    @Query("select b from Book b where b.bookName like %:partialName%")
    List<Book> getBooksByPartialName(@Param("partialName") String partialName);

    @Query("SELECT b FROM Book b WHERE b.dateTime = (SELECT MAX(b2.dateTime) FROM Book b2)")
    Book getLatestBook();
}
