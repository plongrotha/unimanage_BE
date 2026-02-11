package org.plongrotha.unimanage.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TeacherDepartmentId implements Serializable {
    private Long teacherId;
    private Long departmentId;
}
