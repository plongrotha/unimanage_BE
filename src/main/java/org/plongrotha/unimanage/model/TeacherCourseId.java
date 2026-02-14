package org.plongrotha.unimanage.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TeacherCourseId implements Serializable {
    private Long teacherId;
    private Long courseId;

    public TeacherCourseId(Long teacherId, Long courseId) {
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    private LocalDateTime assignedAt;
    private LocalDateTime changeAssignAt;
}
