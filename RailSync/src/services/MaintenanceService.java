package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.MaintenanceRecord;
import domain.ServiceStatus;

public class MaintenanceService {

    private final DataStore dataStore;

    public MaintenanceService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void addRecord(MaintenanceRecord record) {

        validate(record);

        if (dataStore.getMaintenanceRecord(
                record.getMaintenanceId()) != null) {

            throw new IllegalArgumentException(
                    "Maintenance ID already exists.");
        }

        if (dataStore.getTrain(record.getTrainId()) == null) {
            throw new IllegalArgumentException(
                    "Referenced train does not exist.");
        }

        dataStore.addMaintenanceRecord(record);
    }

    public void updateRecord(MaintenanceRecord record) {

        validate(record);

        if (dataStore.getMaintenanceRecord(
                record.getMaintenanceId()) == null) {

            throw new IllegalArgumentException(
                    "Maintenance record not found.");
        }

        dataStore.addMaintenanceRecord(record);
    }

    public void deleteRecord(String maintenanceId) {

        if (dataStore.getMaintenanceRecord(maintenanceId) == null) {
            throw new IllegalArgumentException(
                    "Maintenance record not found.");
        }

        dataStore.removeMaintenanceRecord(maintenanceId);
    }

    public MaintenanceRecord findById(String maintenanceId) {
        return dataStore.getMaintenanceRecord(maintenanceId);
    }

    public List<MaintenanceRecord> getAllRecords() {
        return new ArrayList<>(
                dataStore.getAllMaintenanceRecords());
    }

    public List<MaintenanceRecord> findByTrain(String trainId) {

        List<MaintenanceRecord> result = new ArrayList<>();

        for (MaintenanceRecord record :
                dataStore.getAllMaintenanceRecords()) {

            if (trainId != null
                    && trainId.equals(record.getTrainId())) {

                result.add(record);
            }
        }

        return result;
    }

    public List<MaintenanceRecord> getDueRecords(LocalDate date) {

        List<MaintenanceRecord> result = new ArrayList<>();

        if (date == null) {
            return result;
        }

        for (MaintenanceRecord record :
                dataStore.getAllMaintenanceRecords()) {

            LocalDate dueDate = record.getNextDueDate();

            if (dueDate != null && !dueDate.isAfter(date)) {
                result.add(record);
            }
        }

        return result;
    }

    public void completeMaintenance(String maintenanceId) {

        MaintenanceRecord record = findById(maintenanceId);

        if (record == null) {
            throw new IllegalArgumentException(
                    "Maintenance record not found.");
        }

        record.setStatus(ServiceStatus.COMPLETED);
    }

    public int count() {
        return dataStore.getMaintenanceCount();
    }

    private void validate(MaintenanceRecord record) {

        if (record == null) {
            throw new IllegalArgumentException(
                    "Maintenance record cannot be null.");
        }

        if (record.getCost() < 0) {
            throw new IllegalArgumentException(
                    "Maintenance cost cannot be negative.");
        }
    }
}