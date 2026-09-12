package services;

import data.DataStore;
import data.IndianRailApiClient;
import domain.ServiceStatus;
import domain.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainService {

    private final DataStore dataStore;
    private final IndianRailApiClient apiClient;

    public TrainService(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
        this.apiClient = new IndianRailApiClient();
    }

    public void addTrain(Train train) {

        validate(train);

        if (dataStore.getTrain(
                train.getTrainNumber()) != null) {

            throw new IllegalArgumentException(
                    "Train already exists: "
                            + train.getTrainNumber());
        }

        validateStations(train);

        dataStore.saveTrain(train);
    }

    public void updateTrain(Train train) {

        validate(train);
        validateStations(train);

        if (dataStore.getTrain(
                train.getTrainNumber()) == null) {

            throw new IllegalArgumentException(
                    "Train not found: "
                            + train.getTrainNumber());
        }

        dataStore.saveTrain(train);
    }

    public void deleteTrain(String trainNumber) {

        if (trainNumber == null) {
            return;
        }

        dataStore.deleteTrain(
                trainNumber.trim());
    }

    public Train findByNumber(String trainNumber) {

        if (trainNumber == null
                || trainNumber.trim().isEmpty()) {
            return null;
        }

        return dataStore.getTrain(
                trainNumber.trim());
    }

    public List<Train> getAllTrains() {
        return dataStore.getAllTrains();
    }

    public List<Train> search(String keyword) {

        List<Train> result = new ArrayList<>();

        if (keyword == null) {
            return result;
        }

        String value =
                keyword.trim().toLowerCase();

        for (Train train : getAllTrains()) {

            if (train.getTrainNumber()
                    .toLowerCase()
                    .contains(value)
                    || train.getTrainName()
                    .toLowerCase()
                    .contains(value)
                    || train.getSourceStationCode()
                    .toLowerCase()
                    .contains(value)
                    || train.getDestinationStationCode()
                    .toLowerCase()
                    .contains(value)) {

                result.add(train);
            }
        }

        return result;
    }

    public void changeStatus(
            String trainNumber,
            ServiceStatus status) {

        Train train = findByNumber(trainNumber);

        if (train == null) {
            throw new IllegalArgumentException(
                    "Train not found.");
        }

        train.setStatus(status);

        dataStore.saveTrain(train);
    }

    public int count() {
        return dataStore.getAllTrains().size();
    }

    public String getOnlineTrainInformation(
            String trainNumber) throws Exception {

        return apiClient.getTrainInformation(trainNumber);
    }

    public String getOnlineTrainSchedule(
            String trainNumber) throws Exception {

        return apiClient.getTrainSchedule(trainNumber);
    }

    public String getOnlineTrainsBetween(
            String sourceCode,
            String destinationCode) throws Exception {

        return apiClient.getTrainsBetweenStations(
                sourceCode,
                destinationCode);
    }

    private void validate(Train train) {

        if (train == null) {
            throw new IllegalArgumentException(
                    "Train cannot be null.");
        }

        if (train.getTrainNumber() == null
                || train.getTrainNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Train number is required.");
        }

        if (train.getTrainName() == null
                || train.getTrainName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Train name is required.");
        }
    }

    private void validateStations(Train train) {

        if (train.getSourceStationCode() == null
                || train.getDestinationStationCode() == null) {

            throw new IllegalArgumentException(
                    "Source and destination stations are required.");
        }

        if (train.getSourceStationCode()
                .equalsIgnoreCase(
                        train.getDestinationStationCode())) {

            throw new IllegalArgumentException(
                    "Source and destination cannot be the same.");
        }

        if (dataStore.getStation(
                train.getSourceStationCode()) == null) {

            throw new IllegalArgumentException(
                    "Source station does not exist in RailSync: "
                            + train.getSourceStationCode());
        }

        if (dataStore.getStation(
                train.getDestinationStationCode()) == null) {

            throw new IllegalArgumentException(
                    "Destination station does not exist in RailSync: "
                            + train.getDestinationStationCode());
        }
    }
}