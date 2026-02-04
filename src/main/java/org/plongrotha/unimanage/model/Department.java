package org.plongrotha.unimanage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private String departmentName;

    private String description;

    @OneToMany(mappedBy = "department")
    @JsonManagedReference
    @JsonIgnore
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
