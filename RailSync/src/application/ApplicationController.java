package application;

import analytics.AnalyticsService;
import data.DataStore;
import services.AlertService;
import services.BookingService;
import services.EventService;
import services.MaintenanceService;
import services.PassengerService;
import services.RouteService;
import services.ScheduleService;
import services.StationService;
import services.TicketService;
import services.TrainService;

public class ApplicationController {

    private final ApplicationState state;
    private final DataStore dataStore;

    private final TrainService trainService;
    private final StationService stationService;
    private final RouteService routeService;
    private final ScheduleService scheduleService;
    private final PassengerService passengerService;
    private final TicketService ticketService;
    private final BookingService bookingService;
    private final MaintenanceService maintenanceService;
    private final AlertService alertService;
    private final EventService eventService;

    private final AnalyticsService analyticsService;

    public ApplicationController(
            ApplicationState state,
            DataStore dataStore) {

        if (state == null) {
            throw new IllegalArgumentException(
                    "Application state cannot be null.");
        }

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.state = state;
        this.dataStore = dataStore;

        this.trainService =
                new TrainService(dataStore);

        this.stationService =
                new StationService(dataStore);

        this.routeService =
                new RouteService(dataStore);

        this.scheduleService =
                new ScheduleService(dataStore);

        this.passengerService =
                new PassengerService(dataStore);

        this.ticketService =
                new TicketService(dataStore);

        this.bookingService =
                new BookingService(dataStore);

        this.maintenanceService =
                new MaintenanceService(dataStore);

        this.alertService =
                new AlertService(dataStore);

        this.eventService =
                new EventService(dataStore);

        this.analyticsService =
                new AnalyticsService(dataStore);
    }

    public ApplicationState getState() {
        return state;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public TrainService getTrainService() {
        return trainService;
    }

    public StationService getStationService() {
        return stationService;
    }

    public RouteService getRouteService() {
        return routeService;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public PassengerService getPassengerService() {
        return passengerService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public MaintenanceService getMaintenanceService() {
        return maintenanceService;
    }

    public AlertService getAlertService() {
        return alertService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public AnalyticsService getAnalyticsService() {
        return analyticsService;
    }

    public void navigateTo(String section) {

        if (section == null
                || section.trim().isEmpty()) {
            return;
        }

        state.setCurrentSection(section);
        state.setStatusMessage(section + " selected");
    }

    public String getCurrentSection() {
        return state.getCurrentSection();
    }

    public String getStatusMessage() {
        return state.getStatusMessage();
    }

    public void setStatusMessage(String message) {
        state.setStatusMessage(message);
    }

    public void shutdown() {
        state.setRunning(false);
        state.setStatusMessage("RailSync stopped");
    }
}