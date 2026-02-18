package org.plongrotha.unimanage.dto.req;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequest {

    @NotEmpty(message = "Course name must not be empty")
    private String courseName;

    @NotEmpty(message = "Course code must not be empty")
    private String courseCode;

    private String description;

    @Schema(description = "Course fee", example = "0.99")
    private BigDecimal fee;
}
