package com.example.evote.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant;
@Entity @Table(name="audit_logs", indexes={@Index(name="idx_audit_when", columnList="at_utc")})
@Getter @Setter @NoArgsConstructor
public class AuditLog {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="actor", nullable=false) private String actor;
  @Column(name="action", nullable=false) private String action;
  @Column(name="resource", nullable=false) private String resource;
  @Column(name="at_utc", nullable=false) private Instant atUtc;
  @Column(name="ip") private String ip;
  @Column(name="geo") private String geo;
}
