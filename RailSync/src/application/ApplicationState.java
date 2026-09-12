package application;

public class ApplicationState {

    private boolean running;
    private String currentSection;
    private String statusMessage;

    public ApplicationState() {
        this.running = false;
        this.currentSection = "Dashboard";
        this.statusMessage = "Ready";
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String currentSection) {
        this.currentSection = currentSection;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}