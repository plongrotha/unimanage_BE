package org.plongrotha.unimanage.service.impl;

import org.plongrotha.unimanage.service.ClassService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    @Override
    public void createClass(org.plongrotha.unimanage.dto.req.ClassRequest classRequest) {

    }

    @Override
    public void updateClass(Long classId, org.plongrotha.unimanage.dto.req.ClassRequest classRequest) {

    }

    @Override
    public void deleteClass(Long classId) {

    }

    @Override
    public java.util.List<org.plongrotha.unimanage.model.Class> getAllClasses() {
        return null;
    }

    @Override
    public org.plongrotha.unimanage.model.Class getClassById(Long classId) {
        return null;
    }

}
