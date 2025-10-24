package com.example.evote.service;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import java.security.Key; import java.time.Instant; import java.util.Date;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component;
@Component
public class TokenIssuer {
  private final Key key;
  public TokenIssuer(@Value("${app.jwt.secret}") String secret){ this.key = Keys.hmacShaKeyFor(secret.getBytes()); }
  public String issue(String subject, long ttlSeconds){
    Instant now = Instant.now(); Instant exp = now.plusSeconds(ttlSeconds);
    return Jwts.builder().setSubject(subject).setIssuedAt(Date.from(now)).setExpiration(Date.from(exp)).signWith(key).compact();
  }
  public Key key(){ return key; }
}
