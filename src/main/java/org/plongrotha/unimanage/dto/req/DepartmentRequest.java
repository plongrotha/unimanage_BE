package org.plongrotha.unimanage.dto.req;

import lombok.Data;

@Data
public class DepartmentRequest {
    private Long facultyId;
    private String departmentName;
    private String description;
}
