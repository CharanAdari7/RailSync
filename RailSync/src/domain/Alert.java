package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Alert {

    private String alertId;
    private String title;
    private String message;
    private String severity;
    private String relatedEntityId;
    private LocalDateTime createdAt;
    private boolean acknowledged;

    public Alert() {
        this.createdAt = LocalDateTime.now();
        this.acknowledged = false;
    }

    public Alert(String alertId, String title, String message,
                 String severity, String relatedEntityId) {

        setAlertId(alertId);
        setTitle(title);
        setMessage(message);
        setSeverity(severity);
        setRelatedEntityId(relatedEntityId);

        this.createdAt = LocalDateTime.now();
        this.acknowledged = false;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        if (alertId == null || alertId.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert ID cannot be empty.");
        }
        this.alertId = alertId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message.trim();
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity == null ? "" : severity.trim();
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    @Override
    public String toString() {
        return severity + " - " + title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Alert)) return false;

        Alert other = (Alert) obj;
        return Objects.equals(alertId, other.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }
}