package analytics;

import java.util.HashMap;
import java.util.Map;

import data.DataStore;
import domain.Station;

public class StationAnalytics {

    private final DataStore dataStore;

    public StationAnalytics(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    public int getTotalStations() {
        return dataStore.getStationCount();
    }

    public int getTotalPlatforms() {

        int total = 0;

        for (Station station :
                dataStore.getAllStations()) {

            total += station.getPlatformCount();
        }

        return total;
    }

    public double getAveragePlatformsPerStation() {

        int stationCount =
                dataStore.getStationCount();

        if (stationCount == 0) {
            return 0.0;
        }

        return (double) getTotalPlatforms()
                / stationCount;
    }

    public Map<String, Integer> getStationsByState() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Station station :
                dataStore.getAllStations()) {

            String state = station.getState();

            if (state == null || state.trim().isEmpty()) {
                state = "Unknown";
            }

            result.put(
                    state,
                    result.getOrDefault(state, 0) + 1
            );
        }

        return result;
    }

    public Map<String, Integer> getStationsByCity() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Station station :
                dataStore.getAllStations()) {

            String city = station.getCity();

            if (city == null || city.trim().isEmpty()) {
                city = "Unknown";
            }

            result.put(
                    city,
                    result.getOrDefault(city, 0) + 1
            );
        }

        return result;
    }

    public Station findLargestStation() {

        Station largest = null;

        for (Station station :
                dataStore.getAllStations()) {

            if (largest == null
                    || station.getPlatformCount()
                    > largest.getPlatformCount()) {

                largest = station;
            }
        }

        return largest;
    }
}