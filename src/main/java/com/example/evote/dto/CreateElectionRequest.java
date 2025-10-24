package com.example.evote.dto;
import jakarta.validation.constraints.*; import java.time.Instant;
public record CreateElectionRequest(@NotBlank String code, @NotBlank String name, @NotNull Instant startTimeUtc, @NotNull Instant endTimeUtc) {}
