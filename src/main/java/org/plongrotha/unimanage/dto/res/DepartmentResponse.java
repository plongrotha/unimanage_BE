package org.plongrotha.unimanage.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long departmentId;
    private Long facultyId;
    private String departmentName;
    private String facultyName;
    private String description;
    private String createdAt;
    private String updatedAt;
}
