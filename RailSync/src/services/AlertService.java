package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.Alert;

public class AlertService {

    private final DataStore dataStore;

    public AlertService(DataStore dataStore) {

        if (dataStore == null) {
            throw new IllegalArgumentException(
                    "DataStore cannot be null.");
        }

        this.dataStore = dataStore;
    }

    /*
     * Create a new alert
     */
    public void createAlert(Alert alert) {

        if (alert == null) {
            throw new IllegalArgumentException(
                    "Alert cannot be null.");
        }

        if (alert.getAlertId() == null
                || alert.getAlertId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Alert ID is required.");
        }

        if (findById(alert.getAlertId()) != null) {

            throw new IllegalArgumentException(
                    "Alert ID already exists.");
        }

        dataStore.addAlert(alert);
    }

    /*
     * Update an existing alert
     */
    public void updateAlert(Alert alert) {

        if (alert == null) {
            throw new IllegalArgumentException(
                    "Alert cannot be null.");
        }

        if (alert.getAlertId() == null
                || alert.getAlertId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Alert ID is required.");
        }

        Alert existing =
                findById(alert.getAlertId());

        if (existing == null) {

            throw new IllegalArgumentException(
                    "Alert not found.");
        }

        dataStore.updateAlert(alert);
    }

    /*
     * Find alert by ID
     */
    public Alert findById(String alertId) {

        if (alertId == null
                || alertId.trim().isEmpty()) {

            return null;
        }

        return dataStore.getAlert(
                alertId.trim());
    }

    /*
     * Return all alerts
     */
    public List<Alert> getAllAlerts() {

        return new ArrayList<>(
                dataStore.getAllAlerts());
    }

    /*
     * Return alerts that are not acknowledged
     */
    public List<Alert> getActiveAlerts() {

        List<Alert> result =
                new ArrayList<>();

        for (Alert alert :
                dataStore.getAllAlerts()) {

            if (alert == null) {
                continue;
            }

            if (!alert.isAcknowledged()) {
                result.add(alert);
            }
        }

        return result;
    }

    /*
     * Acknowledge an alert
     */
    public void acknowledge(String alertId) {

        Alert alert =
                findById(alertId);

        if (alert == null) {

            throw new IllegalArgumentException(
                    "Alert not found.");
        }

        alert.setAcknowledged(true);

        dataStore.updateAlert(alert);
    }

    /*
     * Delete an alert
     */
    public void deleteAlert(String alertId) {

        Alert alert =
                findById(alertId);

        if (alert == null) {

            throw new IllegalArgumentException(
                    "Alert not found.");
        }

        dataStore.removeAlert(alertId);
    }

    /*
     * Create a system-generated alert
     */
    public void createSystemAlert(
            String alertId,
            String title,
            String message,
            String severity,
            String relatedEntityId) {

        Alert alert =
                new Alert(
                        alertId,
                        title,
                        message,
                        severity,
                        relatedEntityId
                );

        alert.setCreatedAt(
                LocalDateTime.now());

        createAlert(alert);
    }

    /*
     * Number of alerts
     */
    public int count() {

        return dataStore.getAlertCount();
    }
}