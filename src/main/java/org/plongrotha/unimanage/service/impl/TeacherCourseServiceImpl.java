package org.plongrotha.unimanage.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.plongrotha.unimanage.dto.res.TeacherCourseResponse;
import org.plongrotha.unimanage.exception.ConflictException;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.model.TeacherCourse;
import org.plongrotha.unimanage.model.TeacherCourseId;
import org.plongrotha.unimanage.repository.CourseRepository;
import org.plongrotha.unimanage.repository.TeacherCourseRepository;
import org.plongrotha.unimanage.repository.TeacherRepository;
import org.plongrotha.unimanage.service.TeacherCourseService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherCourseServiceImpl implements TeacherCourseService {

        private final TeacherCourseRepository teacherCourseRepository;
        private final TeacherRepository teacherRepository;
        private final CourseRepository courseRepository;

        @Override
        public void assignCourseToTeacher(Long teacherId, Long courseId) {
                var teacher = teacherRepository.findById(teacherId)
                                .orElseThrow(() -> new NotFoundException("Teacher not found"));
                var course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new NotFoundException("Course not found"));
                if (teacherCourseRepository.existsByTeacher_TeacherIdAndCourse_CourseId(teacherId, courseId)) {
                        throw new ConflictException("Course is already assigned to the teacher");
                }
                var teacherCourse = new TeacherCourse();
                teacherCourse.setId(new TeacherCourseId(teacherId, courseId));
                teacherCourse.setTeacher(teacher);
                teacherCourse.setCourse(course);
                teacherCourse.getId().setAssignedAt(LocalDateTime.now());
                teacherCourse.getId().setChangeAssignAt(LocalDateTime.now());
                teacherCourseRepository.save(teacherCourse);
        }

        @Override
        public List<Teacher> getTeachersByCourseId(Long courseId) {
                var course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new NotFoundException("Course not found"));
                var teachers = teacherCourseRepository.findAllByCourse_CourseId(course.getCourseId());
                return teachers.isEmpty() ? List.of() : teachers;
        }

        @Override
        public void removeCourseFromTeacher(Long teacherId, Long courseId) {
                TeacherCourseId id = new TeacherCourseId(teacherId, courseId);
                teacherCourseRepository.deleteTeacherCourse(id.getTeacherId(), id.getCourseId());
        }

        @Override
        public List<TeacherCourseResponse> getAllTeacherCoruse() {
                var teacherCourses = teacherCourseRepository.findAll();
                return teacherCourses
                                .stream()
                                .collect(Collectors.groupingBy(tc -> tc.getCourse().getCourseId(),
                                                Collectors.collectingAndThen(Collectors.toList(), list -> {
                                                        var course = list.get(0).getCourse();
                                                        var teacherNames = list.stream().map(tc -> tc.getTeacher()
                                                                        .getFirstName()
                                                                        + " "
                                                                        + tc.getTeacher().getLastName())
                                                                        .collect(Collectors.toList());
                                                        TeacherCourseResponse response = new TeacherCourseResponse();
                                                        response.setCourseId(course
                                                                        .getCourseId());
                                                        response.setCourseName(course
                                                                        .getCourseName());
                                                        response.setCourseCode(course
                                                                        .getCourseCode());
                                                        response.setTeacher(
                                                                        teacherNames);
                                                        return response;
                                                })))
                                .values().stream().collect(Collectors.toList());
        }
}
