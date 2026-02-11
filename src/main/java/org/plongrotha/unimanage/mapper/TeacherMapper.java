package org.plongrotha.unimanage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "department", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    List<Teacher> toEntity(List<TeacherRequest> teacherRequests);

    List<TeacherResponse> toResponseList(List<Teacher> teachers);

    TeacherResponse toResponse(Teacher teacher);
}
