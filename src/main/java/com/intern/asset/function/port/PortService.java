package com.intern.asset.function.port;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class PortService {
    public String scanPort(String ip, int port, int timeoutMs) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), timeoutMs);
            return "Port " + port + " on " + ip + " is open!";
        } catch (IOException e) {
            return "Port " + port + " on " + ip + " is closed!";
        }
    }
}
