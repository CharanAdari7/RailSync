package domain;

public class Route {

    private String routeName;
    private String sourceStationCode;
    private String destinationStationCode;
    private double distanceKm;
    private int stationCount;
    private ServiceStatus status;

    public Route() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Route(
            String routeName,
            String sourceStationCode,
            String destinationStationCode,
            double distanceKm,
            int stationCount) {

        if (sourceStationCode == null || sourceStationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Source station code is required.");
        }

        if (destinationStationCode == null || destinationStationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination station code is required.");
        }

        if (sourceStationCode.equalsIgnoreCase(destinationStationCode)) {
            throw new IllegalArgumentException(
                    "Source and destination stations cannot be the same.");
        }

        this.routeName = routeName;
        this.sourceStationCode = sourceStationCode.trim().toUpperCase();
        this.destinationStationCode = destinationStationCode.trim().toUpperCase();
        this.distanceKm = distanceKm;
        this.stationCount = stationCount;
        this.status = ServiceStatus.ACTIVE;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public void setSourceStationCode(String sourceStationCode) {
        this.sourceStationCode = sourceStationCode;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public int getStationCount() {
        return stationCount;
    }

    public void setStationCount(int stationCount) {
        this.stationCount = stationCount;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public String getRouteKey() {
        return sourceStationCode + " -> " + destinationStationCode;
    }

    @Override
    public String toString() {
        return getRouteKey();
    }
}