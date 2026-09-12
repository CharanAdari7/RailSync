package com.railsync.backend.station;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record StationRequest(
        @NotBlank String stationCode,
        @NotBlank String stationName,
        String city,
        String state,
        double latitude,
        double longitude,
        @Min(0) int platformCount,
        StationStatus status) {
}
