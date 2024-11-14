package com.cavad.promanage.dto;

public record LoginRequest(
        String email,
        String password
) {
}
