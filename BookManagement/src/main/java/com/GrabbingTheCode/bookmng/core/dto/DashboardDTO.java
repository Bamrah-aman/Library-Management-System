package com.GrabbingTheCode.bookmng.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private String totalBooks;
    private String libraryName;
    private List<DashBoardBooksList> listOfBooks;

}
