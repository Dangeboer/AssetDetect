package com.intern.asset.detect;

import com.intern.asset.model.AssetResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class PortService {
    public AssetResponse port(String asset) {
        String[] assets = asset.split(":");
        String ip = assets[0];
        int port = Integer.parseInt(assets[1]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            return new AssetResponse(asset, "Alive", "Success");

        } catch (SocketTimeoutException e) { // 超时即不存活
            return new AssetResponse(asset, "Dead", "Time Out");

        } catch (IOException e) {
            return new AssetResponse(asset, "Fail", "Unknown Host");
        }
    }
}
