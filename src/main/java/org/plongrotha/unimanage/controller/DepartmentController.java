package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.req.DepartmentUpdateDto;
import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.mapper.DepartmentMapper;
import org.plongrotha.unimanage.service.DepartmentService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @Operation(summary = "Create a new department")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDepartment(@RequestBody DepartmentRequest request) {
        var department = departmentMapper.toEntity(request);
        departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.created(null, "Department created successfully"));
    }

    @Operation(summary = "Get all departments")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        var departments = departmentService.getAllDepartments();
        var response = departmentMapper.toResponse(departments);
        return ResponseEntity.ok(ResponseUtil.success(response,
                response.isEmpty() ? "No departments found" : "Departments retrieved successfully"));
    }

    @Operation(summary = "Update an existing department")
    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> updateDepartment(@RequestBody DepartmentUpdateDto request,
            @PathVariable @Positive Long departmentId) {
        departmentService.updateDepartment(request, departmentId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Department updated successfully"));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable @Positive Long departmentId) {
        var department = departmentService.getDepartmentById(departmentId);
        if (department == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.error("department not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity
                .ok(ResponseUtil.success(department, "Department retrieved successfully"));
    }

    @Operation(summary = "Delete a department")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable @Positive Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Department deleted successfully"));
    }

    @Deprecated
    @Operation(summary = "Create bulk departments")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> createBulkDepartments(@RequestBody List<DepartmentRequest> requests) {
        var departments = departmentMapper.toEntity(requests);
        departmentService.createBulkDepartments(departments);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.created(null, "Bulk departments created successfully"));
    }

    @Operation(summary = "Create bulk departments - Version 2")
    @PostMapping("/bulk/v2")
    public ResponseEntity<ApiResponse<Void>> createBulkDepartmentsVersion2(
            @RequestBody List<DepartmentRequest> requests) {
        // var departments = departmentMapper.toEntity(requests);
        departmentService.createBulkDepartmentsVersion2(requests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.created(null, "Bulk departments created successfully"));
    }

    @Operation(summary = "Get all departments by faculty id")
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartmentByFacultyId(
            @PathVariable @Positive Long facultyId) {
        var departments = departmentService.getAllDepartmentByFacultyId(facultyId);
        return ResponseEntity.ok(ResponseUtil.success(departments,
                departments.isEmpty() ? "No departments found for faculty id: " + facultyId
                        : "Departments retrieved successfully for faculty id: " + facultyId));
    }

    @Operation(summary = "Clear all cache")
    @PostMapping("/clear-cache")
    public ResponseEntity<ApiResponse<Void>> clearAllCache() {
        departmentService.clearAllCache();
        return ResponseEntity.ok(ResponseUtil.ok(null, "All cache cleared successfully"));
    }
}
