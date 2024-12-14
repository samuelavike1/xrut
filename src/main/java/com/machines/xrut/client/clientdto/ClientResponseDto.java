package com.machines.xrut.client.clientdto;

public record ClientResponseDto(
        Long id,
        String clientId,
        String name,
        String phone,
        String email,
        String address,
        String apiKey
) {}
