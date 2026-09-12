package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.Schedule;
import domain.ServiceStatus;

public class ScheduleService {

    private final DataStore dataStore;

    public ScheduleService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void addSchedule(Schedule schedule) {

        validate(schedule);

        if (dataStore.getSchedule(schedule.getScheduleId()) != null) {
            throw new IllegalArgumentException(
                    "Schedule ID already exists.");
        }

        validateReferences(schedule);

        if (hasConflict(schedule)) {
            throw new IllegalArgumentException(
                    "Schedule conflicts with an existing schedule.");
        }

        dataStore.addSchedule(schedule);
    }

    public void updateSchedule(Schedule schedule) {

        validate(schedule);

        if (dataStore.getSchedule(schedule.getScheduleId()) == null) {
            throw new IllegalArgumentException("Schedule not found.");
        }

        validateReferences(schedule);

        dataStore.addSchedule(schedule);
    }

    public void deleteSchedule(String scheduleId) {

        if (dataStore.getSchedule(scheduleId) == null) {
            throw new IllegalArgumentException("Schedule not found.");
        }

        dataStore.removeSchedule(scheduleId);
    }

    public Schedule findById(String scheduleId) {
        return dataStore.getSchedule(scheduleId);
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(dataStore.getAllSchedules());
    }

    public List<Schedule> findByDate(LocalDate date) {

        List<Schedule> result = new ArrayList<>();

        if (date == null) {
            return result;
        }

        for (Schedule schedule : dataStore.getAllSchedules()) {

            if (date.equals(schedule.getOperatingDate())) {
                result.add(schedule);
            }
        }

        return result;
    }

    public List<Schedule> findByTrain(String trainId) {

        List<Schedule> result = new ArrayList<>();

        for (Schedule schedule : dataStore.getAllSchedules()) {

            if (trainId != null
                    && trainId.equals(schedule.getTrainId())) {

                result.add(schedule);
            }
        }

        return result;
    }

    public List<Schedule> findByStation(String stationId) {

        List<Schedule> result = new ArrayList<>();

        for (Schedule schedule : dataStore.getAllSchedules()) {

            if (stationId != null
                    && (stationId.equals(schedule.getSourceStationId())
                    || stationId.equals(schedule.getDestinationStationId()))) {

                result.add(schedule);
            }
        }

        return result;
    }

    public void changeStatus(String scheduleId, ServiceStatus status) {

        Schedule schedule = findById(scheduleId);

        if (schedule == null) {
            throw new IllegalArgumentException("Schedule not found.");
        }

        schedule.setStatus(status);
    }

    public boolean hasConflict(Schedule candidate) {

        for (Schedule existing : dataStore.getAllSchedules()) {

            if (existing.getScheduleId().equals(candidate.getScheduleId())) {
                continue;
            }

            if (!existing.getOperatingDate()
                    .equals(candidate.getOperatingDate())) {
                continue;
            }

            if (!existing.getTrainId().equals(candidate.getTrainId())) {
                continue;
            }

            if (timeOverlaps(
                    existing.getDepartureTime(),
                    existing.getArrivalTime(),
                    candidate.getDepartureTime(),
                    candidate.getArrivalTime())) {

                return true;
            }
        }

        return false;
    }

    public int count() {
        return dataStore.getScheduleCount();
    }

    private boolean timeOverlaps(
            LocalTime start1,
            LocalTime end1,
            LocalTime start2,
            LocalTime end2) {

        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    private void validate(Schedule schedule) {

        if (schedule == null) {
            throw new IllegalArgumentException("Schedule cannot be null.");
        }

        if (schedule.getDepartureTime() == null
                || schedule.getArrivalTime() == null) {
            throw new IllegalArgumentException(
                    "Departure and arrival times are required.");
        }

        if (!schedule.getDepartureTime()
                .isBefore(schedule.getArrivalTime())) {

            throw new IllegalArgumentException(
                    "Departure time must be before arrival time.");
        }
    }

    private void validateReferences(Schedule schedule) {

        if (dataStore.getTrain(schedule.getTrainId()) == null) {
            throw new IllegalArgumentException(
                    "Referenced train does not exist.");
        }

        if (dataStore.getRoute(schedule.getRouteId()) == null) {
            throw new IllegalArgumentException(
                    "Referenced route does not exist.");
        }
    }
}