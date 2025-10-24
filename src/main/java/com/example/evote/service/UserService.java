package com.example.evote.service;
import com.example.evote.domain.*; import com.example.evote.repo.*; import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.Set;
@Service @RequiredArgsConstructor
public class UserService {
  private final UserRepository users; private final RoleRepository roles; private final PasswordEncoder encoder;
  @Transactional public void ensureAdmin(String username, String password){
    if(users.findByUsername(username).isPresent()) return;
    Role admin = roles.findByName("ADMIN").orElseGet(() -> roles.save(new Role(null, "ADMIN")));
    Role auditor = roles.findByName("AUDITOR").orElseGet(() -> roles.save(new Role(null, "AUDITOR")));
    Role voter = roles.findByName("VOTER").orElseGet(() -> roles.save(new Role(null, "VOTER")));
    AppUser u = new AppUser(); u.setUsername(username); u.setPasswordHash(encoder.encode(password)); u.setRoles(Set.of(admin, auditor)); users.save(u);
  }
  @Transactional public void ensureVoter(String username, String password){
    if(users.findByUsername(username).isPresent()) return;
    Role voter = roles.findByName("VOTER").orElseGet(() -> roles.save(new Role(null, "VOTER")));
    AppUser u = new AppUser(); u.setUsername(username); u.setPasswordHash(encoder.encode(password)); u.setRoles(Set.of(voter)); users.save(u);
  }
}
