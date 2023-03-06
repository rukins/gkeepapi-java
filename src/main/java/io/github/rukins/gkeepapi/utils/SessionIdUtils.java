package io.github.rukins.gkeepapi.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

public class SessionIdUtils {
    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public static String generateIdFromMacAddress() {
        InetAddress localIP;
        try {
            localIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        byte[] macAddress;
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);

            macAddress = ni.getHardwareAddress();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        String sessionId = UUID.nameUUIDFromBytes(macAddress).toString();

        return sessionId != null ? sessionId : generateSessionId();
    }
}
