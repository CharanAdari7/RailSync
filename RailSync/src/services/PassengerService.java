package services;

import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.Passenger;

public class PassengerService {

    private final DataStore dataStore;

    public PassengerService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void addPassenger(Passenger passenger) {

        validate(passenger);

        if (dataStore.getPassenger(passenger.getPassengerId()) != null) {
            throw new IllegalArgumentException(
                    "Passenger ID already exists.");
        }

        dataStore.addPassenger(passenger);
    }

    public void updatePassenger(Passenger passenger) {

        validate(passenger);

        if (dataStore.getPassenger(passenger.getPassengerId()) == null) {
            throw new IllegalArgumentException("Passenger not found.");
        }

        dataStore.addPassenger(passenger);
    }

    public void deletePassenger(String passengerId) {

        if (dataStore.getPassenger(passengerId) == null) {
            throw new IllegalArgumentException("Passenger not found.");
        }

        dataStore.removePassenger(passengerId);
    }

    public Passenger findById(String passengerId) {
        return dataStore.getPassenger(passengerId);
    }

    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(dataStore.getAllPassengers());
    }

    public List<Passenger> search(String keyword) {

        List<Passenger> result = new ArrayList<>();

        if (keyword == null) {
            return result;
        }

        String value = keyword.trim().toLowerCase();

        if (value.isEmpty()) {
            return getAllPassengers();
        }

        for (Passenger passenger : dataStore.getAllPassengers()) {

            if (contains(passenger.getPassengerId(), value)
                    || contains(passenger.getPassengerName(), value)
                    || contains(passenger.getPhone(), value)
                    || contains(passenger.getEmail(), value)) {

                result.add(passenger);
            }
        }

        return result;
    }

    public int count() {
        return dataStore.getPassengerCount();
    }

    private void validate(Passenger passenger) {

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger cannot be null.");
        }
    }

    private boolean contains(String text, String keyword) {
        return text != null && text.toLowerCase().contains(keyword);
    }
}