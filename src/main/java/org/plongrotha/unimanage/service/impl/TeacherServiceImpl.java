package org.plongrotha.unimanage.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.TeacherCourseReponse;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.enums.Gender;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.mapper.TeacherMapper;
import org.plongrotha.unimanage.model.Course;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.repository.TeacherCourseRepository;
import org.plongrotha.unimanage.repository.TeacherRepository;
import org.plongrotha.unimanage.service.TeacherService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public List<Gender> getAllGender() {
        return List.of(Gender.values());
    }

    @Caching(evict = { @CacheEvict(value = "teachers", allEntries = true),
            @CacheEvict(value = "teachersAll", allEntries = true) })
    @Override
    public void createTeacher(TeacherRequest teacherRequest) {
        var teacher = new Teacher();
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());

        if (teacherRepository.existsByEmail(teacherRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        teacher.setEmail(teacherRequest.getEmail());
        teacher.setPhone(teacherRequest.getPhone());
        teacher.setAddress(teacherRequest.getAddress());
        teacher.setGender(teacherRequest.getGender());
        teacher.setDob(teacherRequest.getDob());
        teacher.setAge(Period.between(teacherRequest.getDob(), LocalDate.now()).getYears());
        teacherRepository.save(teacher);
    }

    @Cacheable(value = "teachers", key = "#teacherId", unless = "#result == null")
    @Override
    public TeacherResponse getTeacherById(Long teacherId) {
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        return teacherMapper.toResponse(teacher);
    }

    @Caching(evict = { @CacheEvict(value = "teachers", key = "#teacherId"),
            @CacheEvict(value = "teachersAll", allEntries = true) })
    @Override
    public void deleteTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException("Teacher not found");
        }
        teacherRepository.deleteById(teacherId);
    }

    @Caching(evict = { @CacheEvict(value = "teachers", allEntries = true),
            @CacheEvict(value = "teachersAll", allEntries = true) })
    @Override
    public void createBulkTeachers(List<TeacherRequest> teacherRequests) {
        var teachers = teacherRequests.stream().map(teacher -> {
            var entity = new Teacher();
            entity.setFirstName(teacher.getFirstName());
            entity.setLastName(teacher.getLastName());

            if (teacherRepository.existsByEmail(teacher.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + teacher.getEmail());
            }
            entity.setEmail(teacher.getEmail());

            entity.setPhone(teacher.getPhone());
            entity.setAddress(teacher.getAddress());
            entity.setGender(teacher.getGender());
            entity.setDob(teacher.getDob());
            entity.setAge(Period.between(teacher.getDob(), LocalDate.now()).getYears());
            return entity;
        }).toList();
        teacherRepository.saveAll(teachers);
    }

    @Cacheable(value = "teachers", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<TeacherResponse> getAllTeachers() {
        var teachers = teacherRepository.findAll();
        return teachers.isEmpty() ? List.of() : teacherMapper.toResponseList(teachers);
    }

    @Override
    public List<TeacherResponse> getAllTeacherByGender(Gender gender) {
        var teacherList = teacherRepository.findAllByGender(gender);
        return teacherList.isEmpty() ? List.of() : teacherMapper.toResponseList(teacherList);
    }

    @Override
    public TeacherCourseReponse getAllCourseTeacherTeach(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        List<Course> courses = teacherCourseRepository.findAllByTeacher_TeacherId(teacher.getTeacherId());
        return teacherMapper.toTeacherCourseResponse(teacher, courses);
    }
}
