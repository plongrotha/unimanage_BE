package org.plongrotha.unimanage.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long facultyId;
    private String facultyCode;
    private String facultyName;
    private String status;
    private String createdAt;
    private String updatedAt;
}
