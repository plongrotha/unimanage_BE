package org.plongrotha.unimanage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.TeacherCourseReponse;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.model.Course;
import org.plongrotha.unimanage.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherMapper MAPPER = Mappers.getMapper(TeacherMapper.class);

    @Mapping(target = "teacherId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    List<Teacher> toEntity(List<TeacherRequest> teacherRequests);

    List<TeacherResponse> toResponseList(List<Teacher> teachers);

    TeacherResponse toResponse(Teacher teacher);

    TeacherCourseReponse toTeacherCourseResponse(Teacher teacher, List<Course> courses);

}
