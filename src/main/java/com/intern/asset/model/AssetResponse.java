package com.intern.asset.model;

public record AssetResponse(
        String asset,
        String status,
        String message
) {
}
