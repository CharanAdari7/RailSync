package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {

    private String bookingId;
    private String bookingReference;
    private String passengerId;
    private String scheduleId;
    private String ticketId;
    private LocalDateTime bookingTime;
    private double amount;
    private ServiceStatus status;

    public Booking() {
        this.bookingTime = LocalDateTime.now();
        this.status = ServiceStatus.CONFIRMED;
    }

    public Booking(String bookingId, String bookingReference,
                   String passengerId, String scheduleId,
                   String ticketId, double amount) {

        setBookingId(bookingId);
        setBookingReference(bookingReference);
        setPassengerId(passengerId);
        setScheduleId(scheduleId);
        setTicketId(ticketId);
        setAmount(amount);

        this.bookingTime = LocalDateTime.now();
        this.status = ServiceStatus.CONFIRMED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be empty.");
        }
        this.bookingId = bookingId.trim();
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
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

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = Objects.requireNonNull(bookingTime);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Booking amount cannot be negative.");
        }
        this.amount = amount;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return bookingReference + " - " + passengerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Booking)) return false;

        Booking other = (Booking) obj;
        return Objects.equals(bookingId, other.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}