package org.plongrotha.unimanage.repository;

import java.util.List;
import java.util.Optional;

import org.plongrotha.unimanage.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentName(String departmentName);

    boolean existsByDepartmentName(String departmentName);

    List<Department> findAllByFaculty_FacultyId(Long facultyId);

}
