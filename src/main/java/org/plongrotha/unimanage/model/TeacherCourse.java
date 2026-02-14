package org.plongrotha.unimanage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class TeacherCourse {

    @EmbeddedId
    private TeacherCourseId id;

    @ManyToOne
    @JsonBackReference
    @MapsId("teacherId") // Maps teacherId from the Embeddable
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JsonBackReference
    @MapsId("courseId") // Maps courseId from the Embeddable
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

}
