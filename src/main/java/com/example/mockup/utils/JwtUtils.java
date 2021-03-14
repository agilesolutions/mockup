package com.example.mockup.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    // verify and get claims using public key

    public static Claims verifyToken(String token, PublicKey publicKey) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();

            System.out.println(claims.get("id"));
            System.out.println(claims.get("role"));

        } catch (Exception e) {

            claims = null;
        }
        return claims;
    }

    public static String createSignedJwt(PrivateKey privateKey) {

        Map<String, Object> claims = new HashMap<String, Object>();

        // put your information into claim
        claims.put("id", "xxx");
        claims.put("role", "user");
        claims.put("created", new Date());

        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS512, privateKey).compact();

        return token;
    }
}
