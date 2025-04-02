package com.intern.asset.function.port;

import com.intern.asset.model.PortRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PortController {
    private final PortService portService;

    public PortController(PortService portService) {
        this.portService = portService;
    }

    @GetMapping("/port")
    public String port(@RequestBody PortRequest body) throws IOException {
        return portService.scanPort(body.ip(), body.port(), body.timeoutMs());
    }
}
