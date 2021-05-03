package com.strawhatdev.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @NotBlank(message = "please enter your username")
    private String username;
    @NotNull
    @NotBlank(message = "please enter your password")
    private String password;
}
