package com.machines.xrut.user;

import com.machines.xrut.auth.LoginDto;
import com.machines.xrut.auth.LoginResponseDto;
import com.machines.xrut.auth.UserDto;
import com.machines.xrut.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final AuthenticationManager authManager;

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthServiceImpl(UserRepository repository, AuthenticationManager authManager, JWTService jwtService) {
        this.repository = repository;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

//    public String verify(User user) {
//        Authentication authentication =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getEmail());
//
//        }
//        return "Failure";
//    }

    @Override
    public Optional<User> createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setUsername(userDto.email());
        user.setPassword(encoder.encode(userDto.password()));
        user.setRole(userDto.role());
        return Optional.of(repository.save(user));
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        User user = repository.findByEmail(loginDto.email());
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), loginDto.password()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRole());
            return new LoginResponseDto(
                    token,
                    user.getName(),  // Assuming the User entity has a name field
                    user.getEmail(),
                    user.getRole()
            );

        }


        throw new RuntimeException("Invalid credentials");
    }
}
