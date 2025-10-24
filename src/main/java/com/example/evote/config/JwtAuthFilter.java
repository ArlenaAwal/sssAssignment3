package com.example.evote.config;
import com.example.evote.repo.UserRepository;
import io.jsonwebtoken.*; import jakarta.servlet.*; import jakarta.servlet.http.*; import java.io.IOException;
import org.springframework.http.HttpHeaders; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.util.stream.Collectors;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final io.jsonwebtoken.JwtParser parser; private final UserRepository users;
  public JwtAuthFilter(com.example.evote.service.TokenIssuer issuer, UserRepository users){
    this.parser = Jwts.parserBuilder().setSigningKey(issuer.key()).build(); this.users = users;
  }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
    if(auth != null && auth.startsWith("Bearer ")){
      try{
        String token = auth.substring(7);
        Jws<Claims> jws = parser.parseClaimsJws(token);
        String username = jws.getBody().getSubject();
        var user = users.findByUsername(username).orElse(null);
        if(user != null && user.isEnabled()){
          var authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r.getName())).collect(Collectors.toList());
          SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        }
      }catch(Exception e){}
    }
    chain.doFilter(req, res);
  }
}
