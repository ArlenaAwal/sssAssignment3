package com.example.evote.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="votes", indexes={@Index(name="idx_votes_election",columnList="election_id"),@Index(name="idx_votes_receipt",columnList="receipt_id", unique=true)})
@Getter @Setter @NoArgsConstructor
public class Vote {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="election_id", nullable=false) private UUID electionId;
  @Column(name="candidate_hash", nullable=false, length=128) private String candidateIdHash;
  @Column(name="per_vote_salt", nullable=false, length=64) private String perVoteSalt;
  @Column(name="timestamp_utc", nullable=false) private Instant timestampUtc;
  @Column(name="receipt_id", nullable=false, unique=true) private UUID receiptId;
  @Column(name="prev_hash", nullable=false, length=128) private String prevHash;
  @Column(name="row_hash", nullable=false, length=128) private String rowHash;
  @Column(name="geo_country") private String geoCountry;
  @Column(name="geo_region") private String geoRegion;
}
