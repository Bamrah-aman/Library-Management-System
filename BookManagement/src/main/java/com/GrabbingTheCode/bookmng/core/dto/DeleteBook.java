package com.GrabbingTheCode.bookmng.core.dto;


import javax.validation.constraints.NotBlank;

public record DeleteBook(
        @NotBlank(message = "Book Name cannot be left Blank")
        String bookName,
        @NotBlank(message = "Author Name cannot be left Blank")
        String authorName
) {
}
