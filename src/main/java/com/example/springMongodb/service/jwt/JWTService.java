package com.example.springMongodb.service.jwt;



import com.example.springMongodb.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("tt/tMaTsPvG5cy1c1EhKhKEb22Pr69j55n5MxEAIyUU=".getBytes());
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;


    // Token blacklist for invalidated tokens
    private final List<String> tokenBlacklist = new ArrayList<>();

    // must explain
    public String generateToken (Users user) {
        Map<String, Object> claims = new HashMap<>();  // to store other infos that you want to add in the jwt token playload (role,permission ,...)
        claims.put("username",user.getUsername()) ;
        claims.put("id",user.getId()) ;

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    private SecretKey getSecretKey() {
        return (SecretKey) SECRET_KEY;
    }

    //////////////////// code added
    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);

        if (isTokenBlacklisted(token)) {
            return false;
        }

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Logout method: blacklist the token
    public String logout(String token) {
        if (token != null) {
            tokenBlacklist.add(token);
            return "Token invalidated successfully.";
        }
        return "Invalid token.";
    }

    // Check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}

