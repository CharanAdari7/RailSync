package com.railsync.backend.station;

public class StationNotFoundException extends RuntimeException {

    public StationNotFoundException(String stationCode) {
        super("Station not found: " + stationCode);
    }
}
