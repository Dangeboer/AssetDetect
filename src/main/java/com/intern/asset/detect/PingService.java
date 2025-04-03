package com.intern.asset.function.ping;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class PingService {

    public String ping(String ip) {
        try {
            InetAddress.getByName(ip); // 检查 IP 是否有效
        } catch (UnknownHostException e) {
            throw new PingException("Invalid IP address: " + ip);
        }
        
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command = os.contains("win") ? "ping -n 1 " + ip : "ping -c 1 " + ip;

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean isReachable = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("TTL=") || line.contains("ttl=")) {
                    isReachable = true;
                    break;
                }
            }
            process.waitFor();

            return isReachable ? ip + " is up!" : ip + " is down!";
        } catch (IOException e) {
            throw new PingException("I/O error while executing ping: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PingException("Ping command was interrupted.");
        }
    }
}
