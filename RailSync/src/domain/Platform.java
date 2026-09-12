package domain;

import java.util.Objects;

public class Platform {

    private String platformId;
    private String stationId;
    private String platformNumber;
    private int capacity;
    private boolean operational;

    public Platform() {
        this.operational = true;
    }

    public Platform(String platformId, String stationId,
                    String platformNumber, int capacity) {

        setPlatformId(platformId);
        setStationId(stationId);
        setPlatformNumber(platformNumber);
        setCapacity(capacity);

        this.operational = true;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        if (platformId == null || platformId.trim().isEmpty()) {
            throw new IllegalArgumentException("Platform ID cannot be empty.");
        }
        this.platformId = platformId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Platform capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    public boolean isOperational() {
        return operational;
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    @Override
    public String toString() {
        return "Platform " + platformNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Platform)) return false;

        Platform other = (Platform) obj;
        return Objects.equals(platformId, other.platformId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platformId);
    }
}