package services;

import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.Schedule;
import domain.Ticket;

public class TicketService {

    private final DataStore dataStore;

    public TicketService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void issueTicket(Ticket ticket) {

        validate(ticket);

        if (dataStore.getTicket(ticket.getTicketId()) != null) {
            throw new IllegalArgumentException("Ticket ID already exists.");
        }

        validateReferences(ticket);

        if (isSeatOccupied(
                ticket.getScheduleId(),
                ticket.getCoachId(),
                ticket.getSeatNumber())) {

            throw new IllegalArgumentException(
                    "Selected seat is already occupied.");
        }

        dataStore.addTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {

        validate(ticket);

        if (dataStore.getTicket(ticket.getTicketId()) == null) {
            throw new IllegalArgumentException("Ticket not found.");
        }

        validateReferences(ticket);

        dataStore.addTicket(ticket);
    }

    public void cancelTicket(String ticketId) {

        Ticket ticket = dataStore.getTicket(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found.");
        }

        ticket.setStatus(domain.ServiceStatus.CANCELLED);
    }

    public Ticket findById(String ticketId) {
        return dataStore.getTicket(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(dataStore.getAllTickets());
    }

    public List<Ticket> findBySchedule(String scheduleId) {

        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : dataStore.getAllTickets()) {

            if (scheduleId != null
                    && scheduleId.equals(ticket.getScheduleId())) {

                result.add(ticket);
            }
        }

        return result;
    }

    public List<Ticket> findByPassenger(String passengerId) {

        List<Ticket> result = new ArrayList<>();

        for (Ticket ticket : dataStore.getAllTickets()) {

            if (passengerId != null
                    && passengerId.equals(ticket.getPassengerId())) {

                result.add(ticket);
            }
        }

        return result;
    }

    public boolean isSeatOccupied(
            String scheduleId,
            String coachId,
            String seatNumber) {

        for (Ticket ticket : dataStore.getAllTickets()) {

            if (ticket.getStatus() == domain.ServiceStatus.CANCELLED) {
                continue;
            }

            if (equals(ticket.getScheduleId(), scheduleId)
                    && equals(ticket.getCoachId(), coachId)
                    && equals(ticket.getSeatNumber(), seatNumber)) {

                return true;
            }
        }

        return false;
    }

    public int count() {
        return dataStore.getTicketCount();
    }

    private void validate(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null.");
        }

        if (ticket.getFare() < 0) {
            throw new IllegalArgumentException(
                    "Fare cannot be negative.");
        }
    }

    private void validateReferences(Ticket ticket) {

        if (dataStore.getPassenger(ticket.getPassengerId()) == null) {
            throw new IllegalArgumentException(
                    "Referenced passenger does not exist.");
        }

        Schedule schedule =
                dataStore.getSchedule(ticket.getScheduleId());

        if (schedule == null) {
            throw new IllegalArgumentException(
                    "Referenced schedule does not exist.");
        }
    }

    private boolean equals(String first, String second) {
        return first == null
                ? second == null
                : first.equals(second);
    }
}