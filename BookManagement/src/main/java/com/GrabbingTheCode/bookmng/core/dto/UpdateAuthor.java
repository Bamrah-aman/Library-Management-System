package com.GrabbingTheCode.bookmng.core.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthor(

        @NotBlank(message = "Old Author cannot be left Blank")
        String oldAuthorName,
        @NotBlank(message = "New Author cannot be left Blank")
        String newAuthorName,
        @NotBlank(message = "Book Name cannot be left Blank")
        String bookName
) {
}
