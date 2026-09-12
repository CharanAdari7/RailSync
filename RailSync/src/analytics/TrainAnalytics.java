package analytics;

import java.util.HashMap;
import java.util.Map;

import data.DataStore;
import domain.ServiceStatus;
import domain.Train;

public class TrainAnalytics {

    private final DataStore dataStore;

    public TrainAnalytics(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null."
            );
        }

        this.dataStore = dataStore;
    }

    public int getTotalTrains() {

        return dataStore.getTrainCount();
    }

    public int getActiveTrains() {

        int count = 0;

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            if (train.getStatus() == ServiceStatus.ACTIVE) {
                count++;
            }
        }

        return count;
    }

    public int getMaintenanceTrains() {

        int count = 0;

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            if (train.getStatus() == ServiceStatus.MAINTENANCE) {
                count++;
            }
        }

        return count;
    }

    public Map<String, Integer> getTrainsByType() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            String type = train.getTrainType();

            if (type == null || type.trim().isEmpty()) {
                type = "Unknown";
            }

            result.put(
                    type,
                    result.getOrDefault(type, 0) + 1
            );
        }

        return result;
    }

    public Map<ServiceStatus, Integer> getTrainsByStatus() {

        Map<ServiceStatus, Integer> result =
                new HashMap<>();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            ServiceStatus status =
                    train.getStatus();

            if (status == null) {
                status = ServiceStatus.ACTIVE;
            }

            result.put(
                    status,
                    result.getOrDefault(status, 0) + 1
            );
        }

        return result;
    }

    public Map<String, Integer> getTrainsBySourceStation() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            String station =
                    train.getSourceStationCode();

            if (station == null || station.trim().isEmpty()) {
                station = "Unknown";
            }

            result.put(
                    station,
                    result.getOrDefault(station, 0) + 1
            );
        }

        return result;
    }

    public Map<String, Integer> getTrainsByDestinationStation() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            String station =
                    train.getDestinationStationCode();

            if (station == null || station.trim().isEmpty()) {
                station = "Unknown";
            }

            result.put(
                    station,
                    result.getOrDefault(station, 0) + 1
            );
        }

        return result;
    }

    public int getTrainCountForType(String trainType) {

        if (trainType == null
                || trainType.trim().isEmpty()) {

            return 0;
        }

        int count = 0;

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            if (trainType.trim().equalsIgnoreCase(
                    train.getTrainType())) {

                count++;
            }
        }

        return count;
    }

    public int getTrainCountForRoute(
            String sourceStationCode,
            String destinationStationCode) {

        if (sourceStationCode == null
                || destinationStationCode == null) {

            return 0;
        }

        int count = 0;

        String source =
                sourceStationCode.trim().toUpperCase();

        String destination =
                destinationStationCode.trim().toUpperCase();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            String trainSource =
                    train.getSourceStationCode();

            String trainDestination =
                    train.getDestinationStationCode();

            if (source.equalsIgnoreCase(trainSource)
                    && destination.equalsIgnoreCase(trainDestination)) {

                count++;
            }
        }

        return count;
    }

    public Train findTrain(String trainNumber) {

        if (trainNumber == null
                || trainNumber.trim().isEmpty()) {

            return null;
        }

        String number = trainNumber.trim();

        for (Train train : dataStore.getAllTrains()) {

            if (train == null) {
                continue;
            }

            if (number.equalsIgnoreCase(
                    train.getTrainNumber())) {

                return train;
            }
        }

        return null;
    }

    public double getAverageTrainTypeCount() {

        int totalTrains =
                getTotalTrains();

        if (totalTrains == 0) {
            return 0.0;
        }

        return (double) totalTrains
                / getTrainsByType().size();
    }
    /** Total coaches currently stored for all trains. */
    public int getTotalCoaches() {
        return dataStore.getCoachCount();
    }

    /** Total seat capacity represented by the stored coaches. */
    public int getTotalCapacity() {
        int total = 0;
        for (domain.Coach coach : dataStore.getAllCoaches()) {
            if (coach != null) {
                total += coach.getCapacity();
            }
        }
        return total;
    }

    public double getAverageCapacity() {
        int count = getTotalTrains();
        return count == 0 ? 0.0 : (double) getTotalCapacity() / count;
    }

}