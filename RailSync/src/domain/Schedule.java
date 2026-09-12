package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule {

    private String scheduleId;
    private String trainId;
    private String routeId;
    private LocalDate operatingDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String sourceStationId;
    private String destinationStationId;
    private ServiceStatus status;

    public Schedule() {
        this.status = ServiceStatus.SCHEDULED;
    }

    public Schedule(String scheduleId, String trainId, String routeId,
                    LocalDate operatingDate, LocalTime departureTime,
                    LocalTime arrivalTime, String sourceStationId,
                    String destinationStationId) {

        setScheduleId(scheduleId);
        setTrainId(trainId);
        setRouteId(routeId);
        setOperatingDate(operatingDate);
        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);
        setSourceStationId(sourceStationId);
        setDestinationStationId(destinationStationId);

        this.status = ServiceStatus.SCHEDULED;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        if (scheduleId == null || scheduleId.trim().isEmpty()) {
            throw new IllegalArgumentException("Schedule ID cannot be empty.");
        }
        this.scheduleId = scheduleId.trim();
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public LocalDate getOperatingDate() {
        return operatingDate;
    }

    public void setOperatingDate(LocalDate operatingDate) {
        this.operatingDate = Objects.requireNonNull(operatingDate);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = Objects.requireNonNull(departureTime);
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = Objects.requireNonNull(arrivalTime);
    }

    public String getSourceStationId() {
        return sourceStationId;
    }

    public void setSourceStationId(String sourceStationId) {
        this.sourceStationId = sourceStationId;
    }

    public String getDestinationStationId() {
        return destinationStationId;
    }

    public void setDestinationStationId(String destinationStationId) {
        this.destinationStationId = destinationStationId;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return scheduleId + " - " + operatingDate + " " + departureTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Schedule)) return false;

        Schedule other = (Schedule) obj;
        return Objects.equals(scheduleId, other.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }
}