package com.example.evote.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="elections")
@Getter @Setter @NoArgsConstructor
public class Election {
  @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
  @Column(nullable=false, unique=true) private String code;
  @Column(nullable=false) private String name;
  @Column(nullable=false) private String electionSalt;
  @Column(nullable=false) private Instant startTimeUtc;
  @Column(nullable=false) private Instant endTimeUtc;
  @Column(nullable=false) private boolean active;
}
