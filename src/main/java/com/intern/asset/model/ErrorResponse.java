package com.intern.asset.model;

public record ErrorResponse(
        String message,
        String error
) {
}
