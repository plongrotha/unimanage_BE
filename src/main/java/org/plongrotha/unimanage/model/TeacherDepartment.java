package org.plongrotha.unimanage.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeacherDepartment {

    @EmbeddedId
    private TeacherDepartmentId id;

    @ManyToOne
    @MapsId("teacherId")
    @JsonBackReference
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @MapsId("departmentId")
    @JsonBackReference
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "assigned_date")
    private LocalDateTime assignedDate;

    @Column(name = "unassigned_date")
    private LocalDateTime unassignedDate;

    @PrePersist
    void onAssign() {
        this.assignedDate = LocalDateTime.now();
    }

    @PreUpdate
    void onUnassign() {
        this.unassignedDate = LocalDateTime.now();
    }

}
