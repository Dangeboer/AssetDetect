package com.intern.asset.function.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {
    private final HttpService httpService;

    public HttpController(HttpService httpService) {
        this.httpService = httpService;
    }

    @GetMapping("/http")
    public String http(@RequestParam String url, @RequestParam int TTL) {
        return httpService.checkHttp(url, TTL);
    }
}
