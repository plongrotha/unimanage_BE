package org.plongrotha.unimanage.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCourseResponse {
    private Long courseId;
    private String courseName;
    private String courseCode;
    private List<String> teacher;
}
