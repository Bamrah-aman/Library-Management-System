package com.GrabbingTheCode.bookmng.core.utility;

import com.GrabbingTheCode.bookmng.core.dto.BookDTO;
import com.GrabbingTheCode.bookmng.core.entity.Book;
import org.modelmapper.ModelMapper;

public class BookDTOUtil {

    public static BookDTO mapToBookDto(Book book) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(book, BookDTO.class);
    }
}
