package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class RailwayEvent {

    private String eventId;
    private String eventType;
    private String entityType;
    private String entityId;
    private String description;
    private LocalDateTime timestamp;
    private String source;

    public RailwayEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public RailwayEvent(String eventId, String eventType,
                        String entityType, String entityId,
                        String description, String source) {

        setEventId(eventId);
        setEventType(eventType);
        setEntityType(entityType);
        setEntityId(entityId);
        setDescription(description);
        setSource(source);

        this.timestamp = LocalDateTime.now();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        if (eventId == null || eventId.trim().isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be empty.");
        }
        this.eventId = eventId.trim();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType == null ? "" : eventType.trim();
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType == null ? "" : entityType.trim();
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? "" : source.trim();
    }

    @Override
    public String toString() {
        return timestamp + " - " + eventType + " - " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RailwayEvent)) return false;

        RailwayEvent other = (RailwayEvent) obj;
        return Objects.equals(eventId, other.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}