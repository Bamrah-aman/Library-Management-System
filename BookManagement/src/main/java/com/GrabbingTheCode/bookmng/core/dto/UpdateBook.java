package com.GrabbingTheCode.bookmng.core.dto;

import javax.validation.constraints.NotBlank;

public record UpdateBook(
        @NotBlank(message = "Old Book Name cannot be left Blank")
        String oldBook,
        @NotBlank(message = "New Book Name cannot be left Blank")
        String newBook,
        @NotBlank(message = "Author cannot be left Blank")
        String authorName
) {
}
