package com.intern.asset.function.port;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class PortService {
    public String scanPort(String asset) {
        String[] assets = asset.split(":");
        String ip = assets[0];
        int port = Integer.parseInt(assets[1]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            return "Port " + port + " on " + ip + " is open!";
        } catch (IOException e) {
            return "Port " + port + " on " + ip + " is closed!";
        }
    }
}
