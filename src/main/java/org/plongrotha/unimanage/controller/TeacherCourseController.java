package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.TeacherCourseResponse;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.service.TeacherCourseService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher-course")
public class TeacherCourseController {

    private final TeacherCourseService teacherCourseService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<Void>> assignCourseToTeacher(@RequestParam Long teacherId,
            @RequestParam Long courseId) {
        teacherCourseService.assignCourseToTeacher(teacherId, courseId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Course assigned to teacher successfully"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Void>> removeCourseFromTeacher(@RequestParam Long teacherId,
            @RequestParam Long courseId) {
        teacherCourseService.removeCourseFromTeacher(teacherId, courseId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Course removed from teacher successfully"));
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<List<Teacher>>> getTeachersByCourseId(@RequestParam Long courseId) {
        var teachers = teacherCourseService.getTeachersByCourseId(courseId);
        return ResponseEntity.ok(ResponseUtil.success(teachers, "Teachers retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherCourseResponse>>> getAllTeacherCoruse() {
        var teacherCourses = teacherCourseService.getAllTeacherCoruse();
        return ResponseEntity.ok(ResponseUtil.success(teacherCourses, "Teacher courses retrieved successfully"));
    }
}
