package analytics;

import data.DataStore;

/** Builds a report entirely from the records currently stored in RailSync. */
public class OperationalReport {

    private final DataStore dataStore;
    private final PassengerAnalytics passengerAnalytics;
    private final RevenueAnalytics revenueAnalytics;
    private final TrainAnalytics trainAnalytics;
    private final StationAnalytics stationAnalytics;
    private final ServiceAnalytics serviceAnalytics;

    public OperationalReport(DataStore dataStore,
                             PassengerAnalytics passengerAnalytics,
                             RevenueAnalytics revenueAnalytics,
                             TrainAnalytics trainAnalytics,
                             StationAnalytics stationAnalytics,
                             ServiceAnalytics serviceAnalytics) {
        if (dataStore == null || passengerAnalytics == null || revenueAnalytics == null
                || trainAnalytics == null || stationAnalytics == null || serviceAnalytics == null) {
            throw new IllegalArgumentException("Report dependencies cannot be null.");
        }
        this.dataStore = dataStore;
        this.passengerAnalytics = passengerAnalytics;
        this.revenueAnalytics = revenueAnalytics;
        this.trainAnalytics = trainAnalytics;
        this.stationAnalytics = stationAnalytics;
        this.serviceAnalytics = serviceAnalytics;
    }

    public String generateSummary() {
        String nl = System.lineSeparator();
        StringBuilder report = new StringBuilder();
        report.append("RAILSYNC OPERATIONAL REPORT").append(nl);
        report.append("============================").append(nl).append(nl);

        report.append("NETWORK").append(nl);
        report.append("-------").append(nl);
        report.append("Stations: ").append(stationAnalytics.getTotalStations()).append(nl);
        report.append("Platforms: ").append(stationAnalytics.getTotalPlatforms()).append(nl);
        report.append("Trains: ").append(trainAnalytics.getTotalTrains()).append(nl);
        report.append("Routes: ").append(dataStore.getRouteCount()).append(nl);
        report.append("Schedules: ").append(dataStore.getScheduleCount()).append(nl).append(nl);

        report.append("PASSENGER ACTIVITY").append(nl);
        report.append("------------------").append(nl);
        report.append("Registered passengers: ").append(passengerAnalytics.getTotalPassengers()).append(nl);
        report.append("Tickets issued: ").append(dataStore.getTicketCount()).append(nl);
        report.append("Bookings: ").append(dataStore.getBookingCount()).append(nl);
        report.append("Average passenger age: ").append(format(passengerAnalytics.getAverageAge())).append(nl).append(nl);

        report.append("FINANCIAL").append(nl);
        report.append("---------").append(nl);
        report.append("Ticket revenue: ").append(formatMoney(revenueAnalytics.getTotalTicketRevenue())).append(nl);
        report.append("Booking revenue: ").append(formatMoney(revenueAnalytics.getTotalBookingRevenue())).append(nl);
        report.append("Average ticket fare: ").append(formatMoney(revenueAnalytics.getAverageTicketFare())).append(nl).append(nl);

        report.append("FLEET").append(nl);
        report.append("-----").append(nl);
        report.append("Active trains: ").append(trainAnalytics.getActiveTrains()).append(nl);
        report.append("Trains under maintenance: ").append(trainAnalytics.getMaintenanceTrains()).append(nl);
        report.append("Coaches: ").append(trainAnalytics.getTotalCoaches()).append(nl);
        report.append("Coach capacity: ").append(trainAnalytics.getTotalCapacity()).append(nl);
        report.append("Average capacity per train: ").append(format(trainAnalytics.getAverageCapacity())).append(nl).append(nl);

        report.append("SERVICES").append(nl);
        report.append("--------").append(nl);
        report.append("Total services: ").append(serviceAnalytics.getTotalServices()).append(nl);
        report.append("Active services: ").append(serviceAnalytics.getActiveServices()).append(nl);
        report.append("Maintenance records: ").append(dataStore.getMaintenanceCount()).append(nl);
        report.append("Active alerts: ").append(dataStore.getAlertCount()).append(nl).append(nl);

        report.append("END OF OPERATIONAL REPORT").append(nl);
        return report.toString();
    }

    private String format(double value) {
        return String.format(java.util.Locale.US, "%.2f", value);
    }

    private String formatMoney(double value) {
        return String.format(java.util.Locale.US, "%.2f", value);
    }
}
