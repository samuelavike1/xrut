package com.machines.xrut.auth;

public record UserDto(
        String name,
        String email,
        String password,
        String role
) {}
