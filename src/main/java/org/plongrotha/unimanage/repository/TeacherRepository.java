package org.plongrotha.unimanage.repository;

import org.plongrotha.unimanage.enums.Gender;
import org.plongrotha.unimanage.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmail(String email);

    List<Teacher> findAllByGender(Gender gender);

}
