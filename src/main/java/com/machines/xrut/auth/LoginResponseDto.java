package com.machines.xrut.auth;

public record LoginResponseDto(
        String token,
        String name,
        String email,
        String role
) {}
