package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.req.CourseRequest;
import org.plongrotha.unimanage.dto.res.CourseResponse;

public interface CourseService {

    void createCourse(CourseRequest request);

    void updateCourse(Long courseId, CourseRequest request);

    void deleteCourse(Long courseId);

    CourseResponse getCourseById(Long courseId);

    List<CourseResponse> getAllCourses();

    List<CourseResponse> createBulk(List<CourseRequest> requests);
}
