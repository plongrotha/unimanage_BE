package org.plongrotha.unimanage.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.plongrotha.unimanage.dto.res.FacultyResponse;
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.mapper.FacultyMapper;
import org.plongrotha.unimanage.model.Department;
import org.plongrotha.unimanage.model.Faculty;
import org.plongrotha.unimanage.repository.FacultyRepository;
import org.plongrotha.unimanage.service.FacultyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Override
    public void createFaculty(Faculty faculty) {
        var createFaculty = new Faculty();
        createFaculty.setFacultyCode(faculty.getFacultyCode());
        createFaculty.setFacultyName(faculty.getFacultyName());
        createFaculty.setStatus("ACTIVE");
        facultyRepository.save(createFaculty);
    }

    @Transactional
    @CachePut(value = "faculties", key = "#id")
    @Override
    public void updateFaculty(Long id, Faculty faculty) {
        var createFaculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("factory is not found"));
        createFaculty.setFacultyCode(faculty.getFacultyCode());
        createFaculty.setFacultyName(faculty.getFacultyName());
        facultyRepository.save(createFaculty);
    }

    @Cacheable(value = "faculties", key = "#id")
    @Override
    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundException("factory is not found"));
    }

    @CacheEvict(value = "faculties", key = "#id")
    @Override
    public void deleteFactory(Long id) {
        var createFaculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("factory is not found"));
        facultyRepository.delete(createFaculty);
    }

    @Cacheable(value = "all_faculty", unless = "#result.empty")
    @Override
    public List<FacultyResponse> getAllFaculty() {
        var facultyList = facultyRepository.findAll();
        return facultyList.isEmpty() ? List.of() : facultyMapper.toResponseList(facultyList);
    }

    @Cacheable(value = "departments", key = "#facultyId", unless = "#result.empty")
    @Override
    public List<Department> getAllDepartmentWithFactoryId(Long facultyId) {
        var faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new NotFoundException("faculty is not found"));
        var departmentList = facultyRepository.findAllDepartmentsByFacultyId(faculty.getFacultyId());
        return departmentList.isEmpty() ? List.of() : departmentList;
    }

    @CacheEvict(value = { "faculties", "departments", "all_faculty" }, allEntries = true)
    @Override
    public void clearAllCache() {
        log.info("clear cache is called");
    }

    @Cacheable(value = "faculty_page", key = "'page_' + #page + '_size_' + #size", unless = "#result.empty")
    @Override
    public PageResponse<FacultyResponse> allFacultyPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Faculty> faculties = facultyRepository.findAllFacultyPage(pageable);
        return facultyMapper.toPageResponse(faculties);
    }
    @CacheEvict(value = "faculties", allEntries = true)
    @Override
    public void deleteAllFactory() {
        var facultyList = facultyRepository.findAll();
        if (facultyList.isEmpty()) {
            throw new NotFoundException("no faculty to delete");
        }
        facultyRepository.deleteAll(facultyList);
    }
}
