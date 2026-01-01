package com.logging.crud.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
