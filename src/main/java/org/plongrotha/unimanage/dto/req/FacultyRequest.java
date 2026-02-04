package org.plongrotha.unimanage.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequest {
    private String facultyCode;
    private String facultyName;
}
