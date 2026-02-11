package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.service.TeacherService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "Create a new teacher")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createTeacher(@RequestBody @Valid TeacherRequest teacherRequest) {
        teacherService.createTeacher(teacherRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.created(null, "Teacher created successfully"));
    }

    @Operation(summary = "Get teacher by ID")
    @GetMapping("/{teacherId}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getTeacherById(@PathVariable @Positive Long teacherId) {
        var teacher = teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(ResponseUtil.success(teacher, "Teacher retrieved successfully"));
    }

    @Operation(summary = "Delete a teacher by ID")
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<ApiResponse<Void>> deleteTeacher(@PathVariable @Positive Long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Teacher deleted successfully"));
    }

    @Operation(summary = "Create multiple teachers in bulk")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> createBulkTeachers(
            @RequestBody List<@Valid TeacherRequest> teacherRequests) {
        teacherService.createBulkTeachers(teacherRequests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.created(null, "Bulk teachers created successfully"));
    }

    @Operation(summary = "Get all teachers")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getAllTeachers() {
        var teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(ResponseUtil.success(teachers,
                teachers.isEmpty() ? "No teachers found" : "Teachers retrieved successfully"));
    }
}