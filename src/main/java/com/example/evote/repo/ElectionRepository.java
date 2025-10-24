package com.example.evote.repo;
import com.example.evote.domain.Election; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID; import java.util.Optional; import java.time.Instant;
public interface ElectionRepository extends JpaRepository<Election, UUID> {
  Optional<Election> findByCode(String code);
  boolean existsByIdAndActiveIsTrueAndStartTimeUtcBeforeAndEndTimeUtcAfter(UUID id, Instant now1, Instant now2);
}
