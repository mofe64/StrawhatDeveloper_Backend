package com.strawhatdev.backend.dtos;

import com.strawhatdev.backend.models.Role;
import com.strawhatdev.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    @NotNull
    private String username;
    @NotNull
    @NotBlank(message = "firstname field cannot be empty")
    @Size(min = 2, message = "Firstname cannot be less than 2 characters")
    private String firstname;
    @NotNull
    @NotBlank(message = "lastname field cannot be empty")
    @Size(min = 2, message = "lastname cannot be less than 2 characters")
    private String lastname;
    @Email(message = "Please provide a valid email")
    private String email;
    @NotNull
    @NotBlank
    private String password;

    public static User unpackDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto packDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
