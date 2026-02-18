package org.plongrotha.unimanage.model;

import org.plongrotha.unimanage.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long classId;
    private String className;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Status status;
}
