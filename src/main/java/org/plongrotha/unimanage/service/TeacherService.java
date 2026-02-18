package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.plongrotha.unimanage.dto.res.TeacherCourseReponse;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.enums.Gender;

public interface TeacherService {

    List<Gender> getAllGender();

    void createTeacher(TeacherRequest teacherRequest);

    void createBulkTeachers(List<TeacherRequest> teacherRequests);

    TeacherResponse getTeacherById(Long teacherId);

    void deleteTeacher(Long teacherId);

    TeacherResponse updateTeacher(Long teacherId, TeacherRequest teacherRequest);

    List<TeacherResponse> getAllTeachers();

    List<TeacherResponse> getAllTeacherByGender(Gender gender);

    TeacherCourseReponse getAllCourseTeacherTeach(Long teacherId);

    PageResponse<TeacherResponse> getAllTeacherPagination(int page, int size);
}
