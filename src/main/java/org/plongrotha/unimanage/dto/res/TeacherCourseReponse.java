package org.plongrotha.unimanage.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseReponse {
    private Long teacherId;
    private String firstName;
    private String lastName;
    private List<CourseResponse> courses;
}
