package com.benjamin.E_invoice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @NotNull(message = "Name Cannot be Null")
    @JsonProperty
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Email cannot be Null")
    @Email(message = "Email must be Valid")
    @JsonProperty
    @Column(nullable = false,unique = true)
    private String email;
    @Column
    @NotNull(message = "Password Cannot Be Null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;
    @JsonProperty
    @Column
    private String phoneNumber;
}
