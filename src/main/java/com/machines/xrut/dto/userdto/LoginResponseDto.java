package com.machines.xrut.dto.userdto;

public record LoginResponseDto(
        String token,
        String name,
        String email,
        String role
) {}
