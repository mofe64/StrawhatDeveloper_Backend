package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.UserDto;
import com.strawhatdev.backend.exceptions.UserException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(UserDto userDetails) throws UserException;
    void registerSuperUser(UserDto userDetails) throws UserException;
    List<UserDto> getAllUsers();
    UserDto findUserByUsername(String username) throws UserException;
}
