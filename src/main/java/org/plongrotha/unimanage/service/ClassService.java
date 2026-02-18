package org.plongrotha.unimanage.service;

import java.util.List;

import org.plongrotha.unimanage.dto.req.ClassRequest;

public interface ClassService {

    void createClass(ClassRequest classRequest);

    void updateClass(Long classId, ClassRequest classRequest);

    void deleteClass(Long classId);

    List<org.plongrotha.unimanage.model.Class> getAllClasses();

    org.plongrotha.unimanage.model.Class getClassById(Long classId);
}
