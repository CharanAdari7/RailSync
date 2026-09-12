package services;

import data.DataStore;
import data.IndianRailApiClient;
import data.PublicStationCatalog;
import domain.ServiceStatus;
import domain.Station;
import domain.StationSearchResult;

import java.util.ArrayList;
import java.util.List;

public class StationService {

    private final DataStore dataStore;
    private final IndianRailApiClient apiClient;
    private final PublicStationCatalog publicCatalog;

    public StationService(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
        this.apiClient = new IndianRailApiClient();
        this.publicCatalog = new PublicStationCatalog();
    }

    public void addStation(Station station) {

        validateStation(station);

        if (dataStore.getStation(
                station.getStationCode()) != null) {

            throw new IllegalArgumentException(
                    "Station already exists: "
                            + station.getStationCode());
        }

        dataStore.saveStation(station);
    }

    public void updateStation(Station station) {

        validateStation(station);

        if (dataStore.getStation(
                station.getStationCode()) == null) {

            throw new IllegalArgumentException(
                    "Station not found: "
                            + station.getStationCode());
        }

        dataStore.saveStation(station);
    }

    public void deleteStation(String stationCode) {

        if (stationCode == null
                || stationCode.trim().isEmpty()) {
            return;
        }

        dataStore.deleteStation(
                stationCode.trim().toUpperCase());
    }

    public Station findByCode(String stationCode) {

        if (stationCode == null
                || stationCode.trim().isEmpty()) {
            return null;
        }

        return dataStore.getStation(
                stationCode.trim().toUpperCase());
    }

    public List<Station> getAllStations() {
        return dataStore.getAllStations();
    }

    public List<Station> search(String keyword) {

        List<Station> result = new ArrayList<>();

        if (keyword == null) {
            return result;
        }

        String value = keyword.trim().toLowerCase();

        for (Station station : getAllStations()) {

            if (station.getStationCode()
                    .toLowerCase()
                    .contains(value)
                    || station.getStationName()
                    .toLowerCase()
                    .contains(value)
                    || station.getCity()
                    .toLowerCase()
                    .contains(value)) {

                result.add(station);
            }
        }

        return result;
    }

    public void changeStatus(
            String stationCode,
            ServiceStatus status) {

        Station station = findByCode(stationCode);

        if (station == null) {
            throw new IllegalArgumentException(
                    "Station not found: " + stationCode);
        }

        station.setStatus(status);
        dataStore.saveStation(station);
    }

    public int count() {
        return dataStore.getAllStations().size();
    }


    /**
     * Searches the public station master dataset. This does not require an API key.
     */
    public List<StationSearchResult> searchPublicCatalog(String searchText) throws Exception {
        return publicCatalog.search(searchText);
    }

    public Station importPublicCatalogStation(StationSearchResult result) {
        if (result == null || result.getStationCode() == null
                || result.getStationCode().trim().isEmpty()) {
            throw new IllegalArgumentException("A valid station result is required.");
        }

        Station existing = findByCode(result.getStationCode());
        if (existing != null) {
            return existing;
        }

        Station station = new Station(
                result.getStationCode(),
                result.getStationName(),
                result.getCity(),
                "",
                result.getLatitude(),
                result.getLongitude(),
                0);

        addStation(station);
        return station;
    }

    public String getPublicCatalogSource() {
        return publicCatalog.getSourceDescription();
    }

    public List<StationSearchResult> searchOnline(
            String searchText) throws Exception {

        String json =
                apiClient.searchStation(searchText);

        return parseStations(json);
    }

    public List<StationSearchResult> autocompleteOnline(
            String searchText) throws Exception {

        String json =
                apiClient.autocompleteStation(searchText);

        return parseStations(json);
    }

    public Station importOnlineStation(
            StationSearchResult result) {

        Station existing =
                findByCode(result.getStationCode());

        if (existing != null) {
            return existing;
        }

        Station station = new Station(
                result.getStationCode(),
                result.getStationName(),
                result.getStationName(),
                "",
                result.getLatitude(),
                result.getLongitude(),
                0
        );

        addStation(station);

        return station;
    }

    private void validateStation(Station station) {

        if (station == null) {
            throw new IllegalArgumentException(
                    "Station cannot be null.");
        }

        if (station.getStationCode() == null
                || station.getStationCode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Station code is required.");
        }

        if (station.getStationName() == null
                || station.getStationName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Station name is required.");
        }
    }

    private List<StationSearchResult> parseStations(
            String json) {

        List<StationSearchResult> results =
                new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return results;
        }

        /*
         * The API response is intentionally handled here,
         * rather than making the UI understand JSON.
         */

        String stationArray =
                extractArray(json, "Station");

        if (stationArray == null) {
            return results;
        }

        List<String> objects =
                splitObjects(stationArray);

        for (String object : objects) {

            String code =
                    extractString(object, "StationCode");

            String name =
                    extractString(object, "NameEn");

            String latitudeText =
                    extractString(object, "Latitude");

            String longitudeText =
                    extractString(object, "Longitude");

            if (code == null || name == null) {
                continue;
            }

            double latitude =
                    parseDouble(latitudeText);

            double longitude =
                    parseDouble(longitudeText);

            results.add(
                    new StationSearchResult(
                            code,
                            name,
                            latitude,
                            longitude));
        }

        return results;
    }

    private String extractArray(
            String json,
            String key) {

        String marker = "\"" + key + "\":[";

        int start = json.indexOf(marker);

        if (start < 0) {
            return null;
        }

        start += marker.length();

        int depth = 1;

        for (int i = start; i < json.length(); i++) {

            char c = json.charAt(i);

            if (c == '[') {
                depth++;
            } else if (c == ']') {
                depth--;

                if (depth == 0) {
                    return json.substring(start, i);
                }
            }
        }

        return null;
    }

    private List<String> splitObjects(String content) {

        List<String> objects = new ArrayList<>();

        int depth = 0;
        int start = -1;

        for (int i = 0; i < content.length(); i++) {

            char c = content.charAt(i);

            if (c == '{') {

                if (depth == 0) {
                    start = i;
                }

                depth++;

            } else if (c == '}') {

                depth--;

                if (depth == 0 && start >= 0) {

                    objects.add(
                            content.substring(start, i + 1));

                    start = -1;
                }
            }
        }

        return objects;
    }

    private String extractString(
            String object,
            String key) {

        String marker = "\"" + key + "\":\"";

        int start = object.indexOf(marker);

        if (start < 0) {
            return null;
        }

        start += marker.length();

        int end = object.indexOf("\"", start);

        if (end < 0) {
            return null;
        }

        return object.substring(start, end);
    }

    private double parseDouble(String value) {

        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}