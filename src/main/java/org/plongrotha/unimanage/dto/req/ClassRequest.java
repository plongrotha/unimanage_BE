package org.plongrotha.unimanage.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequest {
    @NotEmpty(message = "Class name must not be empty")
    private String className;

    private String status;
}