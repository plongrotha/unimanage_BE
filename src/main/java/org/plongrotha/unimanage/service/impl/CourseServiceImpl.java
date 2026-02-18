package org.plongrotha.unimanage.service.impl;

import java.util.List;

import org.plongrotha.unimanage.dto.req.CourseRequest;
import org.plongrotha.unimanage.dto.res.CourseResponse;
import org.plongrotha.unimanage.exception.ConflictException;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.mapper.CourseMapper;
import org.plongrotha.unimanage.model.Course;
import org.plongrotha.unimanage.repository.CourseRepository;
import org.plongrotha.unimanage.service.CourseService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public void createCourse(CourseRequest request) {
        courseRepository.save(create(request));
    }

    @Override
    public void updateCourse(Long courseId, CourseRequest request) {
        var course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDescription(request.getDescription());
        course.setFee(request.getFee());
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        var course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
        courseRepository.delete(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        var courses = courseRepository.findAll();
        return courses.isEmpty() ? List.of() : courseMapper.toCourseResponseList(courses);
    }

    @Override
    public CourseResponse getCourseById(Long courseId) {
        var course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public List<CourseResponse> createBulk(List<CourseRequest> requests) {
        var courses = requests.stream().map(this::create).toList();
        courseRepository.saveAll(courses);
        return courseMapper.toCourseResponseList(courses);
    }

    private Course create(CourseRequest request) {
        var course = new Course();

        if (courseRepository.existsByCourseName(request.getCourseName())) {
            throw new ConflictException("Course name already exists");
        }

        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDescription(request.getDescription());
        course.setFee(request.getFee());
        return course;
    }
}