package com.example.evote.repo;
import com.example.evote.domain.AppUser; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<AppUser, Long> { Optional<AppUser> findByUsername(String username); }
