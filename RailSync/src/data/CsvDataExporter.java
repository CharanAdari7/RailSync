package data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import domain.Passenger;
import domain.Route;
import domain.Schedule;
import domain.Station;
import domain.Train;

public class CsvDataExporter {

    // =========================================================
    // TRAINS
    // =========================================================

    public void exportTrains(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            /*
             * Train identity = trainNumber
             *
             * No artificial trainId.
             */
            writer.write(
                    "trainNumber,trainName,sourceStationCode,"
                    + "destinationStationCode,trainType,status"
            );

            writer.newLine();

            for (Train train : dataStore.getAllTrains()) {

                writer.write(
                        csv(train.getTrainNumber()) + ","
                        + csv(train.getTrainName()) + ","
                        + csv(train.getSourceStationCode()) + ","
                        + csv(train.getDestinationStationCode()) + ","
                        + csv(train.getTrainType()) + ","
                        + csv(train.getStatus().name())
                );

                writer.newLine();
            }
        }
    }


    // =========================================================
    // STATIONS
    // =========================================================

    public void exportStations(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            /*
             * Station identity = stationCode
             *
             * No artificial stationId.
             */
            writer.write(
                    "stationCode,stationName,city,state,"
                    + "latitude,longitude,platformCount,status"
            );

            writer.newLine();

            for (Station station :
                    dataStore.getAllStations()) {

                writer.write(
                        csv(station.getStationCode()) + ","
                        + csv(station.getStationName()) + ","
                        + csv(station.getCity()) + ","
                        + csv(station.getState()) + ","
                        + station.getLatitude() + ","
                        + station.getLongitude() + ","
                        + station.getPlatformCount() + ","
                        + csv(station.getStatus().name())
                );

                writer.newLine();
            }
        }
    }


    // =========================================================
    // ROUTES
    // =========================================================

    public void exportRoutes(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            /*
             * Route identity is represented by:
             *
             * sourceStationCode -> destinationStationCode
             *
             * No artificial routeId.
             */
            writer.write(
                    "routeName,sourceStationCode,"
                    + "destinationStationCode,distanceKm,"
                    + "stationCount,status"
            );

            writer.newLine();

            for (Route route :
                    dataStore.getAllRoutes()) {

                writer.write(
                        csv(route.getRouteName()) + ","
                        + csv(route.getSourceStationCode()) + ","
                        + csv(route.getDestinationStationCode()) + ","
                        + route.getDistanceKm() + ","
                        + route.getStationCount() + ","
                        + csv(route.getStatus().name())
                );

                writer.newLine();
            }
        }
    }


    // =========================================================
    // PASSENGERS
    // =========================================================

    public void exportPassengers(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            writer.write(
                    "passengerId,passengerName,age,"
                    + "gender,phone,email"
            );

            writer.newLine();

            for (Passenger passenger :
                    dataStore.getAllPassengers()) {

                writer.write(
                        csv(passenger.getPassengerId()) + ","
                        + csv(passenger.getPassengerName()) + ","
                        + passenger.getAge() + ","
                        + csv(passenger.getGender()) + ","
                        + csv(passenger.getPhone()) + ","
                        + csv(passenger.getEmail())
                );

                writer.newLine();
            }
        }
    }


    // =========================================================
    // SCHEDULES
    // =========================================================

    /*
     * IMPORTANT:
     *
     * Schedule has NOT been redesigned yet.
     * Therefore this method intentionally keeps the
     * existing Schedule getters from your current project.
     *
     * Once Schedule is redesigned around:
     *
     * trainNumber
     * sourceStationCode
     * destinationStationCode
     *
     * this method will be updated together with Schedule.
     */

    public void exportSchedules(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            writer.write(
                    "scheduleId,trainId,routeId,operatingDate,"
                    + "departureTime,arrivalTime,"
                    + "sourceStationId,destinationStationId,status"
            );

            writer.newLine();

            for (Schedule schedule :
                    dataStore.getAllSchedules()) {

                writer.write(
                        csv(schedule.getScheduleId()) + ","
                        + csv(schedule.getTrainId()) + ","
                        + csv(schedule.getRouteId()) + ","
                        + schedule.getOperatingDate() + ","
                        + schedule.getDepartureTime() + ","
                        + schedule.getArrivalTime() + ","
                        + csv(schedule.getSourceStationId()) + ","
                        + csv(schedule.getDestinationStationId()) + ","
                        + csv(schedule.getStatus().name())
                );

                writer.newLine();
            }
        }
    }


    // =========================================================
    // SUMMARY
    // =========================================================

    public void exportSummary(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            writer.write("RailSync Data Summary");
            writer.newLine();

            writer.write(
                    "Generated,"
                    + csv(LocalDateTime.now().toString())
            );

            writer.newLine();
            writer.newLine();

            writer.write("Entity,Count");
            writer.newLine();

            writer.write(
                    "Trains,"
                    + dataStore.getTrainCount()
            );
            writer.newLine();

            writer.write(
                    "Stations,"
                    + dataStore.getStationCount()
            );
            writer.newLine();

            writer.write(
                    "Routes,"
                    + dataStore.getRouteCount()
            );
            writer.newLine();

            writer.write(
                    "Schedules,"
                    + dataStore.getScheduleCount()
            );
            writer.newLine();

            writer.write(
                    "Passengers,"
                    + dataStore.getPassengerCount()
            );
            writer.newLine();

            writer.write(
                    "Tickets,"
                    + dataStore.getTicketCount()
            );
            writer.newLine();

            writer.write(
                    "Bookings,"
                    + dataStore.getBookingCount()
            );
            writer.newLine();

            writer.write(
                    "Services,"
                    + dataStore.getServiceCount()
            );
            writer.newLine();

            writer.write(
                    "Maintenance,"
                    + dataStore.getMaintenanceCount()
            );
            writer.newLine();

            writer.write(
                    "Alerts,"
                    + dataStore.getAlertCount()
            );
            writer.newLine();

            writer.write(
                    "Events,"
                    + dataStore.getEventCount()
            );
            writer.newLine();

            writer.write(
                    "Platforms,"
                    + dataStore.getPlatformCount()
            );
            writer.newLine();

            writer.write(
                    "Coaches,"
                    + dataStore.getCoachCount()
            );
            writer.newLine();

            writer.write(
                    "Staff,"
                    + dataStore.getStaffCount()
            );
            writer.newLine();
        }
    }


    // =========================================================
    // CSV ESCAPING
    // =========================================================

    private String csv(String value) {

        if (value == null) {
            return "";
        }

        String escaped =
                value.replace("\"", "\"\"");

        if (escaped.contains(",")
                || escaped.contains("\"")
                || escaped.contains("\n")
                || escaped.contains("\r")) {

            return "\"" + escaped + "\"";
        }

        return escaped;
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    private void validate(
            Path file,
            DataStore dataStore) {

        if (file == null) {
            throw new IllegalArgumentException(
                    "File cannot be null.");
        }

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }
    }
}