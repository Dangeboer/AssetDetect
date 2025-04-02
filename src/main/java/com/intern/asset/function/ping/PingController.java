package com.intern.asset.function.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PingController {

    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/ping")
    public String ping2(@RequestParam String ip) throws IOException, InterruptedException {
        return pingService.ping(ip) ? "Alive" : "Dead";
    }
}
