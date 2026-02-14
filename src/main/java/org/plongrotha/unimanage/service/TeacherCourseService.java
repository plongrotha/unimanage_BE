package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.res.TeacherCourseResponse;
import org.plongrotha.unimanage.model.Teacher;

public interface TeacherCourseService {
    void assignCourseToTeacher(Long teacherId, Long courseId);

    void removeCourseFromTeacher(Long teacherId, Long courseId);

    List<TeacherCourseResponse> getAllTeacherCoruse();

    List<Teacher> getTeachersByCourseId(Long courseId);
}
