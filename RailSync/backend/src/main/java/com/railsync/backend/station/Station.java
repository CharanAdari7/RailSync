package com.railsync.backend.station;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @Column(name = "station_code", nullable = false, length = 10)
    private String stationCode;

    @Column(name = "station_name", nullable = false, length = 150)
    private String stationName;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    private double latitude;
    private double longitude;

    @Column(name = "platform_count", nullable = false)
    private int platformCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StationStatus status = StationStatus.ACTIVE;

    protected Station() {
    }

    public Station(String stationCode, String stationName, String city, String state,
                   double latitude, double longitude, int platformCount) {
        this.stationCode = normalizeCode(stationCode);
        this.stationName = requireText(stationName, "Station name is required.");
        this.city = city == null ? "" : city.trim();
        this.state = state == null ? "" : state.trim();
        this.latitude = latitude;
        this.longitude = longitude;
        if (platformCount < 0) {
            throw new IllegalArgumentException("Platform count cannot be negative.");
        }
        this.platformCount = platformCount;
        this.status = StationStatus.ACTIVE;
    }

    private static String normalizeCode(String value) {
        return requireText(value, "Station code is required.").toUpperCase();
    }

    private static String requireText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public String getStationCode() { return stationCode; }
    public String getStationName() { return stationName; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getPlatformCount() { return platformCount; }
    public StationStatus getStatus() { return status; }

    public void update(String stationName, String city, String state,
                       double latitude, double longitude, int platformCount,
                       StationStatus status) {
        this.stationName = requireText(stationName, "Station name is required.");
        this.city = city == null ? "" : city.trim();
        this.state = state == null ? "" : state.trim();
        this.latitude = latitude;
        this.longitude = longitude;
        if (platformCount < 0) {
            throw new IllegalArgumentException("Platform count cannot be negative.");
        }
        this.platformCount = platformCount;
        this.status = status == null ? StationStatus.ACTIVE : status;
    }
}
