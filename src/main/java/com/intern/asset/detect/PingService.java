package com.intern.asset.detect;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class PingService {
    public String ping(String ip) {
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

        } catch (IOException e) { // exec() 和 readLine()
            throw new DetectException("I/O error while executing ping");

        } catch (InterruptedException e) { // waitFor()
            Thread.currentThread().interrupt();
            throw new DetectException("Ping command was interrupted.");
        }
    }
}
