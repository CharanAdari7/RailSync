package analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataStore;
import domain.Passenger;
import domain.Ticket;

public class PassengerAnalytics {

    private final DataStore dataStore;

    public PassengerAnalytics(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    public int getTotalPassengers() {
        return dataStore.getPassengerCount();
    }

    public int getActivePassengerCount() {

        int count = 0;

        for (Passenger passenger :
                dataStore.getAllPassengers()) {

            if (passenger != null) {
                count++;
            }
        }

        return count;
    }

    public Map<String, Integer> getPassengersByGender() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Passenger passenger :
                dataStore.getAllPassengers()) {

            String gender = passenger.getGender();

            if (gender == null || gender.trim().isEmpty()) {
                gender = "Unknown";
            }

            result.put(
                    gender,
                    result.getOrDefault(gender, 0) + 1
            );
        }

        return result;
    }

    public double getAverageAge() {

        List<Passenger> passengers =
                dataStore.getAllPassengers();

        if (passengers.isEmpty()) {
            return 0.0;
        }

        int totalAge = 0;
        int count = 0;

        for (Passenger passenger : passengers) {

            if (passenger != null) {
                totalAge += passenger.getAge();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return (double) totalAge / count;
    }

    public Map<String, Integer> getTicketCountByPassenger() {

        Map<String, Integer> result =
                new HashMap<>();

        for (Ticket ticket :
                dataStore.getAllTickets()) {

            if (ticket == null) {
                continue;
            }

            if (ticket.getStatus()
                    == domain.ServiceStatus.CANCELLED) {
                continue;
            }

            String passengerId =
                    ticket.getPassengerId();

            if (passengerId == null) {
                continue;
            }

            result.put(
                    passengerId,
                    result.getOrDefault(passengerId, 0) + 1
            );
        }

        return result;
    }

    public int getPassengerCountForId(
            String passengerId) {

        if (passengerId == null) {
            return 0;
        }

        int count = 0;

        for (Ticket ticket :
                dataStore.getAllTickets()) {

            if (ticket == null) {
                continue;
            }

            if (ticket.getStatus()
                    == domain.ServiceStatus.CANCELLED) {
                continue;
            }

            if (passengerId.equals(ticket.getPassengerId())) {
                count++;
            }
        }

        return count;
    }
}