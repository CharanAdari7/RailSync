package domain;

public class Station {

    private String stationCode;
    private String stationName;
    private String city;
    private String state;

    private double latitude;
    private double longitude;

    private int platformCount;

    private ServiceStatus status;

    public Station() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Station(
            String stationCode,
            String stationName,
            String city,
            String state,
            double latitude,
            double longitude,
            int platformCount) {

        setStationCode(stationCode);
        setStationName(stationName);
        setCity(city);
        setState(state);

        this.latitude = latitude;
        this.longitude = longitude;

        setPlatformCount(platformCount);

        this.status = ServiceStatus.ACTIVE;
    }

    public Station(
            String stationCode,
            String stationName,
            String city,
            String state,
            int platformCount) {

        this(
                stationCode,
                stationName,
                city,
                state,
                0.0,
                0.0,
                platformCount
        );
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {

        if (stationCode == null
                || stationCode.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Station code cannot be empty."
            );
        }

        this.stationCode =
                stationCode.trim().toUpperCase();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {

        if (stationName == null
                || stationName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Station name cannot be empty."
            );
        }

        this.stationName = stationName.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city =
                city == null ? "" : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state =
                state == null ? "" : state.trim();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPlatformCount() {
        return platformCount;
    }

    public void setPlatformCount(int platformCount) {

        if (platformCount < 0) {
            throw new IllegalArgumentException(
                    "Platform count cannot be negative."
            );
        }

        this.platformCount = platformCount;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {

        this.status = status == null
                ? ServiceStatus.ACTIVE
                : status;
    }

    @Override
    public String toString() {

        return stationCode
                + " - "
                + stationName;
    }
}