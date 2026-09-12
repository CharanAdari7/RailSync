package services;

import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.Booking;
import domain.ServiceStatus;
import domain.Ticket;

public class BookingService {

    private final DataStore dataStore;

    public BookingService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void createBooking(Booking booking) {

        validate(booking);

        if (dataStore.getBooking(booking.getBookingId()) != null) {
            throw new IllegalArgumentException(
                    "Booking ID already exists.");
        }

        if (dataStore.getPassenger(booking.getPassengerId()) == null) {
            throw new IllegalArgumentException(
                    "Passenger does not exist.");
        }

        if (dataStore.getSchedule(booking.getScheduleId()) == null) {
            throw new IllegalArgumentException(
                    "Schedule does not exist.");
        }

        if (booking.getTicketId() != null
                && dataStore.getTicket(booking.getTicketId()) == null) {

            throw new IllegalArgumentException(
                    "Ticket does not exist.");
        }

        dataStore.addBooking(booking);
    }

    public void updateBooking(Booking booking) {

        validate(booking);

        if (dataStore.getBooking(booking.getBookingId()) == null) {
            throw new IllegalArgumentException("Booking not found.");
        }

        dataStore.addBooking(booking);
    }

    public void cancelBooking(String bookingId) {

        Booking booking = dataStore.getBooking(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found.");
        }

        booking.setStatus(ServiceStatus.CANCELLED);

        if (booking.getTicketId() != null) {

            Ticket ticket = dataStore.getTicket(
                    booking.getTicketId());

            if (ticket != null) {
                ticket.setStatus(ServiceStatus.CANCELLED);
            }
        }
    }

    public Booking findById(String bookingId) {
        return dataStore.getBooking(bookingId);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(dataStore.getAllBookings());
    }

    public List<Booking> findByPassenger(String passengerId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : dataStore.getAllBookings()) {

            if (passengerId != null
                    && passengerId.equals(booking.getPassengerId())) {

                result.add(booking);
            }
        }

        return result;
    }

    public double getTotalRevenue() {

        double total = 0.0;

        for (Booking booking : dataStore.getAllBookings()) {

            if (booking.getStatus() != ServiceStatus.CANCELLED) {
                total += booking.getAmount();
            }
        }

        return total;
    }

    public int count() {
        return dataStore.getBookingCount();
    }

    private void validate(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking cannot be null.");
        }

        if (booking.getAmount() < 0) {
            throw new IllegalArgumentException(
                    "Booking amount cannot be negative.");
        }
    }
}