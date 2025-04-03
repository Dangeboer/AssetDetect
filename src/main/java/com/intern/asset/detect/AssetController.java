package com.intern.asset.detect;

import com.intern.asset.model.AssetResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/asset")
    public List<AssetResponse> probeAssets(@RequestBody List<String> assets) {
        return assetService.detect(assets);
    }

}
