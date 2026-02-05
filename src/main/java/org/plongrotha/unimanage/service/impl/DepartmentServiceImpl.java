package org.plongrotha.unimanage.service.impl;

import java.util.List;

import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.model.Department;
import org.plongrotha.unimanage.repository.DepartmentRepository;
import org.plongrotha.unimanage.repository.FacultyRepository;
import org.plongrotha.unimanage.service.DepartmentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    @CacheEvict(value = "departmentsAll", allEntries = true)
    @Override
    public void createDepartment(Department department) {
        var saveDepartment = new Department();
        var faculty = facultyRepository.findById(department.getFaculty().getFacultyId())
                .orElseThrow(() -> new NotFoundException(
                        "Faculty not found with id: " + department.getFaculty().getFacultyId()));
        saveDepartment.setFaculty(faculty);
        saveDepartment.setDepartmentId(null);
        saveDepartment.setDepartmentName(department.getDepartmentName());
        saveDepartment.setDescription(department.getDescription());

        // save new department
        departmentRepository.save(saveDepartment);
    }

    @Cacheable(value = "departments", key = "#id", unless = "#result == null")
    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Caching(evict = { @CacheEvict(value = "departments", key = "#id"),
            @CacheEvict(value = "departmentsAll", allEntries = true) })
    @Override
    public void updateDepartment(Department department, Long id) {
        var existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id));
        existingDepartment.setDepartmentName(department.getDepartmentName());
        existingDepartment.setDescription(department.getDescription());
        existingDepartment.setFaculty(department.getFaculty());
        departmentRepository.save(existingDepartment);
    }

    @Caching(evict = { @CacheEvict(value = "departments", key = "#id"),
            @CacheEvict(value = "departmentsAll", allEntries = true) })
    @Override
    public void deleteDepartment(Long id) {
        var existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(" department not found with id: " + id));
        departmentRepository.delete(existingDepartment);
    }

    @Cacheable(value = "departmentsAll", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<Department> getAllDepartments() {
        var departments = departmentRepository.findAll();
        return departments.isEmpty() ? List.of() : departments;
    }

    @Transactional
    @CacheEvict(value = "departmentsAll", allEntries = true)
    @Override
    public void createBulkDepartments(List<Department> departments) {
        var saveDepartments = departments.stream().map(department -> {
            var saveDepartment = new Department();
            var faculty = facultyRepository.findById(department.getFaculty().getFacultyId())
                    .orElseThrow(() -> new NotFoundException(
                            "Faculty not found with id: " + department.getFaculty().getFacultyId()));
            saveDepartment.setFaculty(faculty);
            saveDepartment.setDepartmentId(null);
            saveDepartment.setDepartmentName(department.getDepartmentName());
            saveDepartment.setDescription(department.getDescription());
            return saveDepartment;
        }).toList();
        departmentRepository.saveAll(saveDepartments);
    }
}
