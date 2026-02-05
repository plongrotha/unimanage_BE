package org.plongrotha.unimanage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.model.Department;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "facultyId", source = "faculty.facultyId")
    DepartmentResponse toResponse(Department department);

    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "faculty.facultyId", source = "facultyId")
    Department toEntity(DepartmentRequest request);

    List<Department> toEntity(List<DepartmentRequest> requests);

    List<DepartmentResponse> toResponse(List<Department> departments);

}
