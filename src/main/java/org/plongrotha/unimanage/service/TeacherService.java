package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.TeacherResponse;

public interface TeacherService {
    void createTeacher(TeacherRequest teacherRequest);

    void createBulkTeachers(List<TeacherRequest> teacherRequests);

    TeacherResponse getTeacherById(Long teacherId);

    void deleteTeacher(Long teacherId);

    List<TeacherResponse> getAllTeachers();
}
