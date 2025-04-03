package com.intern.asset.detect;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {
    public boolean http(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 设置超时为5秒
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            throw new DetectException("Error accessing" + urlString);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
