package org.plongrotha.unimanage.repository;

import org.plongrotha.unimanage.enums.Gender;
import org.plongrotha.unimanage.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmail(String email);

    List<Teacher> findAllByGender(Gender gender);

    @Query("""
            SELECT t FROM Teacher t
            """)
    Page<Teacher> findAllTeacher(Pageable pageable);

}
