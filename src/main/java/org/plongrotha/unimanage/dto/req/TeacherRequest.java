package org.plongrotha.unimanage.dto.req;

import java.time.LocalDate;

import org.plongrotha.unimanage.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherRequest {

    private String firstName;
    private String lastName;

    @NotNull(message = "Gender must not be null")
    private Gender gender;

    @Schema(description = "Date of birth in the format YYYY-MM-DD", example = "2000-01-01")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Schema(description = "Address of the teacher", example = "Phnom Penh, Cambodia")
    private String address;

    @Schema(description = "Email address of the teacher", example = "teacher@gmail.com")
    @Email(message = "Email should be valid")
    private String email;

    private String phone;
}
