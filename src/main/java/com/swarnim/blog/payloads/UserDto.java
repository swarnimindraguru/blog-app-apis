package com.swarnim.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.swarnim.blog.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters !!")
    private String name;
    @Email(message = "Email address is not valid !!")
    private String email;
    @NotEmpty
    @Size(min=3, max = 10, message = "Password must be min of 3 characters and max of 10 characters !!")
    private String password;
    @NotEmpty
    private String about;
    private Set<RoleDto> roles = new HashSet<>();
}
