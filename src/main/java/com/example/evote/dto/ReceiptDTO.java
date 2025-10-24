package com.example.evote.dto;
import java.time.Instant; import java.util.UUID;
public record ReceiptDTO(UUID receiptId, Instant timestampUtc, String geoCountry, String geoRegion) {}
