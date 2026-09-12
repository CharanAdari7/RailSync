package analytics;

import java.util.HashMap;
import java.util.Map;

import data.DataStore;
import domain.Booking;
import domain.MaintenanceRecord;
import domain.ServiceStatus;
import domain.Ticket;

public class RevenueAnalytics {

    private final DataStore dataStore;

    public RevenueAnalytics(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    public double getTotalTicketRevenue() {

        double total = 0.0;

        for (Ticket ticket :
                dataStore.getAllTickets()) {

            if (ticket == null) {
                continue;
            }

            if (ticket.getStatus()
                    == ServiceStatus.CANCELLED) {
                continue;
            }

            total += ticket.getFare();
        }

        return total;
    }

    public double getTotalBookingRevenue() {

        double total = 0.0;

        for (Booking booking :
                dataStore.getAllBookings()) {

            if (booking == null) {
                continue;
            }

            if (booking.getStatus()
                    == ServiceStatus.CANCELLED) {
                continue;
            }

            total += booking.getAmount();
        }

        return total;
    }

    public double getMaintenanceCost() {

        double total = 0.0;

        for (MaintenanceRecord record :
                dataStore.getAllMaintenanceRecords()) {

            if (record != null) {
                total += record.getCost();
            }
        }

        return total;
    }

    public double getNetOperationalValue() {

        return getTotalBookingRevenue()
                - getMaintenanceCost();
    }

    public double getAverageTicketFare() {

        int count = 0;
        double total = 0.0;

        for (Ticket ticket :
                dataStore.getAllTickets()) {

            if (ticket == null) {
                continue;
            }

            if (ticket.getStatus()
                    == ServiceStatus.CANCELLED) {
                continue;
            }

            total += ticket.getFare();
            count++;
        }

        if (count == 0) {
            return 0.0;
        }

        return total / count;
    }

    public Map<String, Double> getRevenueByTicketType() {

        Map<String, Double> result =
                new HashMap<>();

        for (Ticket ticket :
                dataStore.getAllTickets()) {

            if (ticket == null) {
                continue;
            }

            if (ticket.getStatus()
                    == ServiceStatus.CANCELLED) {
                continue;
            }

            String type = ticket.getTicketType();

            if (type == null || type.trim().isEmpty()) {
                type = "Unknown";
            }

            result.put(
                    type,
                    result.getOrDefault(type, 0.0)
                            + ticket.getFare()
            );
        }

        return result;
    }
}