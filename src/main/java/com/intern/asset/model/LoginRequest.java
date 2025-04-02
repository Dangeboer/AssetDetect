package com.intern.asset.model;

public record LoginRequest(
        String username,
        String password
) {
}
