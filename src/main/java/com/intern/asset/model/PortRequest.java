package com.intern.asset.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortRequest(
        String ip,
        int port,
        @JsonProperty("timeout_ms") int timeoutMs
) {
}
