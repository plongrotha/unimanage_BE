package org.plongrotha.unimanage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.req.DepartmentUpdateDto;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.model.Department;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "facultyId", source = "faculty.facultyId")
    @Mapping(target = "facultyName", source = "faculty.facultyName")
    DepartmentResponse toResponse(Department department);

    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "faculty.facultyId", source = "facultyId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Department toEntity(DepartmentRequest request);

    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    Department toEntity(DepartmentUpdateDto request);

    List<Department> toEntity(List<DepartmentRequest> requests);

    List<DepartmentResponse> toResponse(List<Department> departments);

}
