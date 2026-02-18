package org.plongrotha.unimanage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.plongrotha.unimanage.dto.req.CourseRequest;
import org.plongrotha.unimanage.dto.res.CourseResponse;
import org.plongrotha.unimanage.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "credits", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    Course toCourse(CourseRequest courseRequest);

    List<Course> toCourse(List<CourseRequest> courseRequests);

    CourseResponse toCourseResponse(Course course);

    List<CourseResponse> toCourseResponseList(List<Course> courses);

}
