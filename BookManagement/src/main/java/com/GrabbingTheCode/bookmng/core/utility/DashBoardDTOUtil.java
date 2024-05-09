package com.GrabbingTheCode.bookmng.core.utility;

import com.GrabbingTheCode.bookmng.core.dto.DashboardDTO;
import com.GrabbingTheCode.bookmng.core.entity.Library;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class DashBoardDTOUtil {

    public static DashboardDTO mapToDashBoard(Library library) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(library, DashboardDTO.class);
    }
}
