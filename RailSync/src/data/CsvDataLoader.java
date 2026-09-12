package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;

import domain.Passenger;
import domain.Route;
import domain.Schedule;
import domain.Station;
import domain.Train;

public class CsvDataLoader {

    // =========================================================
    // TRAINS
    // =========================================================

    public int loadTrains(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        int count = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;

                    if (line.toLowerCase()
                            .contains("trainnumber")) {
                        continue;
                    }
                }

                String[] values = split(line);

                /*
                 * trainNumber,
                 * trainName,
                 * sourceStationCode,
                 * destinationStationCode,
                 * trainType,
                 * status
                 */
                if (values.length < 5) {
                    continue;
                }

                try {

                    Train train = new Train(
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim(),
                            values[4].trim()
                    );

                    if (values.length >= 6
                            && !values[5].trim().isEmpty()) {

                        try {
                            train.setStatus(
                                    domain.ServiceStatus.valueOf(
                                            values[5]
                                                    .trim()
                                                    .toUpperCase()
                                    )
                            );
                        } catch (IllegalArgumentException ignored) {
                            // Keep default status.
                        }
                    }

                    dataStore.saveTrain(train);
                    count++;

                } catch (RuntimeException ignored) {
                    /*
                     * Ignore malformed records and continue
                     * loading valid records.
                     */
                }
            }
        }

        return count;
    }


    // =========================================================
    // STATIONS
    // =========================================================

    public int loadStations(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        int count = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;

                    if (line.toLowerCase()
                            .contains("stationcode")) {
                        continue;
                    }
                }

                String[] values = split(line);

                /*
                 * stationCode,
                 * stationName,
                 * city,
                 * state,
                 * platformCount,
                 * status
                 */
                if (values.length < 5) {
                    continue;
                }

                try {

                    Station station = new Station(
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            values[3].trim(),
                            Integer.parseInt(
                                    values[4].trim()
                            )
                    );

                    if (values.length >= 6
                            && !values[5].trim().isEmpty()) {

                        try {
                            station.setStatus(
                                    domain.ServiceStatus.valueOf(
                                            values[5]
                                                    .trim()
                                                    .toUpperCase()
                                    )
                            );
                        } catch (IllegalArgumentException ignored) {
                            // Keep default status.
                        }
                    }

                    dataStore.saveStation(station);
                    count++;

                } catch (RuntimeException ignored) {
                    /*
                     * Ignore malformed records.
                     */
                }
            }
        }

        return count;
    }


    // =========================================================
    // ROUTES
    // =========================================================

    public int loadRoutes(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        int count = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;

                    if (line.toLowerCase()
                            .contains("routename")) {
                        continue;
                    }
                }

                String[] values = split(line);

                /*
                 * routeName,
                 * sourceStationCode,
                 * destinationStationCode,
                 * distanceKm,
                 * stationCount,
                 * status
                 */
                if (values.length < 5) {
                    continue;
                }

                try {

                    Route route = new Route(
                            values[0].trim(),
                            values[1].trim(),
                            values[2].trim(),
                            Double.parseDouble(
                                    values[3].trim()
                            ),
                            Integer.parseInt(
                                    values[4].trim()
                            )
                    );

                    if (values.length >= 6
                            && !values[5].trim().isEmpty()) {

                        try {
                            route.setStatus(
                                    domain.ServiceStatus.valueOf(
                                            values[5]
                                                    .trim()
                                                    .toUpperCase()
                                    )
                            );
                        } catch (IllegalArgumentException ignored) {
                            // Keep default status.
                        }
                    }

                    String routeKey =
                            values[1].trim()
                                    .toUpperCase()
                                    + "->"
                                    + values[2].trim()
                                            .toUpperCase();

                    dataStore.saveRoute(
                            routeKey,
                            route
                    );

                    count++;

                } catch (RuntimeException ignored) {
                    /*
                     * Ignore malformed records.
                     */
                }
            }
        }

        return count;
    }


    // =========================================================
    // PASSENGERS
    // =========================================================

    public int loadPassengers(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        int count = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;

                    if (line.toLowerCase()
                            .contains("passengerid")) {
                        continue;
                    }
                }

                String[] values = split(line);

                if (values.length < 6) {
                    continue;
                }

                try {

                    Passenger passenger =
                            new Passenger(
                                    values[0].trim(),
                                    values[1].trim(),
                                    Integer.parseInt(
                                            values[2].trim()
                                    ),
                                    values[3].trim(),
                                    values[4].trim(),
                                    values[5].trim()
                            );

                    dataStore.savePassenger(
                            values[0].trim(),
                            passenger
                    );

                    count++;

                } catch (RuntimeException ignored) {
                    /*
                     * Ignore malformed records.
                     */
                }
            }
        }

        return count;
    }


    // =========================================================
    // SCHEDULES
    // =========================================================

    public int loadSchedules(
            Path file,
            DataStore dataStore) throws IOException {

        validate(file, dataStore);

        int count = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;

                    if (line.toLowerCase()
                            .contains("scheduleid")) {
                        continue;
                    }
                }

                String[] values = split(line);

                /*
                 * Current Schedule structure is retained here
                 * until the Schedule domain model is redesigned.
                 */
                if (values.length < 8) {
                    continue;
                }

                try {

                    Schedule schedule =
                            new Schedule(
                                    values[0].trim(),
                                    values[1].trim(),
                                    values[2].trim(),
                                    LocalDate.parse(
                                            values[3].trim()
                                    ),
                                    LocalTime.parse(
                                            values[4].trim()
                                    ),
                                    LocalTime.parse(
                                            values[5].trim()
                                    ),
                                    values[6].trim(),
                                    values[7].trim()
                            );

                    dataStore.saveSchedule(
                            values[0].trim(),
                            schedule
                    );

                    count++;

                } catch (RuntimeException ignored) {
                    /*
                     * Ignore malformed records.
                     */
                }
            }
        }

        return count;
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    private void validate(
            Path file,
            DataStore dataStore) {

        if (file == null) {
            throw new IllegalArgumentException(
                    "File cannot be null."
            );
        }

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null."
            );
        }

        if (!Files.exists(file)) {
            throw new IllegalArgumentException(
                    "File does not exist: " + file
            );
        }

        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException(
                    "Path is not a file: " + file
            );
        }
    }


    // =========================================================
    // CSV SPLITTER
    // =========================================================

    private String[] split(String line) {

        return line.split(",", -1);
    }
}