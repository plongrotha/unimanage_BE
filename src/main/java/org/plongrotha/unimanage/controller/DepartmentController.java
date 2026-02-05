package org.plongrotha.unimanage.controller;

import java.util.List;

import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.mapper.DepartmentMapper;
import org.plongrotha.unimanage.service.DepartmentService;
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
        return ResponseEntity.ok(ResponseUtil.success(null, "Department created successfully"));
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
    @PostMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> updateDepartment(@RequestBody DepartmentRequest request,
            @PathVariable @Positive Long departmentId) {
        var department = departmentMapper.toEntity(request);
        departmentService.updateDepartment(department, departmentId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Department updated successfully"));
    }

    @Operation(summary = "Get department by ID")
    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(
            @PathVariable @Positive Long departmentId) {
        var department = departmentService.getDepartmentById(departmentId);
        var response = departmentMapper.toResponse(department);
        if (response == null) {
            return ResponseEntity.ok(
                    ResponseUtil.error("department not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(ResponseUtil.success(response, response != null ? "Department retrieved successfully"
                : "Department not found with id: " + departmentId));
    }

    @Operation(summary = "Delete a department")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable @Positive Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(ResponseUtil.success(null, "Department deleted successfully"));
    }

    @Operation(summary = "Create bulk departments")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> createBulkDepartments(@RequestBody List<DepartmentRequest> requests) {
        var departments = departmentMapper.toEntity(requests);
        departmentService.createBulkDepartments(departments);
        return ResponseEntity.ok(ResponseUtil.success(null, "Bulk departments created successfully"));
    }
}
