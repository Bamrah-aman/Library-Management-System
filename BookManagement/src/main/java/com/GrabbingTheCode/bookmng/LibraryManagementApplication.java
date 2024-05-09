package com.GrabbingTheCode.bookmng;


import com.GrabbingTheCode.bookmng.core.entity.Library;
import com.GrabbingTheCode.bookmng.core.repo.LibRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(LibRepo libRepo) {
		return args -> {
			Library library = libRepo.libraryFindByName("College Library");
			if(library == null) {
				Library collegeLibrary = new Library();
				collegeLibrary.setLibraryName("College Library");
				libRepo.save(collegeLibrary);
			}
		};
	}

}