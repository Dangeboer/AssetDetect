package com.intern.asset.detect;

import com.intern.asset.model.AssetResponse;
import com.intern.asset.model.UserEntity;
import com.intern.asset.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AssetService {

    private final HttpService httpService;
    private final PingService pingService;
    private final PortService portService;

    public AssetService(
            HttpService httpService,
            PingService pingService,
            PortService portService
    ) {
        this.httpService = httpService;
        this.pingService = pingService;
        this.portService = portService;
    }

    public List<AssetResponse> detectEachAsset(UserEntity user, List<String> assets) {
        int threadCount = Math.min(assets.size(), 10); // 最多开10个线程
        ExecutorService executor = Executors.newFixedThreadPool(threadCount); // 创建了一个固定大小的线程池
        List<Future<AssetResponse>> futures = new ArrayList<>();

        for (String asset : assets) { // 这里调用submit了就立即开始执行任务
            futures.add(executor.submit(() -> checkIfAliveOrDead(user, asset))); // 为每一个asset创建一个线程来执行
        }

        List<AssetResponse> results = new ArrayList<>();
        for (Future<AssetResponse> future : futures) {
            try {
                results.add(future.get()); // future.get() 会阻塞当前线程（调用future.get()的线程），直到所有任务执行完毕并返回结果
            } catch (InterruptedException e) {
                throw new DetectException("Command Interruption");
            } catch (ExecutionException e) {
                throw new DetectException("Execution Exception");
            }
        }

        executor.shutdown();
        return results;
    }

    public AssetResponse checkIfAliveOrDead(UserEntity user, String asset) {
        if (isPort(asset) && !user.getRole().equals(UserRole.ROLE_ADMIN)) {
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
