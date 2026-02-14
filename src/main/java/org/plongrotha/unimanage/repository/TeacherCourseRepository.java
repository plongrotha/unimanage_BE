package org.plongrotha.unimanage.repository;

import java.util.List;

import org.plongrotha.unimanage.model.Course;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.model.TeacherCourse;
import org.plongrotha.unimanage.model.TeacherCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, TeacherCourseId> {

    @Query("SELECT tc.course FROM TeacherCourse tc WHERE tc.teacher.teacherId = :teacherId")
    List<Course> findAllByTeacher_TeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT tc.teacher FROM TeacherCourse tc WHERE tc.course.courseId = :courseId")
    List<Teacher> findAllByCourse_CourseId(@Param("courseId") Long courseId);

    boolean existsByTeacher_TeacherIdAndCourse_CourseId(Long teacherId, Long courseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TeacherCourse tc WHERE tc.id.teacherId = :tId AND tc.id.courseId = :cId")
    void deleteTeacherCourse(@Param("tId") Long teacherId, @Param("cId") Long courseId);
}
