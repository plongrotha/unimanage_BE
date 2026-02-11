package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.req.DepartmentUpdateDto;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.model.Department;

public interface DepartmentService {

    void createDepartment(Department department);

    DepartmentResponse getDepartmentById(Long id);

    void updateDepartment(DepartmentUpdateDto department, Long id);

    List<Department> getAllDepartments();

    List<DepartmentResponse> getAllDepartmentByFacultyId(Long facultyId);

    void createBulkDepartments(List<Department> departments);

    void createBulkDepartmentsVersion2(List<DepartmentRequest> departments);

    void deleteDepartment(Long id);

    void clearAllCache();
}
