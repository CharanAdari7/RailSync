package analytics;

import java.util.HashMap;
import java.util.Map;

import data.DataStore;
import domain.RailwayService;
import domain.Schedule;
import domain.ServiceStatus;

public class ServiceAnalytics {

    private final DataStore dataStore;

    public ServiceAnalytics(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    public int getTotalServices() {
        return dataStore.getServiceCount();
    }

    public Map<ServiceStatus, Integer>
            getSchedulesByStatus() {

        Map<ServiceStatus, Integer> result =
                new HashMap<>();

        for (Schedule schedule :
                dataStore.getAllSchedules()) {

            ServiceStatus status =
                    schedule.getStatus();

            result.put(
                    status,
                    result.getOrDefault(status, 0) + 1
            );
        }

        return result;
    }

    public Map<ServiceStatus, Integer>
            getServicesByStatus() {

        Map<ServiceStatus, Integer> result =
                new HashMap<>();

        for (RailwayService service :
                dataStore.getAllServices()) {

            ServiceStatus status =
                    service.getStatus();

            result.put(
                    status,
                    result.getOrDefault(status, 0) + 1
            );
        }

        return result;
    }

    public int getDelayedSchedules() {

        int count = 0;

        for (Schedule schedule :
                dataStore.getAllSchedules()) {

            if (schedule.getStatus()
                    == ServiceStatus.DELAYED) {

                count++;
            }
        }

        return count;
    }

    public int getCompletedSchedules() {

        int count = 0;

        for (Schedule schedule :
                dataStore.getAllSchedules()) {

            if (schedule.getStatus()
                    == ServiceStatus.COMPLETED) {

                count++;
            }
        }

        return count;
    }

    public int getCancelledSchedules() {

        int count = 0;

        for (Schedule schedule :
                dataStore.getAllSchedules()) {

            if (schedule.getStatus()
                    == ServiceStatus.CANCELLED) {

                count++;
            }
        }

        return count;
    }

    public double getDelayRate() {

        int total =
                dataStore.getScheduleCount();

        if (total == 0) {
            return 0.0;
        }

        return ((double) getDelayedSchedules()
                / total) * 100.0;
    }
    public int getActiveServices() {
        int count = 0;
        for (RailwayService service : dataStore.getAllServices()) {
            if (service != null && service.getStatus() == ServiceStatus.ACTIVE) count++;
        }
        return count;
    }

}