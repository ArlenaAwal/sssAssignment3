package com.example.evote.repo;
import com.example.evote.domain.Vote; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; import java.util.UUID;
public interface VoteRepository extends JpaRepository<Vote, Long> {
  Optional<Vote> findTopByOrderByIdDesc();
  boolean existsByReceiptId(UUID id);
}
