package org.plongrotha.unimanage.repository;

import org.plongrotha.unimanage.model.Department;
import org.plongrotha.unimanage.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("""
                SELECT d
                FROM Department d
                WHERE d.faculty.facultyId = :facultyId
            """)
    List<Department> findAllDepartmentsByFacultyId(@Param("facultyId") Long facultyId);

    @Query("""
            SELECT f FROM Faculty f
            """)
    Page<Faculty> findAllFacultyPage(Pageable pageable);
}
