package com.machines.xrut.service;


import com.machines.xrut.dto.userdto.LoginDto;
import com.machines.xrut.dto.userdto.LoginResponseDto;
import com.machines.xrut.dto.userdto.UserDto;
import com.machines.xrut.entity.User;

import java.util.Optional;

public interface AuthService {
    Optional<User> createUser(UserDto userDto);
    LoginResponseDto login(LoginDto loginDto);
}
