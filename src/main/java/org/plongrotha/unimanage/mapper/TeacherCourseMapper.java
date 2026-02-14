package org.plongrotha.unimanage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.res.TeacherCourseResponse;
import org.plongrotha.unimanage.model.TeacherCourse;

@Mapper(componentModel = "spring")
public interface TeacherCourseMapper {

    @Mapping(target = "teacher", expression = "java(mapTeacherNames(List.of(teacherCourse)))")
    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "courseName", source = "course.courseName")
    @Mapping(target = "courseCode", source = "course.courseCode")
    TeacherCourseResponse toTeacherCourseResponse(TeacherCourse teacherCourse);

    List<TeacherCourseResponse> toTeacherCourseResponseList(List<TeacherCourse> teachers);

    // Helper to extract the name from the TeacherCourse bridge entity
    default String mapTeacherName(TeacherCourse tc) {
        if (tc == null || tc.getTeacher() == null)
            return null;
        return tc.getTeacher().getFirstName() + " " + tc.getTeacher().getLastName();
    }

    // Maps the list of bridge entities to a list of strings automatically
    List<String> mapTeacherNames(List<TeacherCourse> teacherCourses);
}
