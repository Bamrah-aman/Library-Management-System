package com.GrabbingTheCode.bookmng.server.service;

import com.GrabbingTheCode.bookmng.core.entity.Library;
import com.GrabbingTheCode.bookmng.core.repo.LibRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {
	
	private final LibRepo libRepo;
	
	@Transactional
	public Library addLibrary(Library library) {
		String libraryName = library.getLibraryName();
		System.out.println(libraryName);
		Library getLibrary = libRepo.libraryFindByName(libraryName);
		if(getLibrary != null) {
			throw new RuntimeException("Library already Exists Kindly change the Name");
		}
        return libRepo.save(library);
	}

}
