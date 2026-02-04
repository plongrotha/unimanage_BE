package org.plongrotha.unimanage.service;

import org.plongrotha.unimanage.dto.res.FacultyResponse;
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.plongrotha.unimanage.model.Department;
import org.plongrotha.unimanage.model.Faculty;

import java.util.List;

public interface FacultyService {

    void createFaculty(Faculty faculty);

    void updateFaculty(Long id, Faculty faculty);

    Faculty getFacultyById(Long id);

    void deleteFactory(Long id);

    void deleteAllFactory();

    List<FacultyResponse> getAllFaculty();

    List<Department> getAllDepartmentWithFactoryId(Long facultyId);

    PageResponse<FacultyResponse> allFacultyPagination(int page, int size);

    void clearAllCache();
}
