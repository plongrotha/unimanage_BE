package org.plongrotha.unimanage.dto.res;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long courseId;
    private String courseName;
    private String courseCode;
    private int credits;
    private String description;
    private BigDecimal fee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
