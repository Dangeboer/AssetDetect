package com.intern.asset.detect;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class PortService {
    public boolean port(String asset) {
        String[] assets = asset.split(":");
        String ip = assets[0];
        int port = Integer.parseInt(assets[1]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
