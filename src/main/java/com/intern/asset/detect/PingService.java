package com.intern.asset.detect;

import com.intern.asset.model.AssetResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PingService {
    public AssetResponse ping(String ip) {
        try {
            Process process = Runtime.getRuntime().exec("ping -c 1 " + ip);
            int responseCode = process.waitFor(); // 等待命令执行完毕并返回一个退出代码，0 表示成功，非 0 表示失败
            if (responseCode == 0) {
                return new AssetResponse(ip, "存活", "探测成功");
            }
            return new AssetResponse(ip, "不存活", "探测超时");

        } catch (IOException e) { // exec()
            return new AssetResponse(ip, "失败", "未知地址");

        } catch (InterruptedException e) { // waitFor()
            Thread.currentThread().interrupt();
            return new AssetResponse(ip, "失败", "线程中断");
        }
    }
}
