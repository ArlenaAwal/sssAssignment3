package com.example.evote.dto;
import jakarta.validation.constraints.*; import java.util.UUID;
public record CastVoteRequest(@NotNull UUID electionId, @NotBlank String candidateId) {}
