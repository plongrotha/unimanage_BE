package org.plongrotha.unimanage.repository;

import java.util.List;

import org.plongrotha.unimanage.model.TeacherDepartment;
import org.plongrotha.unimanage.model.TeacherDepartmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDepartmentRepository extends JpaRepository<TeacherDepartment, TeacherDepartmentId> {

    @Query("SELECT td FROM TeacherDepartment td WHERE td.department.id = :departmentId")
    List<TeacherDepartment> findAllByDepartmentId(Long departmentId);

    @Query("SELECT td FROM TeacherDepartment td WHERE td.teacher.id = :teacherId")
    List<TeacherDepartment> findAllByTeacherId(Long teacherId);

    @Query("SELECT CASE WHEN COUNT(td) > 0 THEN true ELSE false END FROM TeacherDepartment td WHERE td.teacher.id = :teacherId AND td.department.id = :departmentId")
    boolean existsByTeacherIdAndDepartmentId(Long teacherId, Long departmentId);
}
