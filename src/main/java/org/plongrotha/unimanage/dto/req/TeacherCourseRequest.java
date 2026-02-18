package org.plongrotha.unimanage.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCourseRequest {

    @NotNull(message = "Teacher ID must not be null")
    @Schema(description = "ID of the teacher", example = "1")
    private Long teacherId;

    @Schema(description = "ID of the course", example = "1")
    @NotNull(message = "Course ID must not be null")
    private Long courseId;

}
