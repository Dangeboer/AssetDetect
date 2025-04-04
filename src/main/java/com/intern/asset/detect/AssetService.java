package com.intern.asset.detect;

import com.intern.asset.authentication.AuthenticationService;
import com.intern.asset.model.AssetResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {

    private final HttpService httpService;
    private final PingService pingService;
    private final PortService portService;
    private final AuthenticationService authenticationService;

    public AssetService(
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

    public List<AssetResponse> detectEachAsset(List<String> assets) {
        List<AssetResponse> results = new ArrayList<>();

        for (String asset : assets) {
            results.add(checkIfAliveOrDead(asset));
        }

        return results;
    }

    public AssetResponse checkIfAliveOrDead(String asset) {
        if (isPort(asset) && !authenticationService.isAdmin()) {
            return new AssetResponse(asset, "Fail", "No Permission");
        }

        if (isIP(asset)) {
            return pingService.ping(asset);
        } else if (isPort(asset)) {
            return portService.port(asset);
        } else if (isURL(asset)) {
            return httpService.http(asset);
        }
        return new AssetResponse(asset, "Fail", "Unknown Asset");
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
