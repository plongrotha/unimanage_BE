package org.plongrotha.unimanage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.req.FacultyRequest;
import org.plongrotha.unimanage.dto.res.FacultyResponse;
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.plongrotha.unimanage.model.Faculty;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    @Mapping(target = "facultyId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "departments", ignore = true)
    Faculty toEntity(FacultyRequest request);

    FacultyResponse toResponse(Faculty faculty);

    List<FacultyResponse> toResponseList(List<Faculty> faculties);

    default PageResponse<FacultyResponse> toPageResponse(Page<Faculty> hotels) {
        return PageResponse.<FacultyResponse>builder()
                .content(toResponseList(hotels.getContent()))
                .pageNumber(hotels.getNumber())
                .pageSize(hotels.getSize())
                .totalElements(hotels.getTotalElements())
                .totalPages(hotels.getTotalPages())
                .last(hotels.isLast())
                .first(hotels.isFirst())
                .empty(hotels.isEmpty())
                .build();
    }
}
