package com.example.evote.controller;
import com.example.evote.dto.*; import com.example.evote.repo.UserRepository; import com.example.evote.service.TokenIssuer; import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import jakarta.validation.Valid;
import java.util.Set; import java.util.stream.Collectors;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
  private final UserRepository users; private final PasswordEncoder encoder; private final TokenIssuer tokens;
  @PostMapping("/login") public TokenResponse login(@RequestBody @Valid LoginRequest req){
    var u = users.findByUsername(req.username()).orElseThrow(() -> new RuntimeException("Bad credentials"));
    if(!encoder.matches(req.password(), u.getPasswordHash())) throw new RuntimeException("Bad credentials");
    if(u.getMfaSecret()!=null && !u.getMfaSecret().isBlank()){
      if(req.otp()==null || req.otp().isBlank()) throw new RuntimeException("OTP required");
      // TODO: verify TOTP
    }
    return new TokenResponse(tokens.issue(u.getUsername(), 3600));
  }
  @GetMapping("/me") public Me me(Authentication auth){
    if(auth==null) throw new RuntimeException("Unauthenticated");
    var user = users.findByUsername(auth.getName()).orElseThrow();
    Set<String> roles = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet());
    return new Me(user.getUsername(), roles);
  }
  public record Me(String username, Set<String> roles) {}
}
