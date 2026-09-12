package services;

import data.DataStore;
import domain.Route;
import domain.ServiceStatus;

import java.util.ArrayList;
import java.util.List;

public class RouteService {

    private final DataStore dataStore;

    public RouteService(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    public void addRoute(Route route) {

        validate(route);

        String key = createKey(
                route.getSourceStationCode(),
                route.getDestinationStationCode());

        if (dataStore.getRoute(key) != null) {

            throw new IllegalArgumentException(
                    "Route already exists: " + key);
        }

        validateStations(route);

        dataStore.saveRoute(key, route);
    }

    public void updateRoute(Route route) {

        validate(route);
        validateStations(route);

        String key = createKey(
                route.getSourceStationCode(),
                route.getDestinationStationCode());

        if (dataStore.getRoute(key) == null) {

            throw new IllegalArgumentException(
                    "Route not found: " + key);
        }

        dataStore.saveRoute(key, route);
    }

    public void deleteRoute(
            String sourceStationCode,
            String destinationStationCode) {

        dataStore.deleteRoute(
                createKey(
                        sourceStationCode,
                        destinationStationCode));
    }

    public Route findRoute(
            String sourceStationCode,
            String destinationStationCode) {

        return dataStore.getRoute(
                createKey(
                        sourceStationCode,
                        destinationStationCode));
    }

    public List<Route> getAllRoutes() {
        return dataStore.getAllRoutes();
    }

    public List<Route> search(String keyword) {

        List<Route> result = new ArrayList<>();

        if (keyword == null) {
            return result;
        }

        String value =
                keyword.trim().toLowerCase();

        for (Route route : getAllRoutes()) {

            if (route.getSourceStationCode()
                    .toLowerCase()
                    .contains(value)
                    || route.getDestinationStationCode()
                    .toLowerCase()
                    .contains(value)
                    || (route.getRouteName() != null
                    && route.getRouteName()
                    .toLowerCase()
                    .contains(value))) {

                result.add(route);
            }
        }

        return result;
    }

    public void changeStatus(
            String sourceStationCode,
            String destinationStationCode,
            ServiceStatus status) {

        Route route =
                findRoute(
                        sourceStationCode,
                        destinationStationCode);

        if (route == null) {
            throw new IllegalArgumentException(
                    "Route not found.");
        }

        route.setStatus(status);

        dataStore.saveRoute(
                createKey(
                        sourceStationCode,
                        destinationStationCode),
                route);
    }

    public int count() {
        return dataStore.getAllRoutes().size();
    }

    private String createKey(
            String source,
            String destination) {

        return source.trim().toUpperCase()
                + "->"
                + destination.trim().toUpperCase();
    }

    private void validate(Route route) {

        if (route == null) {
            throw new IllegalArgumentException(
                    "Route cannot be null.");
        }
    }

    private void validateStations(Route route) {

        if (dataStore.getStation(
                route.getSourceStationCode()) == null) {

            throw new IllegalArgumentException(
                    "Source station does not exist.");
        }

        if (dataStore.getStation(
                route.getDestinationStationCode()) == null) {

            throw new IllegalArgumentException(
                    "Destination station does not exist.");
        }
    }
}