package com.machines.xrut.user;


import com.machines.xrut.auth.LoginDto;
import com.machines.xrut.auth.LoginResponseDto;
import com.machines.xrut.auth.UserDto;

import java.util.Optional;

public interface AuthService {
    Optional<User> createUser(UserDto userDto);
    LoginResponseDto login(LoginDto loginDto);
}
