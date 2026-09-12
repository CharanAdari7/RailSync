package domain;

import java.time.LocalDate;
import java.util.Objects;

public class MaintenanceRecord {

    private String maintenanceId;
    private String trainId;
    private String description;
    private LocalDate maintenanceDate;
    private LocalDate nextDueDate;
    private String technician;
    private double cost;
    private ServiceStatus status;

    public MaintenanceRecord() {
        this.status = ServiceStatus.PENDING;
    }

    public MaintenanceRecord(String maintenanceId, String trainId,
                             String description, LocalDate maintenanceDate,
                             LocalDate nextDueDate, String technician,
                             double cost) {

        setMaintenanceId(maintenanceId);
        setTrainId(trainId);
        setDescription(description);
        setMaintenanceDate(maintenanceDate);
        setNextDueDate(nextDueDate);
        setTechnician(technician);
        setCost(cost);

        this.status = ServiceStatus.PENDING;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        if (maintenanceId == null || maintenanceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Maintenance ID cannot be empty.");
        }
        this.maintenanceId = maintenanceId.trim();
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = Objects.requireNonNull(maintenanceDate);
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician == null ? "" : technician.trim();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Maintenance cost cannot be negative.");
        }
        this.cost = cost;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return maintenanceId + " - " + trainId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MaintenanceRecord)) return false;

        MaintenanceRecord other = (MaintenanceRecord) obj;
        return Objects.equals(maintenanceId, other.maintenanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maintenanceId);
    }
}