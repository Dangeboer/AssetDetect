package com.intern.asset.function.detect;

import com.intern.asset.authentication.AuthenticationService;
import com.intern.asset.function.http.HttpService;
import com.intern.asset.function.ping.PingService;
import com.intern.asset.function.port.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectController {

    private final HttpService httpService;
    private final PingService pingService;
    private final PortService portService;
    private final AuthenticationService authenticationService;

    public DetectController(
            HttpService httpService,
            PingService pingService,
            PortService portService,
            AuthenticationService authenticationService
    ) {
        this.httpService = httpService;
        this.pingService = pingService;
        this.portService = portService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/detect")
    public String detect(@RequestParam String asset) {
        try {
            if (isPort(asset) && !authenticationService.isAdmin()) {
                return "You don't have permission to detect this asset.";
            }

            if (isIP(asset)) {
                return pingService.ping(asset);
            } else if (isPort(asset)) {
                return portService.scanPort(asset);
            } else if (isURL(asset)) {
                return httpService.checkHttp(asset);
            } else {
                return "Input Asset is invalid.";
            }
        } catch (Exception e) {
            throw new InvalidAssetException(e.getMessage());
        }
    }

    private boolean isIP(String asset) {
        return asset.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$");
    }

    private boolean isPort(String asset) {
        return asset.matches("^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|[1-9]?\\d):(\\d{1,5})$");
    }

    private boolean isURL(String asset) {
        return asset.matches("^(https?|ftp)://[\\w.-]+(:\\d+)?(/.*)?$");
    }
}
