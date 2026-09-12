package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.DataStore;
import domain.RailwayEvent;

public class EventService {

    private final DataStore dataStore;

    public EventService(DataStore dataStore) {
        if (dataStore == null) {
            throw new IllegalArgumentException("DataStore cannot be null.");
        }
        this.dataStore = dataStore;
    }

    public void recordEvent(RailwayEvent event) {

        if (event == null) {
            throw new IllegalArgumentException(
                    "Event cannot be null.");
        }

        if (dataStore.getEvent(event.getEventId()) != null) {
            throw new IllegalArgumentException(
                    "Event ID already exists.");
        }

        dataStore.addEvent(event);
    }

    public void record(
            String eventId,
            String eventType,
            String entityType,
            String entityId,
            String description,
            String source) {

        RailwayEvent event = new RailwayEvent(
                eventId,
                eventType,
                entityType,
                entityId,
                description,
                source
        );

        recordEvent(event);
    }

    public RailwayEvent findById(String eventId) {
        return dataStore.getEvent(eventId);
    }

    public List<RailwayEvent> getAllEvents() {
        return new ArrayList<>(dataStore.getAllEvents());
    }

    public List<RailwayEvent> findByEntity(
            String entityType,
            String entityId) {

        List<RailwayEvent> result = new ArrayList<>();

        for (RailwayEvent event : dataStore.getAllEvents()) {

            boolean typeMatches =
                    entityType == null
                    || entityType.equalsIgnoreCase(
                            event.getEntityType());

            boolean idMatches =
                    entityId == null
                    || entityId.equals(event.getEntityId());

            if (typeMatches && idMatches) {
                result.add(event);
            }
        }

        return result;
    }

    public List<RailwayEvent> findByType(String eventType) {

        List<RailwayEvent> result = new ArrayList<>();

        for (RailwayEvent event : dataStore.getAllEvents()) {

            if (eventType != null
                    && eventType.equalsIgnoreCase(
                            event.getEventType())) {

                result.add(event);
            }
        }

        return result;
    }

    public List<RailwayEvent> findBetween(
            LocalDateTime start,
            LocalDateTime end) {

        List<RailwayEvent> result = new ArrayList<>();

        if (start == null || end == null) {
            return result;
        }

        for (RailwayEvent event : dataStore.getAllEvents()) {

            LocalDateTime time = event.getTimestamp();

            if (!time.isBefore(start) && !time.isAfter(end)) {
                result.add(event);
            }
        }

        return result;
    }

    public int count() {
        return dataStore.getEventCount();
    }
}