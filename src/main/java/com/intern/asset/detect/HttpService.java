package com.intern.asset.detect;

import com.intern.asset.model.AssetResponse;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

@Service
public class HttpService {
    public AssetResponse http(String urlString) {
        HttpURLConnection connection = null;

        try {
            // 尝试将字符串转换为 URL 对象
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 设置超时为5秒
            connection.setReadTimeout(5000);
            connection.connect();

            // 获取响应代码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new AssetResponse(urlString, "存活", "探测成功");
            }
            return new AssetResponse(urlString, "不存活", String.valueOf(responseCode));

        } catch (IOException e) { // 网络连接失败或超时 // openConnection(), setRequestMethod("GET"), getResponseCode()
            return new AssetResponse(urlString, "失败", "连接失败");
        } finally {
            // 确保连接关闭
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
