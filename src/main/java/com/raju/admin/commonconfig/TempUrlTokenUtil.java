package com.raju.admin.commonconfig;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class TempUrlTokenUtil {

    private static final String SECRET = "aboosto-secret-key";
    private static final long EXPIRY_MS = 5 * 60 * 1000; // 5 minutes

    public String generateToken(String filePath) {

        long expiryTime = System.currentTimeMillis() + EXPIRY_MS;
        String payload = filePath + "|" + expiryTime;

        String signature = hmacSha256(payload, SECRET);
        String tokenData = payload + "|" + signature;

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(tokenData.getBytes(StandardCharsets.UTF_8));
    }

    public String validateToken(String token) throws Exception {

        String decoded = new String(
                Base64.getUrlDecoder().decode(token),
                StandardCharsets.UTF_8
        );

        String[] parts = decoded.split("\\|");

        if (parts.length != 3) {
            throw new Exception("Invalid token structure");
        }

        String filePath = parts[0];
        long expiry = Long.parseLong(parts[1]);
        String signature = parts[2];

        if (System.currentTimeMillis() > expiry) {
            throw new Exception("Token expired");
        }

        String expectedSig = hmacSha256(filePath + "|" + expiry, SECRET);
        if (!expectedSig.equals(signature)) {
            throw new Exception("Invalid token signature");
        }

        return filePath;
    }

    private String hmacSha256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
            return Base64.getUrlEncoder().encodeToString(mac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
