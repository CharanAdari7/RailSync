package processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Ticket;

public class PassengerFlowEngine {

    public Map<String, Integer> countPassengersBySchedule(
            List<Ticket> tickets) {

        Map<String, Integer> result = new HashMap<>();

        if (tickets == null) {
            return result;
        }

        for (Ticket ticket : tickets) {

            if (ticket == null
                    || ticket.getScheduleId() == null) {
                continue;
            }

            if (ticket.getStatus() == domain.ServiceStatus.CANCELLED) {
                continue;
            }

            String scheduleId = ticket.getScheduleId();

            result.put(
                    scheduleId,
                    result.getOrDefault(scheduleId, 0) + 1
            );
        }

        return result;
    }

    public int getPassengerCountForSchedule(
            List<Ticket> tickets,
            String scheduleId) {

        if (tickets == null || scheduleId == null) {
            return 0;
        }

        int count = 0;

        for (Ticket ticket : tickets) {

            if (ticket == null
                    || ticket.getStatus()
                    == domain.ServiceStatus.CANCELLED) {
                continue;
            }

            if (scheduleId.equals(ticket.getScheduleId())) {
                count++;
            }
        }

        return count;
    }
}