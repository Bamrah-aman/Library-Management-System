package com.GrabbingTheCode.bookmng.core.repo;


import com.GrabbingTheCode.bookmng.core.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface LibRepo extends JpaRepository<Library, Integer> {

    @Query("select l from Library l where l.libraryName=?1")
    Library libraryFindByName(String name);


}
