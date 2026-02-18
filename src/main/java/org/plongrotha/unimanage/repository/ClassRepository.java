package org.plongrotha.unimanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<org.plongrotha.unimanage.model.Class, Long> {

    boolean existsByClassName(String className);
}
