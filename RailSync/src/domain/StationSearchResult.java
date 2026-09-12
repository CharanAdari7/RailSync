package domain;

public class StationSearchResult {

    private String stationCode;
    private String stationName;
    private double latitude;
    private double longitude;
    private String city;

    public StationSearchResult(
            String stationCode,
            String stationName,
            double latitude,
            double longitude) {
        this(stationCode, stationName, latitude, longitude, "");
    }

    public StationSearchResult(
            String stationCode,
            String stationName,
            double latitude,
            double longitude,
            String city) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city == null ? "" : city;
    }

    public String getStationCode() { return stationCode; }
    public String getStationName() { return stationName; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return stationCode + " - " + stationName;
    }
}
