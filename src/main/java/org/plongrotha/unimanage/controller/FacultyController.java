package org.plongrotha.unimanage.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.plongrotha.unimanage.dto.req.FacultyRequest;
import org.plongrotha.unimanage.dto.res.ApiResponse;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.dto.res.FacultyResponse;
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.plongrotha.unimanage.mapper.DepartmentMapper;
import org.plongrotha.unimanage.mapper.FacultyMapper;
import org.plongrotha.unimanage.model.Faculty;
import org.plongrotha.unimanage.service.FacultyService;
import org.plongrotha.unimanage.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final FacultyMapper facultyMapper;
    private final DepartmentMapper departmentMapper;

    @Operation(summary = "Create Faculty", description = "Create a new faculty")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createFaculty(@RequestBody FacultyRequest request) {
        var data = facultyMapper.toEntity(request);
        facultyService.createFaculty(data);
        return ResponseEntity.ok(ResponseUtil.created(null, "faculty created successfully"));
    }

    @Operation(summary = "Get All Faculties", description = "Retrieve a list of all faculties")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FacultyResponse>>> getAllFaculty() {
        var faculties = facultyService.getAllFaculty();
        return ResponseEntity.ok(ResponseUtil.ok(faculties,
                faculties.isEmpty() ? "faculty is empty" : "Faculties retrieved successfully"));
    }

    @Operation(summary = "Get Faculty by ID", description = "Retrieve a faculty by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FacultyResponse>> getFacultyById(@PathVariable @Positive Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        var response = facultyMapper.toResponse(faculty);
        return ResponseEntity.ok(ResponseUtil.ok(response, "Faculty retrieved successfully"));
    }

    @Deprecated
    @Operation(summary = "Get All Departments by Faculty ID", description = "Retrieve all departments associated with a specific faculty ID")
    @GetMapping("/{id}/departments")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartmentByFacultyId(
            @PathVariable @Positive Long id) {
        var departmentList = facultyService.getAllDepartmentWithFactoryId(id);
        var response = departmentMapper.toResponse(departmentList);
        return ResponseEntity.ok(ResponseUtil.ok(response,
                departmentList.isEmpty() ? "no have department in faculty" : "departments retrieved successfully"));
    }

    @Operation(summary = "Delete Faculty", description = "Delete a faculty by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFaculty(@PathVariable @Positive Long id) {
        facultyService.deleteFactory(id);
        return ResponseEntity.ok(ResponseUtil.ok(null, "faculty deleted successfully"));
    }

    @Operation(summary = "Delete All Faculties", description = "Delete all faculties")
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<Void>> deleteAllFaculty() {
        facultyService.deleteAllFactory();
        return ResponseEntity.ok(ResponseUtil.ok(null, "all faculties deleted successfully"));
    }

    @Operation(summary = "Get All Faculty Pagination")
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<PageResponse<FacultyResponse>>> getAllFacultyPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Positive int size) {
        PageResponse<FacultyResponse> faculties = facultyService.allFacultyPagination(page, size);
        return ResponseEntity.ok(ResponseUtil.ok(faculties, "all faculties retrieved successfully"));
    }

    @Operation(summary = "Update Faculty By Id")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateFacultyById(@PathVariable Long id,
            @RequestBody FacultyRequest request) {
        facultyService.updateFaculty(id, facultyMapper.toEntity(request));
        return ResponseEntity.ok(ResponseUtil.ok(null, "faculty updated successfully"));
    }

    @Operation(summary = "Create Bulk Faculties", description = "Create multiple faculties in bulk")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> createBulkFaculty(@RequestBody List<FacultyRequest> requests) {
        var faculties = facultyMapper.toEntity(requests);
        facultyService.createBulkFaculty(faculties);
        return ResponseEntity.ok(ResponseUtil.created(null, "bulk faculties created successfully"));
    }

    @Operation(summary = "Clear Faculty Cache", description = "Clear all cached faculty data")
    @PostMapping("/clear-cache")
    public ResponseEntity<ApiResponse<Void>> clearAllCache() {
        facultyService.clearAllCache();
        return ResponseEntity.ok(ResponseUtil.ok(null, "faculty cache cleared successfully"));
    }
}
