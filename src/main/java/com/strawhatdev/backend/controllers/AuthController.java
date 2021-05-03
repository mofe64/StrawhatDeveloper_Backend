package com.strawhatdev.backend.controllers;

import com.strawhatdev.backend.dtos.APIResponse;
import com.strawhatdev.backend.dtos.AuthToken;
import com.strawhatdev.backend.dtos.LoginRequest;
import com.strawhatdev.backend.dtos.UserDto;
import com.strawhatdev.backend.exceptions.UserException;
import com.strawhatdev.backend.security.jwt.TokenProvider;
import com.strawhatdev.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenProvider jwtUtil;
    private UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenProvider jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<?> generateToken(@RequestBody @Valid LoginRequest loginRequest) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        return new ResponseEntity<>(new AuthToken(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return new ResponseEntity<>(new APIResponse(true, "User successfully registered"), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }));
        return errors;
    }
}
