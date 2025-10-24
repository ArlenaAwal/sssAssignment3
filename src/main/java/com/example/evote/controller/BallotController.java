package com.example.evote.controller;
import com.example.evote.dto.*; import com.example.evote.service.VoteService; import jakarta.servlet.http.HttpServletRequest; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.Optional;
@RestController @RequestMapping("/api/ballot") @RequiredArgsConstructor
public class BallotController {
  private final VoteService votes;
  @PostMapping("/cast") @PreAuthorize("hasRole('VOTER')")
  public ReceiptDTO cast(@RequestBody @Valid CastVoteRequest req, HttpServletRequest http){
    Optional<String> country = Optional.ofNullable(http.getHeader("X-Geo-Country"));
    Optional<String> region = Optional.ofNullable(http.getHeader("X-Geo-Region"));
    return votes.cast(req, country, region);
  }
}
