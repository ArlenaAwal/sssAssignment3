package com.example.evote.repo;
import com.example.evote.domain.AuditLog; import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
