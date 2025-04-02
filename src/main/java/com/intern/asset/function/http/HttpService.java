package com.intern.asset.function.http;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {
    public String checkHttp(String urlString, int TTL) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TTL); // 设置超时为5秒
            connection.setReadTimeout(TTL);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return urlString + " is up!";
            } else {
                return urlString + " is down! Status code: " + responseCode;
            }
        } catch (Exception e) {
            return "Error accessing " + urlString + ": " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
