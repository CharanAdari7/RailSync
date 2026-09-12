package processing;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import domain.Schedule;

public class ConflictAnalysisEngine {

    public List<String> findConflicts(List<Schedule> schedules) {

        List<String> conflicts = new ArrayList<>();

        if (schedules == null) {
            return conflicts;
        }

        for (int i = 0; i < schedules.size(); i++) {

            Schedule first = schedules.get(i);

            if (first == null) {
                continue;
            }

            for (int j = i + 1; j < schedules.size(); j++) {

                Schedule second = schedules.get(j);

                if (second == null) {
                    continue;
                }

                if (!first.getOperatingDate()
                        .equals(second.getOperatingDate())) {
                    continue;
                }

                if (!first.getTrainId()
                        .equals(second.getTrainId())) {
                    continue;
                }

                if (overlaps(
                        first.getDepartureTime(),
                        first.getArrivalTime(),
                        second.getDepartureTime(),
                        second.getArrivalTime())) {

                    conflicts.add(
                            first.getScheduleId()
                            + " conflicts with "
                            + second.getScheduleId()
                    );
                }
            }
        }

        return conflicts;
    }

    private boolean overlaps(
            LocalTime start1,
            LocalTime end1,
            LocalTime start2,
            LocalTime end2) {

        return start1.isBefore(end2)
                && start2.isBefore(end1);
    }
}