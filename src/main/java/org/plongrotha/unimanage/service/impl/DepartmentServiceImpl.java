package org.plongrotha.unimanage.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.plongrotha.unimanage.dto.req.DepartmentRequest;
import org.plongrotha.unimanage.dto.req.DepartmentUpdateDto;
import org.plongrotha.unimanage.dto.res.DepartmentResponse;
import org.plongrotha.unimanage.exception.BadRequestException;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.mapper.DepartmentMapper;
import org.plongrotha.unimanage.model.Department;
import org.plongrotha.unimanage.model.Faculty;
import org.plongrotha.unimanage.repository.DepartmentRepository;
import org.plongrotha.unimanage.repository.FacultyRepository;
import org.plongrotha.unimanage.service.DepartmentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper departmentMapper;

    // @CacheEvict(value = "departmentsAll", allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "departmentsAll", allEntries = true),
            @CacheEvict(value = "departmentsByFaculty", allEntries = true) })
    @Override
    public void createDepartment(Department department) {
        var saveDepartment = new Department();
        var faculty = facultyRepository.findById(department.getFaculty().getFacultyId())
                .orElseThrow(() -> new NotFoundException(
                        "Faculty not found with id: " + department.getFaculty().getFacultyId()));
        saveDepartment.setFaculty(faculty);
        saveDepartment.setDepartmentId(null);
        // validate department name not exists
        validateDepartmentNameNotExists(department.getDepartmentName());
        saveDepartment.setDepartmentName(department.getDepartmentName());
        saveDepartment.setDescription(department.getDescription());

        // save new department
        departmentRepository.save(saveDepartment);
    }

    @Cacheable(value = "departments", key = "#id", unless = "#result == null")
    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        var department = departmentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Department not found with id: " + id));
        return departmentMapper.toResponse(department);
    }

    @Caching(evict = { @CacheEvict(value = "departments", key = "#id"),
            @CacheEvict(value = "departmentsAll", allEntries = true),
            @CacheEvict(value = "departmentsByFaculty", allEntries = true) })
    @Override
    public void updateDepartment(DepartmentUpdateDto department, Long id) {
        var existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id));
        // validateDepartmentNameNotExists(department.getDepartmentName());
        existingDepartment.setDepartmentName(department.getDepartmentName());
        existingDepartment.setDescription(department.getDescription());
        departmentRepository.save(existingDepartment);
    }

    @Caching(evict = { @CacheEvict(value = "departments", key = "#id"),
            @CacheEvict(value = "departmentsAll", allEntries = true),
            @CacheEvict(value = "departmentsByFaculty", allEntries = true) })
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
            // validate department name not exists
            validateDepartmentNameNotExists(department.getDepartmentName());
            saveDepartment.setDepartmentName(department.getDepartmentName());
            saveDepartment.setDescription(department.getDescription());
            return saveDepartment;
        }).toList();
        departmentRepository.saveAll(saveDepartments);
    }

    @Transactional
    @Caching(evict = { @CacheEvict(value = "departmentsAll", allEntries = true),
            @CacheEvict(value = "departmentsByFaculty", allEntries = true) })
    @Override
    public void createBulkDepartmentsVersion2(List<DepartmentRequest> departmentRequests) {
        var departmentList = departmentMapper.toEntity(departmentRequests);

        Set<Long> facultyId = departmentList.stream().map(department -> department.getFaculty().getFacultyId())
                .collect(Collectors.toSet());

        Map<Long, Faculty> facultyMap = facultyRepository.findAllById(facultyId).stream()
                .collect(Collectors.toMap(Faculty::getFacultyId, faculty -> faculty));

        Set<Long> missingIds = facultyId.stream().filter(id -> !facultyMap.containsKey(id)).collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Faculties not found with ids: " + missingIds);
        }

        var listDeparmentSaved = departmentList.stream().map(department -> {
            var saveDepartment = new Department();
            saveDepartment.setFaculty(facultyMap.get(department.getFaculty().getFacultyId()));
            saveDepartment.setDepartmentId(null);
            // validate department name not exists
            validateDepartmentNameNotExists(department.getDepartmentName());
            saveDepartment.setDepartmentName(department.getDepartmentName());
            saveDepartment.setDescription(department.getDescription());
            return saveDepartment;
        }).toList();
        departmentRepository.saveAll(listDeparmentSaved);
    }

    @Cacheable(value = "departmentsByFaculty", key = "#facultyId", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<DepartmentResponse> getAllDepartmentByFacultyId(Long facultyId) {
        var departments = departmentRepository.findAllByFaculty_FacultyId(facultyId);
        return departments.isEmpty() ? List.of()
                : departments.stream().map(departmentMapper::toResponse).collect(Collectors.toList());
    }

    @CacheEvict(value = { "departments", "departmentsAll", "departmentsByFaculty" }, allEntries = true)
    @Override
    public void clearAllCache() {
        log.info("clear cache is called");
    }

    private void validateDepartmentNameNotExists(String departmentName) {
        if (departmentRepository.existsByDepartmentName(departmentName)) {
            throw new BadRequestException(
                    "Department already exists with name: " + departmentName);
        }
    }
}
