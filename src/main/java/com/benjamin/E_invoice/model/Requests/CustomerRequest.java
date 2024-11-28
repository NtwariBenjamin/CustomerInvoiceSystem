package com.benjamin.E_invoice.model.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @JsonProperty
    @NotNull(message = "ID cannot be null.")

    private Long id;

    @JsonProperty
    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;

    @JsonProperty
    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    @JsonProperty
    @NotNull(message = "Enter Phone Number.")
    private String phoneNumber;

    @JsonProperty
    @NotNull(message = "Password cannot be null.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    @JsonProperty
    @NotNull(message = "Confirm Password cannot be null.")
    private String confirmPassword;
}
