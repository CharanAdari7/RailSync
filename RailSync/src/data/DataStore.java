package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import domain.Alert;
import domain.Booking;
import domain.Coach;
import domain.MaintenanceRecord;
import domain.Passenger;
import domain.Platform;
import domain.RailwayEvent;
import domain.RailwayService;
import domain.Route;
import domain.Schedule;
import domain.Staff;
import domain.Station;
import domain.Ticket;
import domain.Train;

public class DataStore {

    /*
     * ============================================================
     * MAIN DATA COLLECTIONS
     * ============================================================
     */

    private final Map<String, Train> trains;
    private final Map<String, Station> stations;
    private final Map<String, Route> routes;
    private final Map<String, Schedule> schedules;

    private final Map<String, Passenger> passengers;
    private final Map<String, Ticket> tickets;
    private final Map<String, Booking> bookings;

    private final Map<String, RailwayService> services;
    private final Map<String, MaintenanceRecord> maintenanceRecords;
    private final Map<String, Alert> alerts;
    private final Map<String, RailwayEvent> events;

    private final Map<String, Platform> platforms;
    private final Map<String, Coach> coaches;
    private final Map<String, Staff> staff;


    /*
     * ============================================================
     * CONSTRUCTOR
     * ============================================================
     */

    public DataStore() {

        trains = new LinkedHashMap<>();
        stations = new LinkedHashMap<>();
        routes = new LinkedHashMap<>();
        schedules = new LinkedHashMap<>();

        passengers = new LinkedHashMap<>();
        tickets = new LinkedHashMap<>();
        bookings = new LinkedHashMap<>();

        services = new LinkedHashMap<>();
        maintenanceRecords = new LinkedHashMap<>();
        alerts = new LinkedHashMap<>();
        events = new LinkedHashMap<>();

        platforms = new LinkedHashMap<>();
        coaches = new LinkedHashMap<>();
        staff = new LinkedHashMap<>();
    }


    /*
     * ============================================================
     * TRAIN
     *
     * Business identity:
     * TRAIN NUMBER
     * ============================================================
     */

    public void saveTrain(Train train) {

        if (train == null) {
            throw new IllegalArgumentException(
                    "Train cannot be null.");
        }

        if (train.getTrainNumber() == null
                || train.getTrainNumber().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Train number is required.");
        }

        trains.put(
                train.getTrainNumber().trim(),
                train
        );
    }

    public void addTrain(Train train) {
        saveTrain(train);
    }

    public void updateTrain(Train train) {
        saveTrain(train);
    }

    public Train getTrain(String trainNumber) {

        if (trainNumber == null) {
            return null;
        }

        return trains.get(trainNumber.trim());
    }

    public Train findTrain(String trainNumber) {
        return getTrain(trainNumber);
    }

    public void deleteTrain(String trainNumber) {

        if (trainNumber == null) {
            return;
        }

        trains.remove(trainNumber.trim());
    }

    public void removeTrain(String trainNumber) {
        deleteTrain(trainNumber);
    }

    public List<Train> getAllTrains() {
        return new ArrayList<>(trains.values());
    }

    public int getTrainCount() {
        return trains.size();
    }


    /*
     * ============================================================
     * STATION
     *
     * Business identity:
     * STATION CODE
     * ============================================================
     */

    public void saveStation(Station station) {

        if (station == null) {
            throw new IllegalArgumentException(
                    "Station cannot be null.");
        }

        if (station.getStationCode() == null
                || station.getStationCode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Station code is required.");
        }

        String code =
                station.getStationCode()
                       .trim()
                       .toUpperCase();

        stations.put(code, station);
    }

    public void addStation(Station station) {
        saveStation(station);
    }

    public void updateStation(Station station) {
        saveStation(station);
    }

    public Station getStation(String stationCode) {

        if (stationCode == null) {
            return null;
        }

        return stations.get(
                stationCode.trim().toUpperCase()
        );
    }

    public Station findStation(String stationCode) {
        return getStation(stationCode);
    }

    public void deleteStation(String stationCode) {

        if (stationCode == null) {
            return;
        }

        stations.remove(
                stationCode.trim().toUpperCase()
        );
    }

    public void removeStation(String stationCode) {
        deleteStation(stationCode);
    }

    public List<Station> getAllStations() {
        return new ArrayList<>(stations.values());
    }

    public int getStationCount() {
        return stations.size();
    }


    /*
     * ============================================================
     * ROUTE
     *
     * Business identity:
     * SOURCE STATION CODE -> DESTINATION STATION CODE
     * ============================================================
     */

    public void saveRoute(String key, Route route) {

        if (route == null) {
            throw new IllegalArgumentException(
                    "Route cannot be null.");
        }

        if (key == null || key.trim().isEmpty()) {

            key = createRouteKey(route);
        }

        routes.put(
                key.trim().toUpperCase(),
                route
        );
    }

    public void addRoute(Route route) {

        if (route == null) {
            throw new IllegalArgumentException(
                    "Route cannot be null.");
        }

        saveRoute(createRouteKey(route), route);
    }

    public void updateRoute(Route route) {

        if (route == null) {
            throw new IllegalArgumentException(
                    "Route cannot be null.");
        }

        saveRoute(createRouteKey(route), route);
    }

    public Route getRoute(String key) {

        if (key == null) {
            return null;
        }

        return routes.get(
                key.trim().toUpperCase()
        );
    }

    public Route findRoute(String key) {
        return getRoute(key);
    }

    public void deleteRoute(String key) {

        if (key == null) {
            return;
        }

        routes.remove(
                key.trim().toUpperCase()
        );
    }

    public void removeRoute(String key) {
        deleteRoute(key);
    }

    public List<Route> getAllRoutes() {
        return new ArrayList<>(routes.values());
    }

    public int getRouteCount() {
        return routes.size();
    }

    private String createRouteKey(Route route) {

        String source =
                route.getSourceStationCode() == null
                        ? ""
                        : route.getSourceStationCode()
                               .trim()
                               .toUpperCase();

        String destination =
                route.getDestinationStationCode() == null
                        ? ""
                        : route.getDestinationStationCode()
                               .trim()
                               .toUpperCase();

        return source + "->" + destination;
    }


    /*
     * ============================================================
     * SCHEDULE
     * ============================================================
     */

    public void addSchedule(Schedule schedule) {

        if (schedule == null) {
            throw new IllegalArgumentException(
                    "Schedule cannot be null.");
        }

        String key = getScheduleKey(schedule);

        schedules.put(key, schedule);
    }

    public void saveSchedule(Schedule schedule) {
        addSchedule(schedule);
    }

    public void updateSchedule(Schedule schedule) {
        addSchedule(schedule);
    }

    public Schedule getSchedule(String scheduleId) {

        if (scheduleId == null) {
            return null;
        }

        return schedules.get(scheduleId.trim());
    }

    public Schedule findSchedule(String scheduleId) {
        return getSchedule(scheduleId);
    }

    public void deleteSchedule(String scheduleId) {

        if (scheduleId == null) {
            return;
        }

        schedules.remove(scheduleId.trim());
    }

    public void removeSchedule(String scheduleId) {
        deleteSchedule(scheduleId);
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules.values());
    }

    public int getScheduleCount() {
        return schedules.size();
    }

    private String getScheduleKey(Schedule schedule) {

        /*
         * Schedule models can vary.
         *
         * We first use schedule ID through reflection-free
         * compatibility by using toString when necessary.
         *
         * If your Schedule has getScheduleId(), the service
         * can still use the same stored object.
         */

        String key = schedule.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "SCHEDULE-" + schedules.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * PASSENGER
     * ============================================================
     */

    public void addPassenger(Passenger passenger) {

        if (passenger == null) {
            throw new IllegalArgumentException(
                    "Passenger cannot be null.");
        }

        passengers.put(
                getPassengerKey(passenger),
                passenger
        );
    }

    public void savePassenger(Passenger passenger) {
        addPassenger(passenger);
    }

    public void updatePassenger(Passenger passenger) {
        addPassenger(passenger);
    }

    public Passenger getPassenger(String passengerId) {

        if (passengerId == null) {
            return null;
        }

        return passengers.get(passengerId.trim());
    }

    public void deletePassenger(String passengerId) {

        if (passengerId == null) {
            return;
        }

        passengers.remove(passengerId.trim());
    }

    public void removePassenger(String passengerId) {
        deletePassenger(passengerId);
    }

    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengers.values());
    }

    public int getPassengerCount() {
        return passengers.size();
    }

    private String getPassengerKey(Passenger passenger) {

        String key = passenger.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "PASSENGER-" + passengers.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * TICKET
     * ============================================================
     */

    public void addTicket(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Ticket cannot be null.");
        }

        tickets.put(
                getTicketKey(ticket),
                ticket
        );
    }

    public void saveTicket(Ticket ticket) {
        addTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {
        addTicket(ticket);
    }

    public Ticket getTicket(String ticketId) {

        if (ticketId == null) {
            return null;
        }

        return tickets.get(ticketId.trim());
    }

    public void deleteTicket(String ticketId) {

        if (ticketId == null) {
            return;
        }

        tickets.remove(ticketId.trim());
    }

    public void removeTicket(String ticketId) {
        deleteTicket(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets.values());
    }

    public int getTicketCount() {
        return tickets.size();
    }

    private String getTicketKey(Ticket ticket) {

        String key = ticket.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "TICKET-" + tickets.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * BOOKING
     * ============================================================
     */

    public void addBooking(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking cannot be null.");
        }

        bookings.put(
                getBookingKey(booking),
                booking
        );
    }

    public void saveBooking(Booking booking) {
        addBooking(booking);
    }

    public void updateBooking(Booking booking) {
        addBooking(booking);
    }

    public Booking getBooking(String bookingId) {

        if (bookingId == null) {
            return null;
        }

        return bookings.get(bookingId.trim());
    }

    public void deleteBooking(String bookingId) {

        if (bookingId == null) {
            return;
        }

        bookings.remove(bookingId.trim());
    }

    public void removeBooking(String bookingId) {
        deleteBooking(bookingId);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    public int getBookingCount() {
        return bookings.size();
    }

    private String getBookingKey(Booking booking) {

        String key = booking.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "BOOKING-" + bookings.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * RAILWAY SERVICE
     * ============================================================
     */

    public void addService(RailwayService service) {

        if (service == null) {
            throw new IllegalArgumentException(
                    "Service cannot be null.");
        }

        services.put(
                getServiceKey(service),
                service
        );
    }

    public void saveService(RailwayService service) {
        addService(service);
    }

    public void updateService(RailwayService service) {
        addService(service);
    }

    public RailwayService getService(String serviceId) {

        if (serviceId == null) {
            return null;
        }

        return services.get(serviceId.trim());
    }

    public void deleteService(String serviceId) {

        if (serviceId == null) {
            return;
        }

        services.remove(serviceId.trim());
    }

    public void removeService(String serviceId) {
        deleteService(serviceId);
    }

    public List<RailwayService> getAllServices() {
        return new ArrayList<>(services.values());
    }

    public int getServiceCount() {
        return services.size();
    }

    private String getServiceKey(RailwayService service) {

        String key = service.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "SERVICE-" + services.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * MAINTENANCE RECORD
     * ============================================================
     */

    public void addMaintenanceRecord(
            MaintenanceRecord record) {

        if (record == null) {
            throw new IllegalArgumentException(
                    "Maintenance record cannot be null.");
        }

        maintenanceRecords.put(
                getMaintenanceKey(record),
                record
        );
    }

    public void saveMaintenanceRecord(
            MaintenanceRecord record) {

        addMaintenanceRecord(record);
    }

    public void updateMaintenanceRecord(
            MaintenanceRecord record) {

        addMaintenanceRecord(record);
    }

    public MaintenanceRecord getMaintenanceRecord(
            String maintenanceId) {

        if (maintenanceId == null) {
            return null;
        }

        return maintenanceRecords.get(
                maintenanceId.trim()
        );
    }

    public void deleteMaintenanceRecord(
            String maintenanceId) {

        if (maintenanceId == null) {
            return;
        }

        maintenanceRecords.remove(
                maintenanceId.trim()
        );
    }

    public void removeMaintenanceRecord(
            String maintenanceId) {

        deleteMaintenanceRecord(maintenanceId);
    }

    public List<MaintenanceRecord>
    getAllMaintenanceRecords() {

        return new ArrayList<>(
                maintenanceRecords.values()
        );
    }

    public int getMaintenanceRecordCount() {
        return maintenanceRecords.size();
    }

    private String getMaintenanceKey(
            MaintenanceRecord record) {

        String key = record.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "MAINTENANCE-" +
                    maintenanceRecords.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * ALERT
     * ============================================================
     */

    public void addAlert(Alert alert) {

        if (alert == null) {
            throw new IllegalArgumentException(
                    "Alert cannot be null.");
        }

        if (alert.getAlertId() == null
                || alert.getAlertId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Alert ID is required.");
        }

        alerts.put(
                alert.getAlertId().trim(),
                alert
        );
    }

    public void saveAlert(Alert alert) {
        addAlert(alert);
    }

    public void updateAlert(Alert alert) {
        addAlert(alert);
    }

    public Alert getAlert(String alertId) {

        if (alertId == null) {
            return null;
        }

        return alerts.get(alertId.trim());
    }

    public void deleteAlert(String alertId) {

        if (alertId == null) {
            return;
        }

        alerts.remove(alertId.trim());
    }

    public void removeAlert(String alertId) {
        deleteAlert(alertId);
    }

    public List<Alert> getAllAlerts() {
        return new ArrayList<>(alerts.values());
    }

    public int getAlertCount() {
        return alerts.size();
    }


    /*
     * ============================================================
     * RAILWAY EVENT
     * ============================================================
     */

    public void addEvent(RailwayEvent event) {

        if (event == null) {
            throw new IllegalArgumentException(
                    "Event cannot be null.");
        }

        events.put(
                getEventKey(event),
                event
        );
    }

    public void saveEvent(RailwayEvent event) {
        addEvent(event);
    }

    public void updateEvent(RailwayEvent event) {
        addEvent(event);
    }

    public RailwayEvent getEvent(String eventId) {

        if (eventId == null) {
            return null;
        }

        return events.get(eventId.trim());
    }

    public void deleteEvent(String eventId) {

        if (eventId == null) {
            return;
        }

        events.remove(eventId.trim());
    }

    public void removeEvent(String eventId) {
        deleteEvent(eventId);
    }

    public List<RailwayEvent> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    public int getEventCount() {
        return events.size();
    }

    private String getEventKey(RailwayEvent event) {

        String key = event.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "EVENT-" + events.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * PLATFORM
     * ============================================================
     */

    public void addPlatform(Platform platform) {

        if (platform == null) {
            throw new IllegalArgumentException(
                    "Platform cannot be null.");
        }

        platforms.put(
                getPlatformKey(platform),
                platform
        );
    }

    public void savePlatform(Platform platform) {
        addPlatform(platform);
    }

    public Platform getPlatform(String platformId) {

        if (platformId == null) {
            return null;
        }

        return platforms.get(platformId.trim());
    }

    public void deletePlatform(String platformId) {

        if (platformId == null) {
            return;
        }

        platforms.remove(platformId.trim());
    }

    public void removePlatform(String platformId) {
        deletePlatform(platformId);
    }

    public List<Platform> getAllPlatforms() {
        return new ArrayList<>(platforms.values());
    }

    public int getPlatformCount() {
        return platforms.size();
    }

    private String getPlatformKey(Platform platform) {

        String key = platform.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "PLATFORM-" + platforms.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * COACH
     * ============================================================
     */

    public void addCoach(Coach coach) {

        if (coach == null) {
            throw new IllegalArgumentException(
                    "Coach cannot be null.");
        }

        coaches.put(
                getCoachKey(coach),
                coach
        );
    }

    public void saveCoach(Coach coach) {
        addCoach(coach);
    }

    public Coach getCoach(String coachId) {

        if (coachId == null) {
            return null;
        }

        return coaches.get(coachId.trim());
    }

    public void deleteCoach(String coachId) {

        if (coachId == null) {
            return;
        }

        coaches.remove(coachId.trim());
    }

    public void removeCoach(String coachId) {
        deleteCoach(coachId);
    }

    public List<Coach> getAllCoaches() {
        return new ArrayList<>(coaches.values());
    }

    public int getCoachCount() {
        return coaches.size();
    }

    private String getCoachKey(Coach coach) {

        String key = coach.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "COACH-" + coaches.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * STAFF
     * ============================================================
     */

    public void addStaff(Staff member) {

        if (member == null) {
            throw new IllegalArgumentException(
                    "Staff member cannot be null.");
        }

        staff.put(
                getStaffKey(member),
                member
        );
    }

    public void saveStaff(Staff member) {
        addStaff(member);
    }

    public Staff getStaff(String staffId) {

        if (staffId == null) {
            return null;
        }

        return staff.get(staffId.trim());
    }

    public void deleteStaff(String staffId) {

        if (staffId == null) {
            return;
        }

        staff.remove(staffId.trim());
    }

    public void removeStaff(String staffId) {
        deleteStaff(staffId);
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }

    public int getStaffCount() {
        return staff.size();
    }

    private String getStaffKey(Staff member) {

        String key = member.toString();

        if (key == null || key.trim().isEmpty()) {
            key = "STAFF-" + staff.size();
        }

        return key.trim();
    }


    /*
     * ============================================================
     * GENERIC COUNTS
     * ============================================================
     */

    public int getTotalRecordCount() {

        return trains.size()
                + stations.size()
                + routes.size()
                + schedules.size()
                + passengers.size()
                + tickets.size()
                + bookings.size()
                + services.size()
                + maintenanceRecords.size()
                + alerts.size()
                + events.size()
                + platforms.size()
                + coaches.size()
                + staff.size();
    }


    /*
     * ============================================================
     * EMPTY CHECK
     * ============================================================
     */

    public boolean isEmpty() {
        return getTotalRecordCount() == 0;
    }


    /*
     * ============================================================
     * CLEAR METHODS
     * ============================================================
     */

    public void clearTrains() {
        trains.clear();
    }

    public void clearStations() {
        stations.clear();
    }

    public void clearRoutes() {
        routes.clear();
    }

    public void clearSchedules() {
        schedules.clear();
    }

    public void clearPassengers() {
        passengers.clear();
    }

    public void clearTickets() {
        tickets.clear();
    }

    public void clearBookings() {
        bookings.clear();
    }

    public void clearServices() {
        services.clear();
    }

    public void clearMaintenanceRecords() {
        maintenanceRecords.clear();
    }

    public void clearAlerts() {
        alerts.clear();
    }

    public void clearEvents() {
        events.clear();
    }

    public void clearPlatforms() {
        platforms.clear();
    }

    public void clearCoaches() {
        coaches.clear();
    }

    public void clearStaff() {
        staff.clear();
    }


    /*
     * ============================================================
     * CLEAR EVERYTHING
     * ============================================================
     */

    public void clearAll() {

        trains.clear();
        stations.clear();
        routes.clear();
        schedules.clear();

        passengers.clear();
        tickets.clear();
        bookings.clear();

        services.clear();
        maintenanceRecords.clear();
        alerts.clear();
        events.clear();

        platforms.clear();
        coaches.clear();
        staff.clear();
    }


    /*
     * ============================================================
     * COLLECTION ACCESS
     *
     * Useful for backup/export/analytics.
     * ============================================================
     */

    public Collection<Train> trainValues() {
        return trains.values();
    }

    public Collection<Station> stationValues() {
        return stations.values();
    }

    public Collection<Route> routeValues() {
        return routes.values();
    }

    public Collection<Schedule> scheduleValues() {
        return schedules.values();
    }

    public Collection<Passenger> passengerValues() {
        return passengers.values();
    }

    public Collection<Ticket> ticketValues() {
        return tickets.values();
    }

    public Collection<Booking> bookingValues() {
        return bookings.values();
    }

    public Collection<RailwayService> serviceValues() {
        return services.values();
    }

    public Collection<MaintenanceRecord>
    maintenanceRecordValues() {

        return maintenanceRecords.values();
    }

    public Collection<Alert> alertValues() {
        return alerts.values();
    }

    public Collection<RailwayEvent> eventValues() {
        return events.values();
    }

    public Collection<Platform> platformValues() {
        return platforms.values();
    }

    public Collection<Coach> coachValues() {
        return coaches.values();
    }

    public Collection<Staff> staffValues() {
        return staff.values();
    }


    /*
     * ============================================================
     * SNAPSHOT
     * ============================================================
     */

    public Map<String, Integer> getRecordCounts() {

        Map<String, Integer> result =
                new LinkedHashMap<>();

        result.put("Trains", trains.size());
        result.put("Stations", stations.size());
        result.put("Routes", routes.size());
        result.put("Schedules", schedules.size());

        result.put("Passengers", passengers.size());
        result.put("Tickets", tickets.size());
        result.put("Bookings", bookings.size());

        result.put("Services", services.size());
        result.put(
                "Maintenance",
                maintenanceRecords.size()
        );

        result.put("Alerts", alerts.size());
        result.put("Events", events.size());

        result.put("Platforms", platforms.size());
        result.put("Coaches", coaches.size());
        result.put("Staff", staff.size());

        return result;
    }
    // Compatibility overloads used by CSV importers.
    public void savePassenger(String key, Passenger passenger) {
        if (passenger == null) throw new IllegalArgumentException("Passenger cannot be null.");
        if (key == null || key.trim().isEmpty()) { addPassenger(passenger); return; }
        passengers.put(key.trim(), passenger);
    }

    public void saveSchedule(String key, Schedule schedule) {
        if (schedule == null) throw new IllegalArgumentException("Schedule cannot be null.");
        if (key == null || key.trim().isEmpty()) { addSchedule(schedule); return; }
        schedules.put(key.trim(), schedule);
    }

    public int getMaintenanceCount() {
        return maintenanceRecords.size();
    }

}