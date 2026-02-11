package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.service.TeacherDepartmentService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher-departments")
public class TeacherDepartmentController {

    private final TeacherDepartmentService teacherDepartmentService;

    @Operation(summary = "Assign a teacher to a department")
    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<Void>> assignTeacherToDepartment(@RequestParam Long teacherId,
            @RequestParam Long departmentId) {
        teacherDepartmentService.assignTeacherToDepartment(teacherId, departmentId);
        return ResponseEntity.ok(ResponseUtil.created());
    }

    @Operation(summary = "Get teachers by department ID")
    @GetMapping("/teachers/{departmentId}")
    public ResponseEntity<ApiResponse<List<Teacher>>> getTeachersByDepartmentId(@PathVariable Long departmentId) {
        var teachers = teacherDepartmentService.getTeachersByDepartmentId(departmentId);
        return ResponseEntity.ok(ResponseUtil.ok(teachers, "Teachers retrieved successfully "));
    }
}
