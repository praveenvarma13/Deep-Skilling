package com.cognizant.springlearn.controller;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

    // Generate a secure secure signing key valid for HMAC-SHA algorithms
    private final SecretKey key = Keys.hmacShaKeyFor("YourSuperSecretSecureSigningKeyMustBeLongEnough!!!".getBytes(StandardCharsets.UTF_8));

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Simple check against our in-memory data requirements 
        if ("user".equals(username) && "pwd".equals(password)) {
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Date expiry = new Date(nowMillis + 1800000); // Token expires in 30 minutes

            // Mint the token string as outlined in the architecture requirements
            String jwtToken = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            return ResponseEntity.ok(jwtToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR: Invalid Credentials provided.");
    }
}