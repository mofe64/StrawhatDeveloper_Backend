package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.UserDto;
import com.strawhatdev.backend.exceptions.UserException;
import com.strawhatdev.backend.models.Role;
import com.strawhatdev.backend.models.User;
import com.strawhatdev.backend.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Todo add functionality to login with email;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadAUserByUsername(username);
    }

    private UserDetails loadAUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("No user found with this username %s", username))
                );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthorities(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.addAll(role.getGrantedAuthorities());
        });
        return authorities;
    }


    @Override
    public void registerUser(UserDto userDetails) throws UserException {
        boolean usernameExists = doesUsernameExist(userDetails.getUsername());
        boolean emailExists = doesEmailExist(userDetails.getEmail());
        if (usernameExists) {
            throw new UserException(String.format("This username already exists %s", userDetails.getUsername()));
        }
        if (emailExists) {
            throw new UserException(String.format("This email already exists %s", userDetails.getEmail()));
        }
        User user = UserDto.unpackDto(userDetails);
        user.getRoles().add(Role.ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        saveUser(user);
    }

    @Override
    public void registerSuperUser(UserDto userDetails) throws UserException {
        User user = UserDto.unpackDto(userDetails);
        boolean usernameExists = doesUsernameExist(user.getUsername());
        boolean emailExists = doesEmailExist(user.getEmail());
        if (usernameExists) {
            throw new UserException(String.format("This username already exists %s", user.getUsername()));
        }
        if (emailExists) {
            throw new UserException(String.format("This email already exists %s", user.getEmail()));
        }
        user.getRoles().add(Role.SUPER_ADMIN);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        saveUser(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return getUsers().stream().map(UserDto::packDto).collect(Collectors.toList());
    }

    private List<User> getUsers() {
        return userRepository.findAll();
    }


    private boolean doesUsernameExist(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }


    private boolean doesEmailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public UserDto findUserByUsername(String username) throws UserException {
        return UserDto.packDto(findUserWithUsername(username));
    }

    private User findUserWithUsername(String username) throws UserException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserException(String.format("No User found with this username %s", username)));
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }
}
