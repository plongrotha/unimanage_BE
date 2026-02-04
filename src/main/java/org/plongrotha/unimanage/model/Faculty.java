package org.plongrotha.unimanage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    private String facultyCode;

    private String facultyName;

    private String status;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    @JsonIgnore
//    @Transient
    private List<Department> departments;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
