package com.intern.asset.model;

public record RegisterRequest(
        String username,
        String password,
        UserRole role
) {
}
