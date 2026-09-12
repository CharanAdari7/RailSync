package domain;

import java.util.Objects;

public class Ticket {

    private String ticketId;
    private String ticketNumber;
    private String passengerId;
    private String scheduleId;
    private String coachId;
    private String seatNumber;
    private double fare;
    private String ticketType;
    private ServiceStatus status;

    public Ticket() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Ticket(String ticketId, String ticketNumber,
                  String passengerId, String scheduleId,
                  String coachId, String seatNumber,
                  double fare, String ticketType) {

        setTicketId(ticketId);
        setTicketNumber(ticketNumber);
        setPassengerId(passengerId);
        setScheduleId(scheduleId);
        setCoachId(coachId);
        setSeatNumber(seatNumber);
        setFare(fare);
        setTicketType(ticketType);

        this.status = ServiceStatus.ACTIVE;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be empty.");
        }
        this.ticketId = ticketId.trim();
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        if (fare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative.");
        }
        this.fare = fare;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType == null ? "" : ticketType.trim();
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return ticketNumber + " - " + passengerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ticket)) return false;

        Ticket other = (Ticket) obj;
        return Objects.equals(ticketId, other.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
}