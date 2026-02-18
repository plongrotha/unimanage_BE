package org.plongrotha.unimanage.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.plongrotha.unimanage.dto.req.TeacherRequest;
import org.plongrotha.unimanage.dto.res.TeacherCourseReponse;
import org.plongrotha.unimanage.dto.res.TeacherResponse;
import org.plongrotha.unimanage.enums.Gender;
import org.plongrotha.unimanage.exception.BadRequestException;
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
import org.plongrotha.unimanage.dto.res.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        var teacher = create(teacherRequest);
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
        var teachers = teacherRequests.stream().map(this::create).toList();
        teacherRepository.saveAll(teachers);
    }

    @Cacheable(value = "teachers", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<TeacherResponse> getAllTeachers() {
        var teachers = teacherRepository.findAll();
        return teachers.isEmpty() ? List.of() : teacherMapper.toResponseList(teachers);
    }

    @Cacheable(value = "genders", key = "#gender", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<TeacherResponse> getAllTeacherByGender(Gender gender) {
        var teacherList = teacherRepository.findAllByGender(gender);
        return teacherMapper.toResponseList(teacherList);
    }

    @Caching(evict = { @CacheEvict(value = "teachers", key = "#teacherId"),
            @CacheEvict(value = "teachersAll", allEntries = true) })
    @Override
    public TeacherCourseReponse getAllCourseTeacherTeach(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        List<Course> courses = teacherCourseRepository.findAllByTeacher_TeacherId(teacher.getTeacherId());
        return teacherMapper.toTeacherCourseResponse(teacher, courses.isEmpty() ? List.of() : courses);
    }

    private Teacher create(TeacherRequest teacherRequest) {
        var teacher = new Teacher();
        teacher.setFirstName(teacherRequest.getFirstName().trim().toLowerCase());
        teacher.setLastName(teacherRequest.getLastName().trim().toLowerCase());

        if (teacherRepository.existsByEmail(teacherRequest.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        teacher.setEmail(teacherRequest.getEmail());
        teacher.setPhone(teacherRequest.getPhone());
        teacher.setAddress(teacherRequest.getAddress());

        if (!Arrays.asList(Gender.values()).contains(teacherRequest.getGender())) {
            throw new IllegalArgumentException("Invalid gender value");
        }

        teacher.setGender(teacherRequest.getGender());
        teacher.setDob(teacherRequest.getDob());
        teacher.setAge(Period.between(teacherRequest.getDob(), LocalDate.now()).getYears());

        return teacher;
    }

    @Override
    public PageResponse<TeacherResponse> getAllTeacherPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teacherPagination = teacherRepository.findAllTeacher(pageable);
        return teacherMapper.toPageResponse(teacherPagination);
    }

    @Override
    public TeacherResponse updateTeacher(Long teacherId, TeacherRequest teacherRequest) {
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));

        teacher.setFirstName(teacherRequest.getFirstName().toLowerCase());
        teacher.setLastName(teacherRequest.getLastName().toLowerCase());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setPhone(teacherRequest.getPhone());
        teacher.setAddress(teacherRequest.getAddress());
        teacher.setGender(teacherRequest.getGender());
        teacher.setDob(teacherRequest.getDob());
        teacher.setAge(Period.between(teacherRequest.getDob(), LocalDate.now()).getYears());

        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

}
