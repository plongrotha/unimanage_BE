package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.model.Teacher;

public interface TeacherDepartmentService {
    void assignTeacherToDepartment(Long teacherId, Long departmentId);

    List<Teacher> getTeachersByDepartmentId(Long departmentId);

}
