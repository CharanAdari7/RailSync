package domain;

import java.util.Objects;

public class Coach {

    private String coachId;
    private String trainId;
    private String coachNumber;
    private String coachType;
    private int capacity;
    private ServiceStatus status;

    public Coach() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Coach(String coachId, String trainId,
                 String coachNumber, String coachType,
                 int capacity) {

        setCoachId(coachId);
        setTrainId(trainId);
        setCoachNumber(coachNumber);
        setCoachType(coachType);
        setCapacity(capacity);

        this.status = ServiceStatus.ACTIVE;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        if (coachId == null || coachId.trim().isEmpty()) {
            throw new IllegalArgumentException("Coach ID cannot be empty.");
        }
        this.coachId = coachId.trim();
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType == null ? "" : coachType.trim();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Coach capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return coachNumber + " - " + coachType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coach)) return false;

        Coach other = (Coach) obj;
        return Objects.equals(coachId, other.coachId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coachId);
    }
}