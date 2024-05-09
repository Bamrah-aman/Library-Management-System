package com.GrabbingTheCode.bookmng.core.repo;

import com.GrabbingTheCode.bookmng.core.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<Author, Integer>{

    @Query("select a from Author a where a.authorName=?1")
    Author authorFindByName(String name);


}
