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
                return new AssetResponse(urlString, "Alive", "Success");
            }
            return new AssetResponse(urlString, "Dead", String.valueOf(responseCode));

        } catch (IOException e) { // 网络连接失败或超时 // openConnection(), setRequestMethod("GET"), getResponseCode()
//            throw new DetectException("Error Access");
            return new AssetResponse(urlString, "Fail", "Error Access");
        } finally {
            // 确保连接关闭
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
