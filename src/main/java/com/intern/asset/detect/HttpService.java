package com.intern.asset.detect;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {
    public String http(String urlString) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 设置超时为5秒
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return urlString + " is up!";
            } else {
                return urlString + " is down!";
            }
        } catch (Exception e) {
            throw new DetectException("Error accessing" + urlString);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
