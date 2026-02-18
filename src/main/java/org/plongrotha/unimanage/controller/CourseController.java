package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.req.CourseRequest;
import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.CourseResponse;
import org.plongrotha.unimanage.service.CourseService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Get all courses")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        var courses = courseService.getAllCourses();
        return ResponseUtil.ok(courses, "Courses retrieved successfully");
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable @Positive Long courseId) {
        var course = courseService.getCourseById(courseId);
        return ResponseUtil.ok(course);
    }

    @Operation(summary = "Create new course")
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@RequestBody CourseRequest request) {
        courseService.createCourse(request);
        return ResponseUtil.created(null, "Course created successfully");
    }

    @Operation(summary = "Update course by id")
    @PutMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable @Positive Long courseId,
            @RequestBody CourseRequest request) {
        courseService.updateCourse(courseId, request);
        return ResponseUtil.ok();
    }

    @Operation(summary = "Create multiple courses")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> createBulk(
            @RequestBody List<@Valid CourseRequest> requests) {
        courseService.createBulk(requests);
        return ResponseUtil.created(null, "Bulk courses created successfully");
    }

    @Operation(summary = "Delete course by id")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable @Positive Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseUtil.ok();
    }
}
