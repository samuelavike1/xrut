package com.machines.xrut.dto.userdto;

public record UserDto(
        String name,
        String email,
        String password,
        String role
) {}
