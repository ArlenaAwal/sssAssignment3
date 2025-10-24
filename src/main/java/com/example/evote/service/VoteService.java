package com.example.evote.service;
import com.example.evote.domain.*; import com.example.evote.dto.*; import com.example.evote.repo.*; import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.nio.charset.StandardCharsets; import java.security.MessageDigest;
import java.time.Instant; import java.util.HexFormat; import java.util.Optional; import java.util.UUID;
@Service @RequiredArgsConstructor
public class VoteService {
  private final VoteRepository repo; private final ElectionRepository elections;
  private static String sha256(String s){ try{ return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8))); }catch(Exception e){ throw new RuntimeException(e); } }
  @Transactional
  public ReceiptDTO cast(CastVoteRequest req, Optional<String> geoCountry, Optional<String> geoRegion){
    Election el = elections.findById(req.electionId()).orElseThrow(() -> new IllegalArgumentException("Unknown election"));
    Instant now = Instant.now();
    if(!el.isActive() || now.isBefore(el.getStartTimeUtc()) || now.isAfter(el.getEndTimeUtc())) throw new IllegalStateException("Election not active");
    String perVoteSalt = UUID.randomUUID().toString().replace("-", "");
    String candidateHash = sha256(req.electionId() + "|" + req.candidateId() + "|" + el.getElectionSalt() + "|" + perVoteSalt);
    Instant ts = now;
    UUID receiptId = UUID.randomUUID();
    String prevHash = repo.findTopByOrderByIdDesc().map(Vote::getRowHash).orElse("GENESIS");
    String rowMaterial = String.join("|", prevHash, req.electionId().toString(), candidateHash, perVoteSalt, ts.toString(), receiptId.toString(), geoCountry.orElse(""), geoRegion.orElse(""));
    String rowHash = sha256(rowMaterial);
    Vote v = new Vote();
    v.setElectionId(req.electionId()); v.setCandidateIdHash(candidateHash); v.setPerVoteSalt(perVoteSalt);
    v.setTimestampUtc(ts); v.setReceiptId(receiptId); v.setPrevHash(prevHash); v.setRowHash(rowHash);
    geoCountry.ifPresent(v::setGeoCountry); geoRegion.ifPresent(v::setGeoRegion);
    repo.save(v);
    return new ReceiptDTO(receiptId, ts, geoCountry.orElse(null), geoRegion.orElse(null));
  }
}
