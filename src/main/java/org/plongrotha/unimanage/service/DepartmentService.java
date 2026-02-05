package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.model.Department;

public interface DepartmentService {

    void createDepartment(Department department);

    Department getDepartmentById(Long id);

    void updateDepartment(Department department, Long id);

    List<Department> getAllDepartments();

    void createBulkDepartments(List<Department> departments);

    void deleteDepartment(Long id);
}
