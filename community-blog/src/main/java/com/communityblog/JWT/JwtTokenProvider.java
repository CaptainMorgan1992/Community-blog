
package com.communityblog.JWT;


import com.communityblog.model.User;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider() {
        String secret = UUID.randomUUID().toString();
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {
        Integer id = user.getId();
        String username = user.getUserName();

        Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1hour

        return Jwts.builder()
                .setSubject(Integer.toString(id))
                .claim("id", id)
                .claim("username", username)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public int getTokenId(String token) {
        return getTokenClaim(token, "id", Integer.class);
    }

    public <T> T getTokenClaim(String token, String type, Class<T> returnType) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(type, returnType);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
