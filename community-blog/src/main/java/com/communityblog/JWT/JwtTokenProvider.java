package com.communityblog.JWT;

import com.communityblog.model.BlogUser;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final Key key;

    public JwtTokenProvider() {
        String secret = UUID.randomUUID().toString();
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(BlogUser blogUser) {
        Long id = blogUser.getId();
        String username = blogUser.getUsername();

        return Jwts.builder()
                .setSubject(Long.toString(id))
                .claim("id", id)
                .claim("username", username)
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

    public boolean validate(String token) {
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
