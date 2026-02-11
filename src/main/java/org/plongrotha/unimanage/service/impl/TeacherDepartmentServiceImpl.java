package org.plongrotha.unimanage.service.impl;

import java.util.List;

import org.plongrotha.unimanage.exception.BadRequestException;
import org.plongrotha.unimanage.exception.NotFoundException;
import org.plongrotha.unimanage.model.Teacher;
import org.plongrotha.unimanage.model.TeacherDepartment;
import org.plongrotha.unimanage.model.TeacherDepartmentId;
import org.plongrotha.unimanage.repository.DepartmentRepository;
import org.plongrotha.unimanage.repository.TeacherDepartmentRepository;
import org.plongrotha.unimanage.repository.TeacherRepository;
import org.plongrotha.unimanage.service.TeacherDepartmentService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherDepartmentServiceImpl implements TeacherDepartmentService {

    private final TeacherDepartmentRepository teacherDepartmentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public void assignTeacherToDepartment(Long teacherId, Long departmentId) {
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        var department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found"));

        if (teacherDepartmentRepository.existsByTeacherIdAndDepartmentId(teacherId, departmentId)) {
            throw new BadRequestException("Teacher is already assigned to this department");
        }
        var teacherDepartment = new TeacherDepartment();
        teacherDepartment.setId(new TeacherDepartmentId(teacherId, departmentId));
        teacherDepartment.setTeacher(teacher);
        teacherDepartment.setDepartment(department);
        teacherDepartmentRepository.save(teacherDepartment);
    }

    @Override
    public List<Teacher> getTeachersByDepartmentId(Long departmentId) {
        var department = teacherDepartmentRepository.findAllByDepartmentId(departmentId);
        return department.stream().map(TeacherDepartment::getTeacher).toList();
    }
}
