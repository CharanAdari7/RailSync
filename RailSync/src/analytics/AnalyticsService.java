package analytics;

import data.DataStore;

public class AnalyticsService {

    private final DataStore dataStore;

    private final PassengerAnalytics passengerAnalytics;
    private final RevenueAnalytics revenueAnalytics;
    private final TrainAnalytics trainAnalytics;
    private final StationAnalytics stationAnalytics;
    private final ServiceAnalytics serviceAnalytics;

    public AnalyticsService(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;

        this.passengerAnalytics =
                new PassengerAnalytics(dataStore);

        this.revenueAnalytics =
                new RevenueAnalytics(dataStore);

        this.trainAnalytics =
                new TrainAnalytics(dataStore);

        this.stationAnalytics =
                new StationAnalytics(dataStore);

        this.serviceAnalytics =
                new ServiceAnalytics(dataStore);
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public PassengerAnalytics getPassengerAnalytics() {
        return passengerAnalytics;
    }

    public RevenueAnalytics getRevenueAnalytics() {
        return revenueAnalytics;
    }

    public TrainAnalytics getTrainAnalytics() {
        return trainAnalytics;
    }

    public StationAnalytics getStationAnalytics() {
        return stationAnalytics;
    }

    public ServiceAnalytics getServiceAnalytics() {
        return serviceAnalytics;
    }

    public OperationalReport generateReport() {

        return new OperationalReport(
                dataStore,
                passengerAnalytics,
                revenueAnalytics,
                trainAnalytics,
                stationAnalytics,
                serviceAnalytics
        );
    }
}