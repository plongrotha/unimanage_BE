package org.plongrotha.unimanage.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.plongrotha.unimanage.dto.res.CourseResponse;
import org.plongrotha.unimanage.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    List<CourseResponse> toCourseResponseList(List<Course> courses);

}
