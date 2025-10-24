package com.example.evote.controller;
import com.example.evote.domain.Election; import com.example.evote.dto.CreateElectionRequest; import com.example.evote.repo.ElectionRepository;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom; import java.util.HexFormat; import java.util.List; import java.util.UUID;
@RestController @RequestMapping("/api/admin") @RequiredArgsConstructor
public class AdminController {
  private final ElectionRepository elections;
  private static String randomSalt(){ byte[] b = new byte[16]; new SecureRandom().nextBytes(b); return HexFormat.of().formatHex(b); }
  @PostMapping("/elections") @PreAuthorize("hasRole('ADMIN')")
  public Election create(@RequestBody @Valid CreateElectionRequest req){
    Election e = new Election(); e.setCode(req.code()); e.setName(req.name()); e.setElectionSalt(randomSalt());
    e.setStartTimeUtc(req.startTimeUtc()); e.setEndTimeUtc(req.endTimeUtc()); e.setActive(true); return elections.save(e);
  }
  @GetMapping("/elections") @PreAuthorize("hasAnyRole('ADMIN','AUDITOR')") public List<Election> all(){ return elections.findAll(); }
  @PostMapping("/elections/{id}/deactivate") @PreAuthorize("hasRole('ADMIN')") public Election deactivate(@PathVariable UUID id){
    var e = elections.findById(id).orElseThrow(); e.setActive(false); return elections.save(e);
  }
}
