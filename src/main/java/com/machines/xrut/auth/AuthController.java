package com.machines.xrut.auth;

import com.machines.xrut.user.User;
import com.machines.xrut.user.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        Optional<User> user = authService.createUser(userDto);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        LoginResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
